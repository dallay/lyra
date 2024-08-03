package com.lyra.app.team.domain

import com.lyra.app.organization.domain.OrganizationId

/**
 * Repository to find team.
 */
interface TeamFinderRepository {
    /**
     * Find an organization by its unique identifier.
     *
     * @param id The unique identifier of the team.
     * @return The team with the given unique identifier.
     */
    suspend fun findById(id: TeamId): Team?

    /**
     * Find all team that belong to an organization.
     * @param organizationId The unique identifier of the organization.
     *
     * @return The list of teams that belong to the organization.
     */
    suspend fun findAllTeamsThatBelongToOrganization(organizationId: OrganizationId): List<Team>
}
