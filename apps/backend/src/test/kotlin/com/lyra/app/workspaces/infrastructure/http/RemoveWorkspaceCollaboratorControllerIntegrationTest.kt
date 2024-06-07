package com.lyra.app.workspaces.infrastructure.http

import com.lyra.ControllerIntegrationTest
import java.util.UUID
import org.junit.jupiter.api.Test
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.context.jdbc.Sql

internal class RemoveWorkspaceCollaboratorControllerIntegrationTest : ControllerIntegrationTest() {

    @Test
    @Sql(
        "/db/workspace/workspace.sql",
    )
    @Sql(
        "/db/workspace/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should remove a collaborator from a workspace`() {
        val workspaceId = "a0654720-35dc-49d0-b508-1f7df5d915f1"
        val userId = "efc4b2b8-08be-4020-93d5-f795762bf5c9"
        webTestClient.mutateWith(csrf()).delete()
            .uri("/api/workspace/$workspaceId/collaborator/$userId")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty()
    }

    @Test
    @Sql(
        "/db/workspace/workspace.sql",
    )
    @Sql(
        "/db/workspace/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should return OK when workspace is not found`() {
        val workspaceId = UUID.randomUUID().toString()
        val userId = "efc4b2b8-08be-4020-93d5-f795762bf5c9"
        webTestClient.mutateWith(csrf()).delete()
            .uri("/api/workspace/$workspaceId/collaborator/$userId")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty()
    }

    @Test
    @Sql(
        "/db/workspace/workspace.sql",
    )
    @Sql(
        "/db/workspace/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should return OK when collaborator is not found`() {
        val workspaceId = "a0654720-35dc-49d0-b508-1f7df5d915f1"
        val userId = UUID.randomUUID().toString()
        webTestClient.mutateWith(csrf()).delete()
            .uri("/api/workspace/$workspaceId/collaborator/$userId")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty()
    }
}
