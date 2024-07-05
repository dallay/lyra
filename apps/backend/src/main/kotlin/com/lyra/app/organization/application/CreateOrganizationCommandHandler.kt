package com.lyra.app.organization.application

import com.lyra.app.organization.domain.Organization
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import java.util.*
import org.slf4j.LoggerFactory

/**
 * [CreateOrganizationCommandHandler] is a class responsible for handling the creation of organization.
 * It implements the [CommandHandler] interface with [OrganizationCommand] as the command type.
 *
 * @property organizationCreator The [OrganizationCreator] used to create organization.
 */
@Service
class CreateOrganizationCommandHandler(
    private val organizationCreator: OrganizationCreator
) : CommandHandler<OrganizationCommand> {

    /**
     * Handles the creation of an organization.
     * It logs the creation process, creates a new organization using the [OrganizationCreator],
     * and then creates the organization.
     *
     * @param command The [OrganizationCommand] containing the information needed to create an organization.
     */
    override suspend fun handle(command: OrganizationCommand) {
        log.debug("Creating organization with name: ${command.name}")
        val organization = Organization.create(
            id = UUID.fromString(command.id),
            name = command.name,
            userId = UUID.fromString(command.userId),
        )
        organizationCreator.create(organization)
    }

    companion object {
        private val log = LoggerFactory.getLogger(CreateOrganizationCommandHandler::class.java)
    }
}
