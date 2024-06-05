package com.lyra.app.workspaces.infrastructure.http

import com.lyra.UnitTest
import com.lyra.app.workspaces.application.collaborator.AddWorkspaceCollaboratorCommand
import com.lyra.app.workspaces.infrastructure.WorkspaceStub
import com.lyra.app.workspaces.infrastructure.http.request.AddWorkspaceCollaboratorRequest
import com.lyra.common.domain.bus.Mediator
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import java.time.LocalDateTime
import java.util.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

@UnitTest
internal class AddWorkspaceCollaboratorControllerTest {
    private val workspaceCollaborators = WorkspaceStub.createCollaborator()
    private val mediator = mockk<Mediator>()
    private val id = UUID.randomUUID().toString()
    private val fixedTime = LocalDateTime.of(2024, 1, 1, 0, 0)
    private val command = AddWorkspaceCollaboratorCommand(
        workspaceId = id,
        userId = workspaceCollaborators.userId.value.toString(),
        role = workspaceCollaborators.role.toString(),
        addedAt = fixedTime.toString(),
    )
    private val controller = AddWorkspaceCollaboratorController(mediator)
    private val webTestClient = WebTestClient.bindToController(controller).build()

    @BeforeEach
    fun setUp() {
        mockkStatic(LocalDateTime::class)
        every { LocalDateTime.now() } returns fixedTime
        coEvery { mediator.send(eq(command)) } returns Unit
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
        unmockkStatic(LocalDateTime::class)
    }

    @Test
    fun `should create a new workspace collaborator`() {
        val request = AddWorkspaceCollaboratorRequest(
            workspaceId = id,
            userId = workspaceCollaborators.userId.value.toString(),
            role = workspaceCollaborators.role.toString(),
        )
        webTestClient.put()
            .uri("/api/workspace/collaborator")
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty
        coEvery { mediator.send(eq(command)) }
    }
}
