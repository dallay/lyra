package com.lyra.app.forms.application

import com.lyra.app.forms.domain.Form
import com.lyra.app.forms.domain.FormId
import com.lyra.app.forms.domain.dto.FormDTO
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 * Handles the creation of forms.
 *
 * @property formCreator Service responsible for creating forms.
 */
@Service
class CreateFormCommandHandler(
    private val formCreator: FormCreator
) : CommandHandler<CreateFormCommand> {

    /**
     * Handles the CreateFormCommand.
     *
     * It creates a form from the command and uses the formCreator to create the form.
     *
     * @param command The command to handle.
     */
    override suspend fun handle(command: CreateFormCommand) {
        log.debug("Creating form with name: ${command.name}")
        val form = Form.create(
            id = FormId(command.id),
            dto = FormDTO(
                name = command.name,
                header = command.header,
                description = command.description,
                inputPlaceholder = command.inputPlaceholder,
                buttonText = command.buttonText,
                buttonColor = command.buttonColor,
                backgroundColor = command.backgroundColor,
                textColor = command.textColor,
                buttonTextColor = command.buttonTextColor,
            ),
        )
        formCreator.create(form)
    }

    companion object {
        private val log = LoggerFactory.getLogger(CreateFormCommandHandler::class.java)
    }
}
