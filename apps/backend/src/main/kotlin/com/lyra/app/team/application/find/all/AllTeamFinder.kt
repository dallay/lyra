package com.lyra.app.team.application.find.all

import com.lyra.app.organization.domain.OrganizationId
import com.lyra.app.team.application.TeamResponses
import com.lyra.app.team.domain.TeamFinderRepository
import com.lyra.common.domain.Service
import org.slf4j.LoggerFactory

/**
 * Query to find all teams of an organization.
 * @property finder The repository to find teams.
 */
@Service
class AllTeamFinder(private val finder: TeamFinderRepository) {

    /**
     * Find all teams that belong to an organization.
     * @param organizationId The unique identifier of the organization.
     *
     * @return The [TeamResponses] with the list of teams that belong to the organization.
     */
    suspend fun findAllTeamsThatBelongToOrganization(organizationId: OrganizationId): TeamResponses {
        log.debug("Finding all teams that belong to organization with id: {}", organizationId)
        val all = finder.findAllTeamsThatBelongToOrganization(organizationId)
        return TeamResponses.from(all)
    }

    companion object {
        private val log = LoggerFactory.getLogger(AllTeamFinder::class.java)
    }
}
