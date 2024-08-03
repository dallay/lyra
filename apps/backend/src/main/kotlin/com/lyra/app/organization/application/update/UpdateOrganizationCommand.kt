package com.lyra.app.organization.application.update

import com.lyra.common.domain.bus.command.Command

/**
 * Represents a command to update an organization.
 * @property id The unique identifier of the organization.
 * @property name The new name of the organization.
 */
data class UpdateOrganizationCommand(val id: String, val name: String) : Command
