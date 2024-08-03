package com.lyra.app.organization.application.delete

import com.lyra.common.domain.bus.command.Command

/**
 * Represents a command to delete an organization.
 *
 * @property id Unique identifier for the organization.
 */
data class DeleteOrganizationCommand(
    val id: String
) : Command
