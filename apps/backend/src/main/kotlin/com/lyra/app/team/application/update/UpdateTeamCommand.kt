package com.lyra.app.team.application.update

import com.lyra.common.domain.bus.command.Command

/**
 * Represents a command to update a team.
 *
 * @property teamId The unique identifier of the team.
 * @property name The name of the team.
 */
data class UpdateTeamCommand(
    val teamId: String,
    val name: String
) : Command
