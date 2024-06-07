package com.lyra.app.workspaces.infrastructure.http

import com.lyra.ControllerIntegrationTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql

@Sql(
    "/db/workspace/clean.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS,
)
internal class GetAllWorkspaceControllerIntegrationTest : ControllerIntegrationTest() {

    @Test
    @Sql(
        "/db/workspace/all-workspace.sql",
    )
    @Sql(
        "/db/workspace/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get all workspaces`(): Unit = runBlocking {
        webTestClient.get()
            .uri("/api/workspace")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.data.length()").isEqualTo(3)
    }
}
