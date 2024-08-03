package com.lyra.app.team.member.application

import com.lyra.common.domain.bus.command.Command

/**
 * Command to add a team member to a team.
 * @property teamId The unique identifier of the team.
 * @property userId The unique identifier of the user.
 * @property role The role of the user in the team.
 */
data class AddTeamMemberCommand(
    val teamId: String,
    val userId: String,
    val role: String
) : Command
