package com.lyra.app.team.member.domain

import com.lyra.app.team.domain.Team
import com.lyra.app.team.domain.TeamId

/**
 * Repository to find team members.
 */
interface TeamMemberFinderRepository {
    /**
     * Find a team member by its unique identifier.
     * @param id The unique identifier of the team member.
     * @return The team member with the given unique identifier.
     */
    suspend fun findById(id: TeamId): Team?

    /**
     * Find all team members.
     * @return A list of all team members.
     */
    suspend fun findAll(): List<Team>
}
