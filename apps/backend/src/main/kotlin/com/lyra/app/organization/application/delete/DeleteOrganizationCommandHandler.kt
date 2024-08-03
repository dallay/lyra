package com.lyra.app.organization.application.delete

import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 * This service class is responsible for handling the deletion of organizaitons.
 * It implements the CommandHandler interface with [DeleteOrganizationCommand] as the command type.
 *
 * @property organizationDestroyer An instance of [OrganizationDestroyer] used to delete the organization.
 */
@Service
class DeleteOrganizationCommandHandler(
    private val organizationDestroyer: OrganizationDestroyer
) : CommandHandler<DeleteOrganizationCommand> {

    /**
     * Handles the deletion of an organization.
     * Logs the id of the organization being deleted and calls the organizationDestroyer's delete method.
     *
     * @param command The command object containing the id of the organization to be deleted.
     */
    override suspend fun handle(command: DeleteOrganizationCommand) {
        log.debug("Deleting organization with id: ${command.id}")
        organizationDestroyer.delete(OrganizationId(command.id))
    }

    companion object {
        private val log = LoggerFactory.getLogger(DeleteOrganizationCommandHandler::class.java)
    }
}
