package com.lyra.app.forms.application

import com.lyra.app.forms.domain.Form
import com.lyra.app.forms.domain.FormId
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler

@Service
class CreateFormCommandHandler(
    private val formCreator: FormCreator
) : CommandHandler<CreateFormCommand> {
    override suspend fun handle(command: CreateFormCommand) {
        val form = Form(
            id = FormId(command.id),
            name = command.name,
            header = command.header,
            description = command.description,
            inputPlaceholder = command.inputPlaceholder,
            buttonText = command.buttonText,
            buttonColor = command.buttonColor,
            backgroundColor = command.backgroundColor,
            textColor = command.textColor,
            buttonTextColor = command.buttonTextColor,
        )
        formCreator.create(form)
    }
}
