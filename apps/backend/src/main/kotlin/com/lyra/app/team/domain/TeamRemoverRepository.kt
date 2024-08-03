package com.lyra.app.team.domain

interface TeamRemoverRepository {
    /**
     * Removes a team.
     * @param teamId The unique identifier of the team to be removed.
     */
    suspend fun delete(teamId: TeamId)
}
