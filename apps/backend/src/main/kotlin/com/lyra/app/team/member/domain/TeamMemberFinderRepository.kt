package com.lyra.app.team.member.domain

import com.lyra.app.team.member.application.OrganizationTeamMemberResponses
import com.lyra.app.users.domain.UserId

/**
 * Repository to find team members.
 */
interface TeamMemberFinderRepository {
    /**
     * Find all team members.
     * @return A list of all team members.
     */
    suspend fun findAllTeamMembers(userId: UserId): OrganizationTeamMemberResponses
}
