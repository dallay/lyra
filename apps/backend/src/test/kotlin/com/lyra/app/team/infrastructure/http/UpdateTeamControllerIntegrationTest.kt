package com.lyra.app.team.infrastructure.http

import com.lyra.ControllerIntegrationTest
import com.lyra.app.team.TeamStub
import com.lyra.app.team.infrastructure.http.request.UpdateTeamRequest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.context.jdbc.Sql

internal class UpdateTeamControllerIntegrationTest : ControllerIntegrationTest() {
    @Test
    @Sql(
        "/db/organization/organization.sql",
        "/db/team/team.sql",
    )
    @Sql(
        "/db/team/clean.sql", "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should update a team`(): Unit = runBlocking {
        val id = "35b75e15-a5d9-4d15-accb-9ce3a904bc3c"
        val request: UpdateTeamRequest = TeamStub.generateRequest()
        webTestClient.mutateWith(csrf()).put()
            .uri("/api/$ENDPOINT_TEAM/update/$id")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .isEqualTo("Team updated successfully")
    }

    @Test
    @Sql(
        "/db/organization/organization.sql",
        "/db/team/team.sql",
    )
    @Sql(
        "/db/team/clean.sql", "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should return 404 when team is not found`(): Unit = runBlocking {
        val id = "35b75e15-a5d9-4d15-accb-9ce3a904bc3d"
        val request: UpdateTeamRequest = TeamStub.generateRequest()
        webTestClient.mutateWith(csrf()).put()
            .uri("/api/$ENDPOINT_TEAM/update/$id")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.type").isEqualTo("https://lyra.com/errors/entity-not-found")
            .jsonPath("$.title").isEqualTo("Entity not found")
            .jsonPath("$.status").isEqualTo(404)
            .jsonPath("$.detail").isEqualTo("Team not found")
            .jsonPath("$.instance").isEqualTo("/api/$ENDPOINT_TEAM/update/$id")
            .jsonPath("$.errorCategory").isEqualTo("NOT_FOUND")
    }
}
