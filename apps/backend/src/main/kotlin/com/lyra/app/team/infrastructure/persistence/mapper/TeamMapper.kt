package com.lyra.app.team.infrastructure.persistence.mapper

import com.lyra.app.organization.domain.OrganizationId
import com.lyra.app.team.domain.Team
import com.lyra.app.team.domain.TeamId
import com.lyra.app.team.infrastructure.persistence.entity.TeamEntity

/**
 * This object provides mapping functions to convert between domain and entity objects.
 */
object TeamMapper {
    /**
     * Converts a [Team] domain object to a [TeamEntity].
     *
     * @receiver The [Team] domain object to convert.
     * @return The converted [TeamEntity].
     */
    fun Team.toEntity(): TeamEntity = TeamEntity(
        teamId = id.value,
        organizationId = organizationId.value,
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    /**
     * Converts a [TeamEntity] to a [Team] domain object.
     *
     * @receiver The [TeamEntity] to convert.
     * @return The converted [Team] domain object.
     */
    fun TeamEntity.toDomain(): Team = Team(
        id = TeamId(teamId),
        name = name,
        organizationId = OrganizationId(organizationId),
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
