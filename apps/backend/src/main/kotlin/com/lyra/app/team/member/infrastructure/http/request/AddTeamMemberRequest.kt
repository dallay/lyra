package com.lyra.app.team.member.infrastructure.http.request

import jakarta.validation.constraints.NotBlank

/**
 * Request to add a team member to a team.
 * @property teamId The unique identifier of the team.
 * @property userId The unique identifier of the user.
 * @property role The role of the user in the team.
 */
data class AddTeamMemberRequest(
    @field:NotBlank(message = "Team Id is required")
    val teamId: String,
    @field:NotBlank(message = "User Id is required")
    val userId: String,
    @field:NotBlank(message = "Role is required")
    val role: String
)
