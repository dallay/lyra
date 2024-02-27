package com.lyra.spring.boot.presentation.filter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.lyra.common.domain.presentation.FilterInvalidException
import com.lyra.common.domain.presentation.filter.RHSFilterParser
import com.lyra.spring.boot.entity.Person
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.test.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows

class RHSFilterParserTest {
    private data class TestCase(
        val query: Map<KProperty1<Person, *>, Collection<String?>>,
        val sql: String? = null,
        val exception: KClass<out Exception>? = null
    )

    private val objectMapper = jacksonObjectMapper()
    private val rhsFilterParser = RHSFilterParser(Person::class, objectMapper)

    @Test
    fun `base parse with AND operation`() {
        val testCases = listOf(
            TestCase(
                query = mapOf(
                    Person::name to listOf("ne:test"),
                ),
                sql = "(name != test)",
            ),
            TestCase(
                query = mapOf(
                    Person::name to listOf("eq:test"),
                ),
                sql = "(name = test)",
            ),
            TestCase(
                query = mapOf(
                    Person::name to listOf("lk:test"),
                ),
                sql = "(name LIKE test)",
            ),
            TestCase(
                query = mapOf(
                    Person::age to listOf("gt:0"),
                ),
                sql = "(age > 0)",
            ),
            TestCase(
                query = mapOf(
                    Person::age to listOf("gte:0"),
                ),
                sql = "(age >= 0)",
            ),
            TestCase(
                query = mapOf(
                    Person::age to listOf("lt:0"),
                ),
                sql = "(age < 0)",
            ),
            TestCase(
                query = mapOf(
                    Person::age to listOf("lte:0"),
                ),
                sql = "(age <= 0)",
            ),
            TestCase(
                query = mapOf(
                    Person::name to listOf("eq:test"),
                    Person::age to listOf("gte:0"),
                ),
                sql = "(name = test AND age >= 0)",
            ),
            TestCase(
                query = mapOf(
                    Person::age to listOf("not_found:0"),
                ),
                exception = FilterInvalidException::class,
            ),
            TestCase(
                query = mapOf(
                    Person::age to listOf("eq:invalid"),
                ),
                exception = FilterInvalidException::class,
            ),
        )

        testCases.forEach {
            if (it.sql != null) {
                val criteria = rhsFilterParser.parse(it.query)
                assertEquals(it.sql, criteria.toString())
            }
            if (it.exception != null) {
                assertThrows(it.exception.java) { rhsFilterParser.parse(it.query) }
            }
        }
    }

    @Test
    fun `base parse with OR operation`() {
        val testCases = listOf(
            TestCase(
                query = mapOf(
                    Person::name to listOf("ne:test", "eq:test"),
                ),
                sql = "(name != test OR name = test)",
            ),
            TestCase(
                query = mapOf(
                    Person::name to listOf("ne:test", "eq:test"),
                    Person::age to listOf("gte:0"),
                ),
                sql = "(name != test OR name = test OR age >= 0)",
            ),
            TestCase(
                query = mapOf(
                    Person::name to listOf("ne:test", "eq:test"),
                    Person::age to listOf("gte:0", "lte:0"),
                ),
                sql = "(name != test OR name = test OR age >= 0 OR age <= 0)",
            ),
            TestCase(
                query = mapOf(
                    Person::age to listOf("eq:invalid"),
                ),
                exception = FilterInvalidException::class,
            ),
        )

        testCases.forEach {
            if (it.sql != null) {
                val criteria = rhsFilterParser.parse(it.query, useOr = true)
                assertEquals(it.sql, criteria.toString())
            }
            if (it.exception != null) {
                assertThrows(it.exception.java) { rhsFilterParser.parse(it.query) }
            }
        }
    }
}
