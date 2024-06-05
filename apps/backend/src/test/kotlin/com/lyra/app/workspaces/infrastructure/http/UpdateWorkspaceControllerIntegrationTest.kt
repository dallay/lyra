package com.lyra.app.workspaces.infrastructure.http

import com.lyra.ControllerIntegrationTest
import com.lyra.app.workspaces.infrastructure.WorkspaceStub
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.context.jdbc.Sql

private const val ENDPOINT = "/api/workspace/update"

internal class UpdateWorkspaceControllerIntegrationTest : ControllerIntegrationTest() {

    @Test
    @Sql(
        "/db/workspace/workspace.sql",
    )
    @Sql(
        "/db/workspace/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should update a new workspace`(): Unit = runBlocking {
        val id = "a0654720-35dc-49d0-b508-1f7df5d915f1"
        val request = WorkspaceStub.generateUpdateRequest(name = "New Workspace Name")
        webTestClient.mutateWith(csrf()).put()
            .uri("$ENDPOINT/$id")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .isEqualTo("Workspace updated successfully")
    }

    @Test
    fun `should return 404 when workspace is not found`(): Unit = runBlocking {
        val id = "a2523c80-62f4-4db2-9fb7-191caa22e2bc"
        val request = WorkspaceStub.generateUpdateRequest(name = "New Workspace Name")
        webTestClient.mutateWith(csrf()).put()
            .uri("$ENDPOINT/$id")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.type").isEqualTo("https://lyra.com/errors/entity-not-found")
            .jsonPath("$.title").isEqualTo("Entity not found")
            .jsonPath("$.status").isEqualTo(404)
            .jsonPath("$.detail").isEqualTo("Workspace not found")
            .jsonPath("$.instance").isEqualTo("$ENDPOINT/$id")
            .jsonPath("$.errorCategory").isEqualTo("NOT_FOUND")
    }
}
