package com.lyra.app.organization.application.find

import com.lyra.app.organization.application.OrganizationResponse
import com.lyra.common.domain.bus.query.Query

/**
 * Represents a query to find an organization by its ID.
 *
 * @property id The ID of the organization to find.
 */
data class FindOrganizationQuery(
    val id: String
) : Query<OrganizationResponse> {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FindOrganizationQuery) return false
        return true
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
