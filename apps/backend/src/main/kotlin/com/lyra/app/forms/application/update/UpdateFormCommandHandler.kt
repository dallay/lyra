package com.lyra.app.forms.application.update

import com.lyra.app.forms.domain.FormId
import com.lyra.app.forms.domain.dto.FormStyleConfiguration
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 * Service class responsible for handling form update commands.
 *
 * @property formCreator The service for updating forms.
 */
@Service
class UpdateFormCommandHandler(
    private val formCreator: FormUpdater
) : CommandHandler<UpdateFormCommand> {

    /**
     * Handles a form update command.
     * Logs the name of the form being updated, creates a FormDTO from the command, and calls the form updater service.
     *
     * @param command The form update command to handle.
     */
    override suspend fun handle(command: UpdateFormCommand) {
        log.debug("Updating form with name: ${command.name}")
        val formStyleConfiguration = FormStyleConfiguration(
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
        formCreator.update(
            OrganizationId(command.organizationId),
            FormId(command.id), formStyleConfiguration,
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(UpdateFormCommandHandler::class.java)
    }
}
