package com.lyra.app.forms.domain

import com.lyra.app.forms.domain.dto.FormDTO
import com.lyra.app.forms.domain.event.FormCreatedEvent
import com.lyra.app.forms.domain.event.FormUpdatedEvent
import com.lyra.common.domain.BaseEntity
import java.time.LocalDateTime

/**
 * Data class representing a form.
 *
 * @property id The unique identifier of the form.
 * @property name The name of the form.
 * @property header The header of the form.
 * @property description The description of the form.
 * @property inputPlaceholder The input placeholder of the form.
 * @property buttonText The text of the button.
 * @property buttonColor The color of the button.
 * @property backgroundColor The background color of the form.
 * @property textColor The text color of the form.
 * @property buttonTextColor The text color of the button.
 * @property createdAt The creation time of the form.
 * @property updatedAt The last update time of the form.
 */
data class Form(
    override val id: FormId,
    var name: String,
    var header: String,
    var description: String,
    var inputPlaceholder: String,
    var buttonText: String,
    var buttonColor: HexColor,
    var backgroundColor: HexColor,
    var textColor: HexColor,
    var buttonTextColor: HexColor,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime? = createdAt,
) : BaseEntity<FormId>() {

    /**
     * Updates the form with the given DTO and records a FormUpdatedEvent.
     *
     * @param dto The DTO containing the new form data.
     */
    fun update(dto: FormDTO) {
        name = dto.name
        header = dto.header
        description = dto.description
        inputPlaceholder = dto.inputPlaceholder
        buttonText = dto.buttonText
        buttonColor = HexColor(dto.buttonColor)
        backgroundColor = HexColor(dto.backgroundColor)
        textColor = HexColor(dto.textColor)
        buttonTextColor = HexColor(dto.buttonTextColor)
        updatedAt = LocalDateTime.now()

        record(
            FormUpdatedEvent(
                id.toString(),
                dto.name,
                dto.header,
                dto.description,
                dto.inputPlaceholder,
                dto.buttonText,
                dto.buttonColor,
                dto.backgroundColor,
                dto.textColor,
                dto.buttonTextColor,
            ),
        )
    }

    companion object {
        /**
         * Creates a new form with the given DTO and records a FormCreatedEvent.
         *
         * @param id The id of the new form.
         * @param dto The DTO containing the new form data.
         * @param createdAt The creation time of the new form. Defaults to the current time.
         * @param updatedAt The last update time of the new form. Defaults to the creation time.
         * @return The newly created form.
         */
        fun create(
            id: FormId,
            dto: FormDTO,
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime? = createdAt
        ): Form {
            val form = Form(
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
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
            form.record(FormCreatedEvent(id.toString()))
            return form
        }
    }
}
