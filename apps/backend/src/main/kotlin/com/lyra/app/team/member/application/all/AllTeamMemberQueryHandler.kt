package com.lyra.app.team.member.application.all

import com.lyra.app.team.member.application.OrganizationTeamMemberResponses
import com.lyra.app.users.domain.UserId
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.query.QueryHandler
import org.slf4j.LoggerFactory

/**
 * Query handler for retrieving all team members.
 *
 * @property finder The service used to find team members.
 */
@Service
class AllTeamMemberQueryHandler(private val finder: AllTeamMemberFinder) :
    QueryHandler<GetAllTeamMember, OrganizationTeamMemberResponses> {

    /**
     * Handles the query to get all team members.
     *
     * @param query The query to get all team members.
     * @return The response containing all organization team members.
     */
    override suspend fun handle(query: GetAllTeamMember): OrganizationTeamMemberResponses {
        log.debug("Finding all team members")
        return finder.findAllTeamMembers(UserId(query.userId))
    }

    companion object {
        private val log = LoggerFactory.getLogger(AllTeamMemberQueryHandler::class.java)
    }
}
