package com.lyra.app.config

import com.lyra.UnitTest
import com.lyra.common.domain.presentation.pagination.LogicalOperator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@UnitTest
internal class StringToFilterConditionConverterTest {

    private val converter = StringToFilterConditionConverter()

    @Test
    fun `converts AND condition correctly`() {
        val result = converter.convert("AND:gte:2023-03-12T23:00:00.000Z,lte:2023-03-17T23:00:00.000Z")
        assertEquals(LogicalOperator.AND, result.operator)
        assertEquals(listOf("gte:2023-03-12T23:00:00.000Z", "lte:2023-03-17T23:00:00.000Z"), result.values)
    }

    @Test
    fun `converts OR condition correctly`() {
        val result = converter.convert("OR:gte:2023-03-12T23:00:00.000Z,lte:2023-03-17T23:00:00.000Z")
        assertEquals(LogicalOperator.OR, result.operator)
        assertEquals(listOf("gte:2023-03-12T23:00:00.000Z", "lte:2023-03-17T23:00:00.000Z"), result.values)
    }

    @Test
    fun `converts default AND condition when no operator is specified`() {
        val result = converter.convert("gte:2023-03-12T23:00:00.000Z,lte:2023-03-17T23:00:00.000Z")
        assertEquals(LogicalOperator.AND, result.operator)
        assertEquals(listOf("gte:2023-03-12T23:00:00.000Z", "lte:2023-03-17T23:00:00.000Z"), result.values)
    }

    @Test
    fun `converts single value correctly`() {
        val result = converter.convert("gte:2023-03-12T23:00:00.000Z")
        assertEquals(LogicalOperator.AND, result.operator)
        assertEquals(listOf("gte:2023-03-12T23:00:00.000Z"), result.values)
    }

    @Test
    fun `handles empty string correctly`() {
        val result = converter.convert("")
        assertEquals(LogicalOperator.AND, result.operator)
        assertEquals(listOf(""), result.values)
    }
}
