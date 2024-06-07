package com.lyra.app.workspaces.infrastructure.http

import com.lyra.UnitTest
import com.lyra.app.workspaces.application.collaborator.remove.RemoveWorkspaceCollaboratorCommand
import com.lyra.common.domain.bus.Mediator
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

@UnitTest
internal class RemoveWorkspaceCollaboratorControllerTest {
    private val mediator: Mediator = mockk()
    private val controller: RemoveWorkspaceCollaboratorController =
        RemoveWorkspaceCollaboratorController(mediator)
    private val webTestClient: WebTestClient = WebTestClient.bindToController(controller).build()
    private val workspaceId = UUID.randomUUID().toString()
    private val userId = UUID.randomUUID().toString()

    @Test
    fun `should remove a collaborator from a workspace`() {
        val command = RemoveWorkspaceCollaboratorCommand(workspaceId, userId)
        coEvery { mediator.send(command) } returns Unit
        webTestClient.delete()
            .uri("/api/workspace/$workspaceId/collaborator/$userId")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty()
    }
}
