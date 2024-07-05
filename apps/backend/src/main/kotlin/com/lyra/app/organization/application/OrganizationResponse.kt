package com.lyra.app.organization.application

import com.lyra.app.organization.domain.Organization
import com.lyra.common.domain.bus.query.Response

/**
 * Represents an organization response.
 */
data class OrganizationResponse(
    val id: String,
    val name: String,
    val userId: String,
    val createdAt: String,
    val updatedAt: String?
) : Response {
    companion object {
        fun from(organization: Organization) = OrganizationResponse(
            id = organization.id.value.toString(),
            name = organization.name,
            userId = organization.userId.value.toString(),
            createdAt = organization.createdAt.toString(),
            updatedAt = organization.updatedAt?.toString(),
        )
    }
}

/**
 * Represents a list of organization responses.
 */
data class OrganizationResponses(val data: List<OrganizationResponse>) : Response {
    companion object {
        fun from(organizations: List<Organization>) = OrganizationResponses(
            data = organizations.map { OrganizationResponse.from(it) },
        )
    }
}
