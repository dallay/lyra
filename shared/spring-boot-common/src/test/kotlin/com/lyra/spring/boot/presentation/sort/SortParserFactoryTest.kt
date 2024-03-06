package com.lyra.spring.boot.presentation.sort

import com.fasterxml.jackson.databind.ObjectMapper
import com.lyra.spring.boot.entity.Person
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class SortParserFactoryTest {
    private val objectMapper = ObjectMapper()
    private val sortParserFactory = SortParserFactory(objectMapper)

    @Test
    fun `should create sort parser for valid class`() {
        val sortParser = sortParserFactory.create(Person::class)
        assertNotNull(sortParser)
    }

    @Test
    fun `should create sort parser using reified fun`() {
        val sortParser = sortParserFactory.create<Person>()
        assertNotNull(sortParser)
    }
}
