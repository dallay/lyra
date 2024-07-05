package com.lyra.app.team.member.domain

import com.lyra.app.team.domain.TeamId
import com.lyra.app.users.domain.UserId

/**
 * Repository to remove a team member.
 */
interface TeamMemberRemoverRepository {
    /**
     * Removes a team member. A team member is a user that belongs to a team.
     * @param teamId The unique identifier of the team to which the user belongs.
     * @param userId The unique identifier of the user to be removed.
     */
    suspend fun remove(teamId: TeamId, userId: UserId)
}
