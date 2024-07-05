package com.lyra.app.organization.infrastructure.http

import com.lyra.ControllerIntegrationTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.context.jdbc.Sql

private const val ENDPOINT = "/api/organization"

internal class DeleteOrganizationControllerIntegrationTest : ControllerIntegrationTest() {

    @Test
    @Sql(
        "/db/organization/organization.sql",
    )
    @Sql(
        "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should delete organization when organization is found`(): Unit = runBlocking {
        val id = "a0654720-35dc-49d0-b508-1f7df5d915f1"
        webTestClient.mutateWith(csrf()).delete()
            .uri("$ENDPOINT/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty
    }

    @Test
    fun `should return OK when organization is not found`(): Unit = runBlocking {
        val id = "94be1a32-cf2e-4dfc-892d-bdd8ac7ad354"
        webTestClient.mutateWith(csrf()).delete()
            .uri("$ENDPOINT/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty
    }
}
