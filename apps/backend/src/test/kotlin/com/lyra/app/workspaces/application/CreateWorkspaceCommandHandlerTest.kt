package com.lyra.app.workspaces.application

import com.lyra.app.workspaces.domain.Workspace
import com.lyra.app.workspaces.domain.WorkspaceRepository
import com.lyra.app.workspaces.domain.event.WorkspaceCreatedEvent
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CreateWorkspaceCommandHandlerTest {
    private var eventPublisher: EventPublisher<WorkspaceCreatedEvent> = mockk()
    private val workspaceRepository: WorkspaceRepository = mockk()
    private val workspaceCreator = WorkspaceCreator(workspaceRepository, eventPublisher)
    private val createWorkspaceCommandHandler = CreateWorkspaceCommandHandler(workspaceCreator)

    @BeforeEach
    fun setUp() {
        coEvery { workspaceRepository.create(any(Workspace::class)) } returns Unit
        coEvery { eventPublisher.publish(any(WorkspaceCreatedEvent::class)) } returns Unit
    }

    @Test
    fun `handle should create workspace with provided command`() = runBlocking {
        val workspaceId = UUID.randomUUID().toString()
        val userId = UUID.randomUUID().toString()
        val name = "Test Workspace"
        val command = CreateWorkspaceCommand(
            id = workspaceId,
            name = name,
            userId = userId,
        )
        createWorkspaceCommandHandler.handle(command)

        coVerify(exactly = 1) { workspaceRepository.create(any(Workspace::class)) }
        coVerify(exactly = 1) { eventPublisher.publish(any(WorkspaceCreatedEvent::class)) }
    }
}
