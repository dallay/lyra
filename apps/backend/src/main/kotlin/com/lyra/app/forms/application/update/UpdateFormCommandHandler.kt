package com.lyra.app.forms.application.update

import com.lyra.app.forms.domain.Form
import com.lyra.app.forms.domain.FormId
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

@Service
class UpdateFormCommandHandler(
    private val formCreator: FormUpdater
) : CommandHandler<UpdateFormCommand> {
    override suspend fun handle(command: UpdateFormCommand) {
        log.info("Updating form with name: ${command.name}")
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
        formCreator.update(form)
    }
    companion object {
        private val log = LoggerFactory.getLogger(UpdateFormCommandHandler::class.java)
    }
}
