package com.lyra.app.team.member.application.all

import com.lyra.app.team.member.application.OrganizationTeamMemberResponses
import com.lyra.app.team.member.domain.TeamMemberFinderRepository
import com.lyra.app.users.domain.UserId
import com.lyra.common.domain.Service
import org.slf4j.LoggerFactory

/**
 * Service for finding team members.
 *
 * @property finder The repository used to find team members.
 */
@Service
class AllTeamMemberFinder(private val finder: TeamMemberFinderRepository) {

    suspend fun findAllTeamMembers(userId: UserId): OrganizationTeamMemberResponses {
        log.debug("Finding all organization team members")
        return finder.findAllTeamMembers(userId)
    }

    companion object {
        private val log = LoggerFactory.getLogger(AllTeamMemberFinder::class.java)
    }
}
