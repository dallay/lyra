package com.lyra.spring.boot.presentation.sort

import com.fasterxml.jackson.databind.ObjectMapper
import com.lyra.common.domain.presentation.SortInvalidException
import com.lyra.common.domain.presentation.sort.Order
import com.lyra.spring.boot.entity.Person
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class SortParserTest {
    private val objectMapper = ObjectMapper()
    private val sortParser = SortParser(Person::class, objectMapper)

    @Test
    fun parse() {
        var sort = sortParser.parse(listOf("desc:name", "asc:id"))
        assertEquals(Order.desc("name"), sort.getOrderFor("name"))
        assertEquals(Order.asc("id"), sort.getOrderFor("id"))

        sort = sortParser.parse("desc:name")
        assertEquals(Order.desc("name"), sort.getOrderFor("name"))

        assertThrows(SortInvalidException::class.java) { sortParser.parse("desc:not_found") }
        assertThrows(SortInvalidException::class.java) { sortParser.parse("not_order:name") }
    }

    @Test
    fun `parse multiples sorts`() {
        val sort = sortParser.parse(listOf("desc:name", "asc:id", "asc:age"))
        assertEquals(Order.desc("name"), sort.getOrderFor("name"))
        assertEquals(Order.asc("id"), sort.getOrderFor("id"))
        assertEquals(Order.asc("age"), sort.getOrderFor("age"))
    }
}
