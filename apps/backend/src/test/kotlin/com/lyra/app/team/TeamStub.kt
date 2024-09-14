package com.lyra.app.team

import com.lyra.app.GeneralStub.getTimestampCursorPage
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.app.team.application.TeamResponse
import com.lyra.app.team.domain.Team
import com.lyra.app.team.domain.TeamId
import com.lyra.app.team.infrastructure.http.request.CreateTeamRequest
import com.lyra.app.team.infrastructure.http.request.UpdateTeamRequest
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

    fun generateRequest(
        organizationId: String = UUID.randomUUID().toString(),
        name: String = faker.lorem().words(2).joinToString(" ")
    ): CreateTeamRequest = CreateTeamRequest(
        organizationId = organizationId,
        name = name,
    )

    fun generateRequest(
        name: String = faker.lorem().words(2).joinToString(" ")
    ): UpdateTeamRequest = UpdateTeamRequest(
        name = name,
    )

    fun dummyRandomTeamPageResponse(size: Int): CursorPageResponse<TeamResponse> {
        val data = (1..size).map { TeamResponse.from(create()) }
        val (prevCursor, nextCursor) = getStartAndEndTimestampCursorPage(data)
        return CursorPageResponse(
            data = data,
            prevPageCursor = prevCursor,
            nextPageCursor = nextCursor,
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
        val nextCursor = TimestampCursor(data.last().createdAt).serialize()
        val prevCursor = TimestampCursor(data.first().createdAt).serialize()
        return CursorPageResponse(
            data = data,
            prevPageCursor = prevCursor,
            nextPageCursor = nextCursor,
        )
    }

    fun dummyRandomTeams(
        size: Int,
        id: String = UUID.randomUUID().toString(),
        name: String = faker.lorem().words(3).joinToString(" "),
        organizationId: String = UUID.randomUUID().toString(),
        members: MutableList<TeamMember> = mutableListOf(),
    ): List<Team> = (1..size).map {
        create(
            id = id,
            name = name,
            organizationId = organizationId,
            members = members,
        )
    }
}
