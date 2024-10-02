package com.lyra.common.domain.presentation.pagination

import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.sort.Direction
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

private const val CURSOR_SEPARATOR = "<—>"

internal class TimestampCursorTest {

    @Test
    fun `getCursor returns formatted string`() {
        val timestampCursor = TimestampCursor(LocalDateTime.of(2022, 3, 15, 12, 0))
        val expected = "2022-03-15T12:00${CURSOR_SEPARATOR}ASC"
        assertEquals(expected, timestampCursor.getCursor())
    }

    @Test
    fun `getSort returns ascending sort object`() {
        val timestampCursor = TimestampCursor(LocalDateTime.of(2022, 3, 15, 12, 0))
        val sort = timestampCursor.getSort()
        assertEquals("createdAt", sort.getOrderFor("createdAt")?.property)
        assertEquals(Direction.ASC, sort.getOrderFor("createdAt")?.direction)
    }

    @Test
    fun `getCriteria returns greater than criteria object`() {
        val timestampCursor = TimestampCursor(LocalDateTime.of(2022, 3, 15, 12, 0))
        val criteria = timestampCursor.getCriteria()
        assertEquals(Criteria.GreaterThan("createdAt", LocalDateTime.of(2022, 3, 15, 12, 0)), criteria)
    }

    @Test
    fun `serialize returns encoded cursor string`() {
        val timestampCursor = TimestampCursor(LocalDateTime.of(2022, 3, 15, 12, 0))
        val serialized = timestampCursor.serialize()
        val expected = TimestampCursor.encode("2022-03-15T12:00${CURSOR_SEPARATOR}ASC")
        assertEquals(expected, serialized)
    }

    @Test
    fun `deserialize returns TimestampCursor object from serialized string`() {
        val serialized = TimestampCursor.encode("2022-03-15T12:00${CURSOR_SEPARATOR}ASC")
        val timestampCursor = TimestampCursor.deserialize(serialized)
        assertEquals(LocalDateTime.of(2022, 3, 15, 12, 0), timestampCursor.createdAt)
    }

    @Test
    fun `deserialize throws exception for invalid serialized string`() {
        val serialized = "invalidSerializedString"
        assertThrows<InvalidCursor> {
            TimestampCursor.deserialize(serialized)
        }
    }
}
