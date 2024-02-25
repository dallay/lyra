package com.lyra.common.domain.presentation.sort

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class SortTest {

    @Test
    fun `and combines two sorts`() {
        val sort1 = Sort.by(Direction.ASC, "name")
        val sort2 = Sort.by(Direction.DESC, "age")
        val combinedSort = sort1.and(sort2)

        assertEquals(Order.asc("name"), combinedSort.getOrderFor("name"))
        assertEquals(Order.desc("age"), combinedSort.getOrderFor("age"))
    }

    @Test
    fun `ascending changes all orders to ascending`() {
        val sort = Sort.by(Direction.DESC, "name", "age").ascending()
        assertEquals(Direction.ASC, sort.getOrderFor("name")?.direction)
        assertEquals(Direction.ASC, sort.getOrderFor("age")?.direction)
    }

    @Test
    fun `descending changes all orders to descending`() {
        val sort = Sort.by(Direction.ASC, "name", "age").descending()
        assertEquals(Direction.DESC, sort.getOrderFor("name")?.direction)
        assertEquals(Direction.DESC, sort.getOrderFor("age")?.direction)
    }

    @Test
    fun `getOrderFor returns correct order for property`() {
        val sort = Sort.by(Direction.ASC, "name", "age")
        assertEquals(Order.asc("name"), sort.getOrderFor("name"))
        assertEquals(Order.asc("age"), sort.getOrderFor("age"))
    }

    @Test
    fun `getOrderFor returns null for non-existent property`() {
        val sort = Sort.by(Direction.ASC, "name", "age")
        assertNull(sort.getOrderFor("nonExistentProperty"))
    }

    @Test
    fun `by creates sort with default direction for all properties`() {
        val sort = Sort.by("name", "age")
        assertEquals(Order.asc("name"), sort.getOrderFor("name"))
        assertEquals(Order.asc("age"), sort.getOrderFor("age"))
    }

    @Test
    fun `by creates sort with specified direction for all properties`() {
        val sort = Sort.by(Direction.DESC, "name", "age")
        assertEquals(Order.desc("name"), sort.getOrderFor("name"))
        assertEquals(Order.desc("age"), sort.getOrderFor("age"))
    }
}
