package com.lyra.app.team.application.delete

import com.lyra.common.domain.bus.command.Command

/**
 * Command to delete a team.
 * @property teamId The unique identifier of the team.
 */
data class DeleteTeamCommand(
    val teamId: String
) : Command
