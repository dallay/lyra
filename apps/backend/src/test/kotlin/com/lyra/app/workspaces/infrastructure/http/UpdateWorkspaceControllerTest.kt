package com.lyra.app.workspaces.infrastructure.http

import com.lyra.UnitTest
import com.lyra.app.workspaces.application.update.UpdateWorkspaceCommand
import com.lyra.app.workspaces.infrastructure.WorkspaceStub
import com.lyra.app.workspaces.infrastructure.http.request.UpdateWorkspaceRequest
import com.lyra.common.domain.bus.Mediator
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

@UnitTest
internal class UpdateWorkspaceControllerTest {
    private val workspace = WorkspaceStub.create()
    private val mediator = mockk<Mediator>()
    private val id = UUID.randomUUID().toString()
    private val command = UpdateWorkspaceCommand(
        id = id,
        name = workspace.name,
    )
    private val controller = UpdateWorkspaceController(mediator)
    private val webTestClient = WebTestClient.bindToController(controller).build()

    @BeforeEach
    fun setUp() {
        coEvery { mediator.send(eq(command)) } returns Unit
    }

    @Test
    fun `should return 200 when workspace is updated successfully`() {
        val request = UpdateWorkspaceRequest(
            name = workspace.name,
        )
        webTestClient.put()
            .uri("/api/workspace/update/$id")
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .isEqualTo("Workspace updated successfully")
        coEvery { mediator.send(eq(command)) }
    }
}
