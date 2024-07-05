package com.lyra.app.team.application

import com.lyra.app.team.domain.Team
import com.lyra.common.domain.bus.query.Response

/**
 * Represents a team response.
 * @property id Unique identifier for the team.
 * @property name Name of the team.
 * @property organizationId Unique identifier for the organization.
 * @property createdAt The date and time the team was created.
 * @property updatedAt The date and time the team was last updated.
 */
data class TeamResponse(
    val id: String,
    val name: String,
    val organizationId: String,
    val createdAt: String,
    val updatedAt: String?
) : Response {
    companion object {
        fun from(team: Team) = TeamResponse(
            id = team.id.value.toString(),
            name = team.name,
            organizationId = team.organizationId.value.toString(),
            createdAt = team.createdAt.toString(),
            updatedAt = team.updatedAt?.toString(),
        )
    }
}

/**
 * Represents a list of team responses.
 */
data class TeamResponses(val data: List<TeamResponse>) : Response {
    companion object {
        fun from(teams: List<Team>) = TeamResponses(
            data = teams.map { TeamResponse.from(it) },
        )
    }
}
