package com.lyra.app.team.infrastructure.http

import com.lyra.ControllerIntegrationTest
import com.lyra.app.organization.infrastructure.http.ENDPOINT_ORGANIZATION
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql

internal class GetAllTeamControllerIntegrationTest : ControllerIntegrationTest() {
    private val organizationId = "a0654720-35dc-49d0-b508-1f7df5d915f1"
    @Test
    @Sql(
        "/db/organization/organization.sql",
        "/db/team/all-teams.sql",
    )
    @Sql(
        "/db/team/clean.sql", "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get all teams`(): Unit = runBlocking {
        webTestClient.get()
            .uri("/api/$ENDPOINT_ORGANIZATION/$organizationId/$ENDPOINT_TEAM")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.data.length()").isEqualTo(5)
    }
}
