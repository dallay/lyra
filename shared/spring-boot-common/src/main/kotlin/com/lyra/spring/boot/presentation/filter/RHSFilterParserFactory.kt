package com.lyra.spring.boot.presentation.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.lyra.common.domain.presentation.filter.RHSFilterParser
import kotlin.reflect.KClass
import org.springframework.stereotype.Component

@Component
class RHSFilterParserFactory(
    private val objectMapper: ObjectMapper,
) {
    fun <T : Any> create(clazz: KClass<T>): RHSFilterParser<T> = RHSFilterParser(clazz, objectMapper)
}

inline fun <reified T : Any> RHSFilterParserFactory.create(): RHSFilterParser<T> =
    this.create(T::class)
