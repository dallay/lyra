package com.lyra.app.team.application.find

import com.lyra.app.team.domain.TeamFinderRepository
import com.lyra.app.team.domain.TeamId
import com.lyra.common.domain.Service

/**
 * This is a service class that handles the finding of teams.
 * It uses a [TeamFinderRepository] to find an team by its ID.
 *
 * @property finder [TeamFinderRepository] The repository to use for finding teams.
 */
@Service
class TeamFinder(private val finder: TeamFinderRepository) {

    /**
     * This function is used to find an team by its ID.
     * It is a suspending function, meaning it can be paused and resumed at a later time.
     * This makes it suitable for use in a coroutine context, where it can be used for non-blocking IO operations.
     *
     * @param id The ID of the team to find.
     * @return The team found, or null if no team was found with the given ID.
     */
    suspend fun find(id: TeamId) = finder.findById(id)
}
