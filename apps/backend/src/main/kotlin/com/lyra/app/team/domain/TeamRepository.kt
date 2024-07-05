package com.lyra.app.team.domain

/**
 * Represents a team. A team is a group of users that belong to an organization.
 */
interface TeamRepository {
    /**
     * Create a new team.
     *
     * @param team The team to be created.
     */
    suspend fun create(team: Team)
    /**
     * Updates a team.
     *
     * @param team The team to be updated.
     */
    suspend fun update(team: Team)
}
