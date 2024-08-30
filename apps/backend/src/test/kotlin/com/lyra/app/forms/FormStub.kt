package com.lyra.app.forms

import com.lyra.app.GeneralStub.getTimestampCursorPage
import com.lyra.app.forms.application.FormResponse
import com.lyra.app.forms.domain.Form
import com.lyra.app.forms.domain.FormId
import com.lyra.app.forms.domain.HexColor
import com.lyra.app.forms.domain.dto.FormStyleConfiguration
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.common.domain.presentation.pagination.TimestampCursor
import java.util.*
import net.datafaker.Faker

object FormStub {
    private val faker = Faker()

    fun create(
        id: String = UUID.randomUUID().toString(),
        dto: FormStyleConfiguration = FormStyleConfiguration(
            name = faker.lorem().words(3).joinToString(" "),
            header = faker.lorem().words(3).joinToString(" "),
            description = faker.lorem().words(10).joinToString(" "),
            inputPlaceholder = faker.lorem().words(3).joinToString(" "),
            buttonText = faker.lorem().words(3).joinToString(" "),
            buttonColor = faker.color().hex(),
            backgroundColor = faker.color().hex(),
            textColor = faker.color().hex(),
            buttonTextColor = faker.color().hex(),
        ),
        organizationId: String = UUID.randomUUID().toString(),
    ): Form = create(
        id = FormId(id), dto = dto, organizationId = OrganizationId(organizationId),
    )

    fun create(
        id: FormId = FormId.create(),
        dto: FormStyleConfiguration = FormStyleConfiguration(
            name = faker.lorem().words(3).joinToString(" "),
            header = faker.lorem().words(3).joinToString(" "),
            description = faker.lorem().words(10).joinToString(" "),
            inputPlaceholder = faker.lorem().words(3).joinToString(" "),
            buttonText = faker.lorem().words(3).joinToString(" "),
            buttonColor = faker.color().hex(),
            backgroundColor = faker.color().hex(),
            textColor = faker.color().hex(),
            buttonTextColor = faker.color().hex(),
        ),
        organizationId: OrganizationId = OrganizationId.create(),
    ): Form = Form(
        id = id,
        name = dto.name,
        header = dto.header,
        description = dto.description,
        inputPlaceholder = dto.inputPlaceholder,
        buttonText = dto.buttonText,
        buttonColor = HexColor(dto.buttonColor),
        backgroundColor = HexColor(dto.backgroundColor),
        textColor = HexColor(dto.textColor),
        buttonTextColor = HexColor(dto.buttonTextColor),
        organizationId = organizationId,
    )

    @Suppress("MultilineRawStringIndentation")
    fun generateRequest(
        styleConfiguration: FormStyleConfiguration = FormStyleConfiguration(
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
        "name": "${styleConfiguration.name}",
        "header": "${styleConfiguration.header}",
        "description": "${styleConfiguration.description}",
        "inputPlaceholder": "${styleConfiguration.inputPlaceholder}",
        "buttonText": "${styleConfiguration.buttonText}",
        "buttonColor": "${styleConfiguration.buttonColor}",
        "backgroundColor": "${styleConfiguration.backgroundColor}",
        "textColor": "${styleConfiguration.textColor}",
        "buttonTextColor": "${styleConfiguration.buttonTextColor}"
      }
    """.trimIndent()

    fun dummyRandomFormPageResponse(size: Int): CursorPageResponse<FormResponse> {
        val data = (1..size).map { FormResponse.from(create(id = FormId.create())) }
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
        val data = (1..size).map { generateRandomForm() }
        val cursor = TimestampCursor(data.last().createdAt).serialize()
        return CursorPageResponse(
            data = data,
            nextPageCursor = cursor,
        )
    }

    fun generateRandomForm(): Form = create(id = FormId.create())
}
