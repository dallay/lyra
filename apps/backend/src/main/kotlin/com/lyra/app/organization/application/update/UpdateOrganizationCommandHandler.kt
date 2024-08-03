package com.lyra.app.organization.application.update

import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 * This class is responsible for handling the update organization command.
 * It uses the [OrganizationUpdater] service to perform the update operation.
 *
 * @property organizationUpdater The service used to update the organization.
 */
@Service
class UpdateOrganizationCommandHandler(
    private val organizationUpdater: OrganizationUpdater
) : CommandHandler<UpdateOrganizationCommand> {

    /**
     * This method handles the update organization command.
     * It logs the operation and delegates the update operation to the [OrganizationUpdater] service.
     *
     * @param command The [UpdateOrganizationCommand] that triggers the update operation.
     */
    override suspend fun handle(command: UpdateOrganizationCommand) {
        log.debug("Updating organization with id: ${command.id}")
        organizationUpdater.update(command.id, command.name)
    }

    companion object {
        private val log = LoggerFactory.getLogger(UpdateOrganizationCommandHandler::class.java)
    }
}
