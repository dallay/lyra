package com.lyra.app.forms.domain

import com.lyra.app.forms.domain.event.FormCreatedEvent
import com.lyra.common.domain.BaseEntity
import java.time.LocalDateTime

data class Form(
    override val id: FormId,
    val name: String,
    val header: String,
    val description: String,
    val inputPlaceholder: String,
    val buttonText: String,
    val buttonColor: HexColor,
    val backgroundColor: HexColor,
    val textColor: HexColor,
    val buttonTextColor: HexColor,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime? = createdAt,
) : BaseEntity<FormId>() {
    init {
        record(FormCreatedEvent(id.toString()))
    }
    constructor(
        name: String,
        header: String,
        description: String,
        inputPlaceholder: String,
        buttonText: String,
        buttonColor: String,
        backgroundColor: String,
        textColor: String,
        buttonTextColor: String,
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime? = createdAt,
    ) : this(
        id = FormId.create(),
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
    constructor(
        id: FormId,
        name: String,
        header: String,
        description: String,
        inputPlaceholder: String,
        buttonText: String,
        buttonColor: String,
        backgroundColor: String,
        textColor: String,
        buttonTextColor: String,
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime? = createdAt,
    ) : this(
        id = id,
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
