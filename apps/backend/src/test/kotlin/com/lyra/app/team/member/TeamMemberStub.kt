package com.lyra.app.team.member

import com.lyra.app.team.member.domain.TeamMember
import com.lyra.app.team.member.domain.TeamMemberId
import com.lyra.app.team.member.domain.TeamMemberRole
import com.lyra.app.team.member.infrastructure.http.request.AddTeamMemberRequest
import com.lyra.app.team.member.infrastructure.persistence.entity.OrganizationTeamMemberEntity
import java.util.*
import net.datafaker.Faker

object TeamMemberStub {
    private val faker = Faker()
    fun generateRequest(
        teamId: String = UUID.randomUUID().toString(),
        userId: String = UUID.randomUUID().toString(),
        role: String = "EDITOR"
    ): AddTeamMemberRequest = AddTeamMemberRequest(
        teamId = teamId,
        userId = userId,
        role = role,
    )

    fun create(
        teamId: String = UUID.randomUUID().toString(),
        userId: String = UUID.randomUUID().toString(),
        role: String = "EDITOR"
    ): TeamMember = TeamMember(
        id = TeamMemberId(teamId, userId),
        role = TeamMemberRole.valueOf(role),
    )

    fun create(
        teamName: String = faker.team().name(),
        organizationName: String = faker.company().name(),
        teamMemberRole: String = "OWNER",
        organizationId: UUID = UUID.randomUUID(),
        organizationOwnerId: UUID = UUID.randomUUID(),
        teamId: UUID = UUID.randomUUID(),
        userId: UUID = UUID.randomUUID()
    ): OrganizationTeamMemberEntity = OrganizationTeamMemberEntity(
        teamName = teamName,
        organizationName = organizationName,
        teamMemberRole = teamMemberRole,
        organizationId = organizationId,
        organizationOwnerId = organizationOwnerId,
        teamId = teamId,
        userId = userId,
    )
}
