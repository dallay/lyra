package com.lyra.app.forms

import com.lyra.app.forms.domain.Form
import com.lyra.app.forms.domain.FormId
import com.lyra.app.forms.domain.HexColor
import com.lyra.app.forms.domain.dto.FormDTO
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
}
