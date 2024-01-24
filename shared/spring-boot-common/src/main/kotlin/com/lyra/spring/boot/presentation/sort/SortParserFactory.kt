package com.lyra.spring.boot.presentation.sort

import com.fasterxml.jackson.databind.ObjectMapper
import kotlin.reflect.KClass
import org.springframework.stereotype.Component

@Component
class SortParserFactory(
    private val objectMapper: ObjectMapper
) {
    fun <T : Any> create(clazz: KClass<T>): SortParser<T> = SortParser(clazz, objectMapper)
}

inline fun <reified T : Any> SortParserFactory.create(): SortParser<T> = create(T::class)
