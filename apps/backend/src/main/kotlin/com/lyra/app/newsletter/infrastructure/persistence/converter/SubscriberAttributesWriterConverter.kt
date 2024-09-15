package com.lyra.app.newsletter.infrastructure.persistence.converter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.lyra.app.newsletter.domain.Attributes
import io.r2dbc.postgresql.codec.Json
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class SubscriberAttributesWriterConverter : Converter<Attributes, Json> {
    private val objectMapper = jacksonObjectMapper()

    override fun convert(source: Attributes): Json {
        val jsonString = objectMapper.writeValueAsString(source)
        return Json.of(jsonString)
    }
}
