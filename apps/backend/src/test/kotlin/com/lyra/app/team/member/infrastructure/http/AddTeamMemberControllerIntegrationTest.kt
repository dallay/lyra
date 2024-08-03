package com.lyra.app.team.member.infrastructure.http

import com.lyra.ControllerIntegrationTest
import com.lyra.app.team.member.TeamMemberStub
import com.lyra.app.team.member.infrastructure.http.request.AddTeamMemberRequest
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.context.jdbc.Sql

internal class AddTeamMemberControllerIntegrationTest : ControllerIntegrationTest() {
    private val teamId = "35b75e15-a5d9-4d15-accb-9ce3a904bc3c"
    private val userId = "efc4b2b8-08be-4020-93d5-f795762bf5c9"

    @Test
    @Sql(
        "/db/organization/organization.sql",
        "/db/team/team.sql",
    )
    @Sql(
        "/db/team/clean.sql", "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should add a new team member`() {
        val request: AddTeamMemberRequest = TeamMemberStub.generateRequest(
            teamId = teamId,
            userId = userId,
        )
        webTestClient.mutateWith(csrf()).put()
            .uri("/api/$ENDPOINT_TEAM_MEMBER")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty
    }
}
