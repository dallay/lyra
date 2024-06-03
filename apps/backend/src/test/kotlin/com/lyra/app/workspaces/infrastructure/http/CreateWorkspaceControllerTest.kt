package com.lyra.app.workspaces.infrastructure.http

import com.lyra.UnitTest
import com.lyra.app.workspaces.application.CreateWorkspaceCommand
import com.lyra.app.workspaces.infrastructure.WorkspaceStub
import com.lyra.app.workspaces.infrastructure.http.request.CreateWorkspaceRequest
import com.lyra.common.domain.bus.Mediator
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

@UnitTest
internal class CreateWorkspaceControllerTest {
    private val workspace = WorkspaceStub.create()
    private val mediator = mockk<Mediator>()
    private val id = UUID.randomUUID().toString()
    private val command = CreateWorkspaceCommand(
        id = id,
        name = workspace.name,
        userId = workspace.userId.value.toString(),
    )
    private val controller = CreateWorkspaceController(mediator)
    private val webTestClient = WebTestClient.bindToController(controller).build()

    @BeforeEach
    fun setUp() {
        coEvery { mediator.send(eq(command)) } returns Unit
    }

    @Test
    fun `should create a new workspace`() {
        val request = CreateWorkspaceRequest(
            name = workspace.name,
            userId = workspace.userId.value.toString(),
        )
        webTestClient.put()
            .uri("/api/workspace/$id")
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty
        coEvery { mediator.send(eq(command)) }
    }
}
