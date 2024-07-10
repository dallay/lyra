package com.lyra.app.team.member

import com.lyra.app.team.member.domain.TeamMember
import com.lyra.app.team.member.domain.TeamMemberId
import com.lyra.app.team.member.domain.TeamMemberRole
import com.lyra.app.team.member.infrastructure.http.request.AddTeamMemberRequest
import java.util.*

object TeamMemberStub {
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
}
