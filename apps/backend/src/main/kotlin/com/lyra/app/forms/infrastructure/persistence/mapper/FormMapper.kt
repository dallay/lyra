package com.lyra.app.forms.infrastructure.persistence.mapper

import com.lyra.app.forms.domain.Form
import com.lyra.app.forms.domain.FormId
import com.lyra.app.forms.domain.HexColor
import com.lyra.app.forms.infrastructure.persistence.entity.FormEntity

object FormMapper {
    fun Form.toEntity(): FormEntity = FormEntity(
        id = id.value,
        name = name,
        header = header,
        description = description,
        inputPlaceholder = inputPlaceholder,
        buttonText = buttonText,
        buttonColor = buttonColor.hex,
        backgroundColor = backgroundColor.hex,
        textColor = textColor.hex,
        buttonTextColor = buttonTextColor.hex,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    fun FormEntity.toDomain(): Form = Form(
        id = FormId(id),
        name = name,
        header = header,
        description = description,
        inputPlaceholder = inputPlaceholder,
        buttonText = buttonText,
        buttonColor = HexColor(buttonColor),
        backgroundColor = HexColor(backgroundColor),
        textColor = HexColor(textColor),
        buttonTextColor = HexColor(buttonTextColor),
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
