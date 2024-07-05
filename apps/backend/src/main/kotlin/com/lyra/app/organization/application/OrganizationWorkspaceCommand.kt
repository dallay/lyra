package com.lyra.app.organization.application

import com.lyra.common.domain.bus.command.Command

/**
 * Represents a command to create an organization.
 *
 * @property id Unique identifier for the organization.
 * @property name Name of the organization.
 * @property userId The user ID of the organization creator.
 */
data class OrganizationWorkspaceCommand(
    val id: String,
    val name: String,
    val userId: String,
) : Command
