package com.lyra.app.team.member.infrastructure.persistence.mapper

import com.lyra.app.team.member.application.OrganizationTeamMemberResponse
import com.lyra.app.team.member.domain.TeamMember
import com.lyra.app.team.member.domain.TeamMemberId
import com.lyra.app.team.member.domain.TeamMemberRole
import com.lyra.app.team.member.infrastructure.persistence.entity.OrganizationTeamMemberEntity
import com.lyra.app.team.member.infrastructure.persistence.entity.TeamMemberEntity
import com.lyra.app.team.member.infrastructure.persistence.mapper.TeamMemberMapper.toDomain
import com.lyra.app.team.member.infrastructure.persistence.mapper.TeamMemberMapper.toEntity
import com.lyra.app.team.member.infrastructure.persistence.mapper.TeamMemberMapper.toResponse
import java.time.LocalDateTime
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class TeamMemberMapperTest {

    @Test
    fun teamMemberToEntityConvertsCorrectly() {
        val teamMember = TeamMember(
            id = TeamMemberId(UUID.randomUUID(), UUID.randomUUID()),
            role = TeamMemberRole.EDITOR,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )

        val entity = teamMember.toEntity()
        assertFalse(entity.isNew)
        assertEquals(teamMember.id.value.first, entity.teamId)
        assertEquals(teamMember.id.value.second, entity.userId)
        assertEquals(teamMember.role.toString(), entity.role)
        assertEquals(teamMember.createdAt, entity.createdAt)
        assertEquals(teamMember.updatedAt, entity.updatedAt)
    }

    @Test
    fun teamMemberEntityToDomainConvertsCorrectly() {
        val entity = TeamMemberEntity(
            teamId = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            role = TeamMemberRole.EDITOR.toString(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )

        val domain = entity.toDomain()

        assertEquals(entity.teamId, domain.id.value.first)
        assertEquals(entity.userId, domain.id.value.second)
        assertEquals(TeamMemberRole.valueOf(entity.role), domain.role)
        assertEquals(entity.createdAt, domain.createdAt)
        assertEquals(entity.updatedAt, domain.updatedAt)
    }

    @Test
    fun organizationTeamMemberEntityToResponseConvertsCorrectly() {
        val entity = OrganizationTeamMemberEntity(
            teamId = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            organizationId = UUID.randomUUID(),
            organizationOwnerId = UUID.randomUUID(),
            teamMemberRole = "MEMBER",
            teamName = "Team A",
            organizationName = "Organization A",
        )

        val response = entity.toResponse()

        assertEquals(entity.teamId.toString(), response.teamId)
        assertEquals(entity.userId.toString(), response.userId)
        assertEquals(entity.organizationId.toString(), response.organizationId)
        assertEquals(entity.organizationOwnerId.toString(), response.organizationOwnerId)
        assertEquals(entity.teamMemberRole, response.role)
        assertEquals(entity.teamName, response.teamName)
        assertEquals(entity.organizationName, response.organizationName)
    }

    @Test
    fun organizationTeamMemberResponseToEntityConvertsCorrectly() {
        val response = OrganizationTeamMemberResponse(
            teamId = UUID.randomUUID().toString(),
            userId = UUID.randomUUID().toString(),
            organizationId = UUID.randomUUID().toString(),
            organizationOwnerId = UUID.randomUUID().toString(),
            role = "MEMBER",
            teamName = "Team A",
            organizationName = "Organization A",
        )

        val entity = response.toEntity()

        assertEquals(UUID.fromString(response.teamId), entity.teamId)
        assertEquals(UUID.fromString(response.userId), entity.userId)
        assertEquals(UUID.fromString(response.organizationId), entity.organizationId)
        assertEquals(UUID.fromString(response.organizationOwnerId), entity.organizationOwnerId)
        assertEquals(response.role, entity.teamMemberRole)
        assertEquals(response.teamName, entity.teamName)
        assertEquals(response.organizationName, entity.organizationName)
    }
}
