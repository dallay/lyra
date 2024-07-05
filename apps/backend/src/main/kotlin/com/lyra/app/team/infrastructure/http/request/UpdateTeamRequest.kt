package com.lyra.app.team.infrastructure.http.request

import jakarta.validation.constraints.NotBlank

/**
 * This data class represents the request body for updating a team.
 * @property name The name of the team to be updated. This field is mandatory.
 */
data class UpdateTeamRequest(
    @field:NotBlank
    val name: String,
)
