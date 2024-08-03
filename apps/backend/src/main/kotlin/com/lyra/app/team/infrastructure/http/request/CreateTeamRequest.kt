package com.lyra.app.team.infrastructure.http.request

import jakarta.validation.constraints.NotBlank

/**
 * This data class represents the request body for creating a team.
 *
 * @property organizationId The ID of the organization to which the team belongs. This field is mandatory.
 * @property name The name of the team to be created. This field is mandatory.
 */
data class CreateTeamRequest(
    @field:NotBlank(message = "Organization Id is required")
    val organizationId: String,
    @field:NotBlank(message = "Name is required")
    val name: String
)
