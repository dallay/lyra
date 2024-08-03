package com.lyra.app.team.infrastructure.http

import com.lyra.ControllerIntegrationTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.context.jdbc.Sql

private const val ENDPOINT = "/api/organization"

internal class DeleteTeamControllerIntegrationTest : ControllerIntegrationTest() {

    @Test
    @Sql(
        "/db/organization/organization.sql",
        "/db/team/team.sql",
    )
    @Sql(
        "/db/team/clean.sql", "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should delete a team`(): Unit = runBlocking {
        val id = "35b75e15-a5d9-4d15-accb-9ce3a904bc3c"
        webTestClient.mutateWith(csrf()).delete()
            .uri("$ENDPOINT/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty
    }

    @Test
    fun `should return OK when a team is not found`(): Unit = runBlocking {
        val id = "94be1a32-cf2e-4dfc-892d-bdd8ac7ad354"
        webTestClient.mutateWith(csrf()).delete()
            .uri("$ENDPOINT/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty
    }
}
