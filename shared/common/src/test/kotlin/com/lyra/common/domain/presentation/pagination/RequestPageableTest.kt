package com.lyra.common.domain.presentation.pagination

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RequestPageableTest {

    @Test
    fun `FilterCondition toString should format correctly`() {
        val filterCondition = FilterCondition(
            LogicalOperator.AND,
            listOf("gte:2023-03-12T23:00:00.000Z", "lte:2023-03-17T23:00:00.000Z"),
        )
        assertEquals(
            "AND:gte:2023-03-12T23:00:00.000Z,lte:2023-03-17T23:00:00.000Z",
            filterCondition.toString(),
        )
    }

    @Test
    fun `BaseRequestPageable default values should be correct`() {
        val pageable = BaseRequestPageable()
        assertEquals(10, pageable.size)
        assertEquals(null, pageable.search)
        assertEquals(mutableMapOf<String, FilterCondition>(), pageable.filter)
        assertEquals(emptyList<String>(), pageable.sort)
    }

    @Test
    fun `OffsetRequestPageable default values should be correct`() {
        val pageable = OffsetRequestPageable()
        assertEquals(10, pageable.size)
        assertEquals(null, pageable.search)
        assertEquals(mutableMapOf<String, FilterCondition>(), pageable.filter)
        assertEquals(emptyList<String>(), pageable.sort)
        assertEquals(0, pageable.page)
    }

    @Test
    fun `CursorRequestPageable default values should be correct`() {
        val pageable = CursorRequestPageable()
        assertEquals(10, pageable.size)
        assertEquals(null, pageable.search)
        assertEquals(mutableMapOf<String, FilterCondition>(), pageable.filter)
        assertEquals(emptyList<String>(), pageable.sort)
        assertEquals(null, pageable.cursor)
    }

    @Test
    fun `BaseRequestPageable should allow setting custom values`() {
        val filter = mutableMapOf(
            "status" to FilterCondition(
                LogicalOperator.OR,
                listOf("ENABLED", "DISABLED"),
            ),
        )
        val pageable = BaseRequestPageable(20, "searchTerm", filter, listOf("asc:name"))
        assertEquals(20, pageable.size)
        assertEquals("searchTerm", pageable.search)
        assertEquals(filter, pageable.filter)
        assertEquals(listOf("asc:name"), pageable.sort)
    }

    @Test
    fun `OffsetRequestPageable should allow setting custom values`() {
        val filter = mutableMapOf(
            "status" to FilterCondition(
                LogicalOperator.OR,
                listOf("ENABLED", "DISABLED"),
            ),
        )
        val pageable = OffsetRequestPageable(20, "searchTerm", filter, listOf("asc:name"), 2)
        assertEquals(20, pageable.size)
        assertEquals("searchTerm", pageable.search)
        assertEquals(filter, pageable.filter)
        assertEquals(listOf("asc:name"), pageable.sort)
        assertEquals(2, pageable.page)
    }

    @Test
    fun `CursorRequestPageable should allow setting custom values`() {
        val filter = mutableMapOf(
            "status" to FilterCondition(
                LogicalOperator.OR,
                listOf("ENABLED", "DISABLED"),
            ),
        )
        val pageable =
            CursorRequestPageable(20, "searchTerm", filter, listOf("asc:name"), "cursor123")
        assertEquals(20, pageable.size)
        assertEquals("searchTerm", pageable.search)
        assertEquals(filter, pageable.filter)
        assertEquals(listOf("asc:name"), pageable.sort)
        assertEquals("cursor123", pageable.cursor)
    }
}
