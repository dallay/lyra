package com.lyra.app.team.member.application.remove

import com.lyra.common.domain.bus.command.Command

/**
 * Command to add a team member to a team.
 * @property teamId The unique identifier of the team.
 * @property userId The unique identifier of the user.
 */
data class RemoveTeamMemberCommand(
    val teamId: String,
    val userId: String
) : Command
