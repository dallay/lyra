package com.lyra.app.team.application.find

import com.lyra.app.team.application.TeamResponse
import com.lyra.app.team.domain.TeamId
import com.lyra.app.team.domain.exception.TeamNotFoundException
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.query.QueryHandler
import org.slf4j.LoggerFactory

/**
 * Service class responsible for handling team find queries.
 *
 * @property finder [TeamFinder] The service for finding teams.
 */
@Service
class FindTeamQueryHandler(
    private val finder: TeamFinder
) : QueryHandler<FindTeamQuery, TeamResponse> {

    /**
     * Handles an team find query.
     * Logs the id of the team being found, finds the team using the finder service,
     * and returns a [TeamResponse].
     * If the team is not found, a [TeamNotFoundException] is thrown.
     *
     * @param query The team find query to handle.
     * @return The [TeamResponse] for the found team.
     * @throws [TeamNotFoundException] If the team is not found.
     */
    override suspend fun handle(query: FindTeamQuery): TeamResponse {
        log.debug("Finding team with id: ${query.id}")
        val teamId = TeamId(query.id)
        val team = finder.find(teamId) ?: throw TeamNotFoundException("Team not found")
        return TeamResponse.from(team)
    }

    companion object {
        private val log = LoggerFactory.getLogger(FindTeamQueryHandler::class.java)
    }
}
