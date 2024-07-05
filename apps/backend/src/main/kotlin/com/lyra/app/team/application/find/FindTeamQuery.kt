package com.lyra.app.team.application.find

import com.lyra.app.team.application.TeamResponse
import com.lyra.common.domain.bus.query.Query

/**
 * Represents a query to find an team by its ID.
 *
 * @property id The ID of the team to find.
 */
data class FindTeamQuery(
    val id: String
) : Query<TeamResponse> {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FindTeamQuery) return false
        return true
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
