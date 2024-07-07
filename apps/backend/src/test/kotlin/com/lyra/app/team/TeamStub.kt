package com.lyra.app.team

import com.lyra.app.GeneralStub.getTimestampCursorPage
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.app.team.application.TeamResponse
import com.lyra.app.team.domain.Team
import com.lyra.app.team.domain.TeamId
import com.lyra.app.team.infrastructure.http.request.CreateTeamRequest
import com.lyra.app.team.member.domain.TeamMember
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.common.domain.presentation.pagination.TimestampCursor
import java.util.*
import net.datafaker.Faker

object TeamStub {
    private val faker = Faker()

    fun create(
        id: String = UUID.randomUUID().toString(),
        name: String = faker.lorem().words(3).joinToString(" "),
        organizationId: String = UUID.randomUUID().toString(),
        members: MutableList<TeamMember> = mutableListOf(),
    ): Team = Team(
        id = TeamId(id),
        name = name,
        organizationId = OrganizationId(organizationId),
        members = members,
    )
//
//    @Suppress("MultilineRawStringIndentation")
//    fun generateRequest(
//        organizationId: String,
//        name: String = faker.lorem().words(3).joinToString(" ")
//    ): String = """
//      {
//        "organizationId": "$organizationId",
//        "name": "$name"
//      }
//    """.trimIndent()

    fun generateRequest(
        organizationId: String = UUID.randomUUID().toString(),
        name: String = faker.lorem().words(2).joinToString(" ")
    ): CreateTeamRequest = CreateTeamRequest(
        organizationId = organizationId,
        name = name,
    )

    fun dummyRandomTeamPageResponse(size: Int): CursorPageResponse<TeamResponse> {
        val data = (1..size).map { TeamResponse.from(create()) }
        val (_, cursor) = getStartAndEndTimestampCursorPage(data)
        return CursorPageResponse(
            data = data,
            nextPageCursor = cursor,
        )
    }

    private fun getStartAndEndTimestampCursorPage(data: List<TeamResponse>): Pair<String, String> {
        val startCreatedAt = data.first().createdAt
        val startCursor = getTimestampCursorPage(startCreatedAt)
        val lastCreatedAt = data.last().createdAt
        val endCursor = getTimestampCursorPage(lastCreatedAt)
        return Pair(startCursor, endCursor)
    }

    fun dummyRandomTeamsPageResponse(size: Int): CursorPageResponse<Team> {
        val data = (1..size).map { create() }
        val cursor = TimestampCursor(data.last().createdAt).serialize()
        return CursorPageResponse(
            data = data,
            nextPageCursor = cursor,
        )
    }

    fun dummyRandomTeams(size: Int): List<Team> = (1..size).map { create() }
}
