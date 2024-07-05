package com.lyra.app.team.application

import com.lyra.common.domain.bus.command.Command

/**
 * Represents a command to create a team.
 *
 * @property teamId The unique identifier of the team.
 * @property organizationId The unique identifier of the organization to which the team belongs.
 * @property name The name of the team.
 */
data class CreateTeamCommand(
    val teamId: String,
    val organizationId: String,
    val name: String
) : Command
