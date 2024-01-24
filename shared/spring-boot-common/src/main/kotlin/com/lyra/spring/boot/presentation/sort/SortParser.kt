package com.lyra.spring.boot.presentation.sort

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.introspect.AnnotatedField
import com.lyra.common.domain.presentation.SortInvalidException
import com.lyra.common.domain.presentation.sort.Sort
import com.lyra.spring.boot.repository.columnName
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

class SortParser<T : Any>(
    private val clazz: KClass<T>,
    private val objectMapper: ObjectMapper
) {
    private val regex = Regex("(.[^:]+):(.+)")

    fun parse(sort: Collection<String>): Sort {
        return sort
            .map { parse(it) }
            .reduce { acc, cur -> acc.and(cur) }
    }
    fun parse(sort: String): Sort {
        try {
            val result = regex.find(sort) ?: throw SortInvalidException()
            val (direction, property) = result.destructured

            val memberProperty = clazz.memberProperties.find {
                exportedPropertyName(it) == property
            } ?: throw SortInvalidException()

            return Sort.by(
                Sort.DEFAULT_DIRECTION.fromString(direction),
                columnName(memberProperty),
            )
        } catch (e: IllegalArgumentException) {
            throw SortInvalidException(e.message, e)
        }
    }

    private fun exportedPropertyName(property: KProperty<*>): String {
        return objectMapper.propertyNamingStrategy?.nameForField(
            objectMapper.serializationConfig,
            AnnotatedField(null, property.javaField, null),
            property.name,
        ) ?: property.name
    }
}
