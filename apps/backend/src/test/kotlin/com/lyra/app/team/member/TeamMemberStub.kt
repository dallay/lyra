package com.lyra.app.team.member

import com.lyra.app.team.member.infrastructure.http.request.AddTeamMemberRequest
import java.util.*

object TeamMemberStub {
//    private val faker = Faker()
    fun generateRequest(
        teamId: String = UUID.randomUUID().toString(),
        userId: String = UUID.randomUUID().toString(),
        role: String = "EDITOR"
    ): AddTeamMemberRequest = AddTeamMemberRequest(
        teamId = teamId,
        userId = userId,
        role = role,
    )
}
