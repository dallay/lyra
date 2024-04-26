package com.lyra.app.forms

import com.lyra.app.GeneralStub.getTimestampCursorPage
import com.lyra.app.forms.application.FormResponse
import com.lyra.app.forms.domain.Form
import com.lyra.app.forms.domain.FormId
import com.lyra.app.forms.domain.HexColor
import com.lyra.app.forms.domain.dto.FormDTO
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.common.domain.presentation.pagination.TimestampCursor
import java.util.*
import net.datafaker.Faker

object FormStub {
    private val faker = Faker()

    fun create(
        id: String = UUID.randomUUID().toString(),
        dto: FormDTO = FormDTO(
            name = faker.lorem().words(3).joinToString(" "),
            header = faker.lorem().words(3).joinToString(" "),
            description = faker.lorem().words(10).joinToString(" "),
            inputPlaceholder = faker.lorem().words(3).joinToString(" "),
            buttonText = faker.lorem().words(3).joinToString(" "),
            buttonColor = faker.color().hex(),
            backgroundColor = faker.color().hex(),
            textColor = faker.color().hex(),
            buttonTextColor = faker.color().hex(),
        )
    ): Form = Form(
        id = FormId(id),
        name = dto.name,
        header = dto.header,
        description = dto.description,
        inputPlaceholder = dto.inputPlaceholder,
        buttonText = dto.buttonText,
        buttonColor = HexColor(dto.buttonColor),
        backgroundColor = HexColor(dto.backgroundColor),
        textColor = HexColor(dto.textColor),
        buttonTextColor = HexColor(dto.buttonTextColor),
    )

    @Suppress("MultilineRawStringIndentation")
    fun generateRequest(
        dto: FormDTO = FormDTO(
            name = faker.lorem().words(3).joinToString(" "),
            header = faker.lorem().words(3).joinToString(" "),
            description = faker.lorem().words(10).joinToString(" "),
            inputPlaceholder = faker.lorem().words(3).joinToString(" "),
            buttonText = faker.lorem().words(3).joinToString(" "),
            buttonColor = faker.color().hex(),
            backgroundColor = faker.color().hex(),
            textColor = faker.color().hex(),
            buttonTextColor = faker.color().hex(),
        )
    ): String = """
      {
        "name": "${dto.name}",
        "header": "${dto.header}",
        "description": "${dto.description}",
        "inputPlaceholder": "${dto.inputPlaceholder}",
        "buttonText": "${dto.buttonText}",
        "buttonColor": "${dto.buttonColor}",
        "backgroundColor": "${dto.backgroundColor}",
        "textColor": "${dto.textColor}",
        "buttonTextColor": "${dto.buttonTextColor}"
      }
    """.trimIndent()

    fun dummyRandomFormPageResponse(size: Int): CursorPageResponse<FormResponse> {
        val data = (1..size).map { FormResponse.from(create()) }
        val (_, cursor) = getStartAndEndTimestampCursorPage(data)
        return CursorPageResponse(
            data = data,
            nextPageCursor = cursor,
        )
    }

    private fun getStartAndEndTimestampCursorPage(data: List<FormResponse>): Pair<String, String> {
        val startCreatedAt = data.first().createdAt
        val startCursor = startCreatedAt?.let { getTimestampCursorPage(it) }
            ?: TimestampCursor.DEFAULT_CURSOR.serialize()
        val lastCreatedAt = data.last().createdAt
        val endCursor = lastCreatedAt?.let { getTimestampCursorPage(it) }
            ?: TimestampCursor.DEFAULT_CURSOR.serialize()
        return Pair(startCursor, endCursor)
    }

    fun dummyRandomFormsPageResponse(size: Int): CursorPageResponse<Form> {
        val data = (1..size).map { create() }
        val cursor = TimestampCursor(data.last().createdAt).serialize()
        return CursorPageResponse(
            data = data,
            nextPageCursor = cursor,
        )
    }
}
