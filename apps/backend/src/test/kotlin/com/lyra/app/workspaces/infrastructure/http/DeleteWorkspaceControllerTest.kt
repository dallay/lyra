package com.lyra.app.workspaces.infrastructure.http

import com.lyra.UnitTest
import com.lyra.app.workspaces.application.delete.DeleteWorkspaceCommand
import com.lyra.common.domain.bus.Mediator
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

@UnitTest
internal class DeleteWorkspaceControllerTest {
    private val mediator: Mediator = mockk()
    private lateinit var controller: DeleteWorkspaceController
    private lateinit var webTestClient: WebTestClient
    private val id = UUID.randomUUID().toString()

    @BeforeEach
    fun setup() {
        controller = DeleteWorkspaceController(mediator)
        webTestClient = WebTestClient.bindToController(controller).build()
    }

    @Test
    fun `should delete workspace`() {
        val command = DeleteWorkspaceCommand(id)
        coEvery { mediator.send(command) } returns Unit

        webTestClient.delete()
            .uri("/api/workspace/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty()

        coEvery { mediator.send(command) }
    }
}
