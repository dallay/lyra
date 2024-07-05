package com.lyra.app.organization.infrastructure.http.request

import jakarta.validation.constraints.NotBlank

/**
 * This data class represents the request body for creating an organization.
 *
 * @property name The name of the organization to be created. This field is mandatory.
 * @property userId The ID of the user who is creating the organization. This field is mandatory.
 */
data class CreateOrganizationRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,
    @field:NotBlank(message = "User Id is required")
    val userId: String,
)
