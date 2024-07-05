package com.lyra.app.team.application.find.all

import com.lyra.app.organization.domain.OrganizationId
import com.lyra.app.team.application.TeamResponses
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.query.QueryHandler
import org.slf4j.LoggerFactory

/**
 * This class represents a query to find all teams.
 * @property finder The [AllTeamFinder] to find all teams.
 */
@Service
class AllTeamsQueryHandler(
    private val finder: AllTeamFinder
) : QueryHandler<AllTeamQuery, TeamResponses> {
    /**
     * Handles a query
     *
     * @param query the query to handle
     * @return the response to the query handled
     */
    override suspend fun handle(query: AllTeamQuery): TeamResponses {
        log.debug("Finding all teams that belong to organization with id: {}", query.organizationId)
        return finder.findAllTeamsThatBelongToOrganization(OrganizationId(query.organizationId))
    }

    companion object {
        private val log = LoggerFactory.getLogger(AllTeamsQueryHandler::class.java)
    }
}
