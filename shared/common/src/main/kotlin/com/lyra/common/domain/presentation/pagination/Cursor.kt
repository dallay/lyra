package com.lyra.common.domain.presentation.pagination

import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.sort.Sort

/**
 * This interface represents a cursor for pagination.
 * It provides methods to get the cursor, sort, and criteria.
 * It also provides methods to encode and decode the cursor.
 *
 * @created 12/2/24
 */
interface Cursor {
    /**
     * Returns the cursor as a string.
     *
     * @return The cursor as a string.
     */
    fun getCursor(): String

    /**
     * Returns the sort object for the cursor.
     *
     * @return The sort object.
     */
    fun getSort(): Sort

    /**
     * Returns the criteria object for the cursor.
     *
     * @return The criteria object.
     */
    fun getCriteria(): Criteria

    /**
     * Serializes the cursor into a string.
     *
     * @return The serialized cursor.
     */
    fun serialize(): String = encode(getCursor())
    fun <T : Any> serialize(it: T): String
    fun isDefault(): Boolean

    companion object {
        private val encoder: CursorEncoder = Base64CursorEncoder()

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
         * @param encodedData The data to decode.
         * @return The decoded data.
         * @throws [InvalidCursor] If the data is not a valid Base64 string.
         */
        fun decode(encodedData: String): String = encoder.decode(encodedData)
        fun default(): Cursor = TimestampCursor.DEFAULT_CURSOR
    }
}
