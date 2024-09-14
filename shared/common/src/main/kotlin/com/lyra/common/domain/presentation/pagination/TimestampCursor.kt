package com.lyra.common.domain.presentation.pagination

import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.sort.Direction
import com.lyra.common.domain.presentation.sort.Sort
import java.time.LocalDateTime
import java.time.format.DateTimeParseException
import kotlin.reflect.full.declaredMemberProperties

/**
 * This class represents a timestamp cursor for pagination.
 * It implements the Cursor interface and provides methods to get the cursor, sort, and criteria.
 * It also provides methods to serialize and deserialize the cursor.
 *
 * @property createdAt The timestamp used as the cursor.
 * @property direction The direction of the cursor. Default is [Direction.ASC].
 */
data class TimestampCursor(
    val createdAt: LocalDateTime,
    override val direction: Direction = Direction.ASC
) : Cursor {
    /**
     * Returns the cursor as a formatted string.
     *
     * @return The cursor as a string.
     */
    override fun getCursor(): String = String.format(CURSOR_FORMAT, createdAt, direction)

    /**
     * Returns the sort object for the cursor.
     *
     * @return The sort object.
     */
    override fun getSort(): Sort = Sort.by(Direction.ASC, CREATED_AT)

    /**
     * Returns the criteria object for the cursor.
     * The criteria is a greater than or less than comparison based on the direction.
     *
     * @return The criteria object.
     */
    override fun getCriteria(): Criteria = if (direction == Direction.ASC) {
        Criteria.GreaterThan(CREATED_AT, createdAt)
    } else {
        Criteria.LessThan(CREATED_AT, createdAt)
    }

    override fun <T : Any> serialize(it: T, direction: Direction): String {
        val createdAtProperty = it::class.declaredMemberProperties.find { it.name == CREATED_AT }
        val createdAtValue = createdAtProperty?.getter?.call(it)
            ?: throw IllegalArgumentException("Invalid cursor object")
        return encode(String.format(CURSOR_FORMAT, createdAtValue, direction))
    }

    override fun isDefault(): Boolean = this == DEFAULT_CURSOR

    override fun toString(): String =
        """
            {
            createdAt: $createdAt,
            cursor: ${getCursor()},
            sort: ${getSort()},
            criteria: ${getCriteria()}
            }
        """.trimIndent()

    companion object {
        private const val SEPARATOR = "<—>"
        private const val CREATED_AT = "createdAt"
        private const val CURSOR_FORMAT = "%s$SEPARATOR%s"
        private val encoder: CursorEncoder = Base64CursorEncoder()
        val DEFAULT_CURSOR: Cursor = TimestampCursor(LocalDateTime.MIN)

        /**
         * Encodes the given data into a string.
         *
         * @param data The data to encode.
         * @return The encoded data.
         */
        fun encode(data: String): String = encoder.encode(data)

        /**
         * Decodes the given data into a string.
         *
         * @param encodeData The data to decode.
         * @return The decoded data.
         */
        fun decode(encodeData: String): String = encoder.decode(encodeData)

        /**
         * Deserializes the given data into a TimestampCursor object.
         *
         * @param serializedData The data to deserialize.
         * @return The deserialized TimestampCursor object.
         * @throws [DateTimeParseException] If the serialized data is invalid.
         */
        fun deserialize(serializedData: String): TimestampCursor {
            val decoded = decode(serializedData)
            val parts = decoded.split(SEPARATOR)
            val createdAt = LocalDateTime.parse(parts[0].trim())
            val direction = Direction.valueOf(parts[1].trim())
            return TimestampCursor(createdAt, direction)
        }
    }
}
