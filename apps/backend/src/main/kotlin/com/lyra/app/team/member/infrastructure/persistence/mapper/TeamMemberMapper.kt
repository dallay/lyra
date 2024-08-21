package com.lyra.app.team.member.infrastructure.persistence.mapper

import com.lyra.app.team.member.application.OrganizationTeamMemberResponse
import com.lyra.app.team.member.domain.TeamMember
import com.lyra.app.team.member.domain.TeamMemberId
import com.lyra.app.team.member.domain.TeamMemberRole
import com.lyra.app.team.member.infrastructure.persistence.entity.OrganizationTeamMemberEntity
import com.lyra.app.team.member.infrastructure.persistence.entity.TeamMemberEntity
import java.util.*

/**
 * This object provides mapping functions to convert between domain and entity objects.
 */
object TeamMemberMapper {

    /**
     * Converts a [TeamMember] domain object to a [TeamMemberEntity].
     *
     * @receiver The [TeamMember] domain object to convert.
     * @return The converted [TeamMemberEntity].
     */
    fun TeamMember.toEntity(): TeamMemberEntity = TeamMemberEntity(
        teamId = id.value.first,
        userId = id.value.second,
        role = role.toString(),
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    /**
     * Converts a [TeamMemberEntity] to a [TeamMember] domain object.
     *
     * @receiver The [TeamMemberEntity] to convert.
     * @return The converted [TeamMember] domain object.
     */
    fun TeamMemberEntity.toDomain(): TeamMember = TeamMember(
        id = TeamMemberId(teamId, userId),
        role = TeamMemberRole.valueOf(role),
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    /**
     * Converts an [OrganizationTeamMemberEntity] to an [OrganizationTeamMemberResponse].
     *
     * @receiver The [OrganizationTeamMemberEntity] to convert.
     * @return The converted [OrganizationTeamMemberResponse].
     */
    fun OrganizationTeamMemberEntity.toResponse(): OrganizationTeamMemberResponse =
        OrganizationTeamMemberResponse(
            teamId = teamId.toString(),
            userId = userId.toString(),
            organizationId = organizationId.toString(),
            organizationOwnerId = organizationOwnerId.toString(),
            role = teamMemberRole,
            teamName = teamName,
            organizationName = organizationName,
        )

    /**
     * Converts an [OrganizationTeamMemberResponse] to an [OrganizationTeamMemberEntity].
     *
     * @receiver The [OrganizationTeamMemberResponse] to convert.
     * @return The converted [OrganizationTeamMemberEntity].
     */
    fun OrganizationTeamMemberResponse.toEntity(): OrganizationTeamMemberEntity =
        OrganizationTeamMemberEntity(
            organizationId = UUID.fromString(organizationId),
            organizationOwnerId = UUID.fromString(organizationOwnerId),
            teamId = UUID.fromString(teamId),
            userId = UUID.fromString(userId),
            teamMemberRole = role,
            teamName = teamName,
            organizationName = organizationName,
        )
}
