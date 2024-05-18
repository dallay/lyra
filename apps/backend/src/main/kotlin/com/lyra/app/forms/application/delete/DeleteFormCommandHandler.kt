package com.lyra.app.forms.application.delete

import com.lyra.app.forms.domain.FormId
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 * This service class is responsible for handling the deletion of forms.
 * It implements the CommandHandler interface with DeleteFormCommand as the command type.
 *
 * @property formDestroyer An instance of FormDestroyer used to delete the form.
 */
@Service
class DeleteFormCommandHandler(
    private val formDestroyer: FormDestroyer
) : CommandHandler<DeleteFormCommand> {

    /**
     * Handles the deletion of a form.
     * Logs the id of the form being deleted and calls the formDestroyer's delete method.
     *
     * @param command The command object containing the id of the form to be deleted.
     */
    override suspend fun handle(command: DeleteFormCommand) {
        log.debug("Deleting form with id: ${command.id}")
        formDestroyer.delete(FormId(command.id))
    }

    companion object {
        private val log = LoggerFactory.getLogger(DeleteFormCommandHandler::class.java)
    }
}
