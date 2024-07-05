package com.lyra.app.organization.infrastructure.http.request

import jakarta.validation.constraints.NotBlank

/**
 * Request to update an organization.
 * @property name The name of the organization.
 */
data class UpdateOrganizationRequest(
    @field:NotBlank
    val name: String,
)
