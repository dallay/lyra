package com.lyra.app.workspaces.application

import com.lyra.app.workspaces.application.collaborator.AddWorkspaceCollaboratorCommand
import com.lyra.app.workspaces.application.collaborator.AddWorkspaceCollaboratorCommandHandler
import com.lyra.app.workspaces.application.collaborator.WorkspaceCollaboratorCreator
import com.lyra.app.workspaces.domain.WorkspaceCollaborators
import com.lyra.app.workspaces.domain.WorkspaceRepository
import com.lyra.app.workspaces.domain.event.WorkspaceCollaboratorAddedEvent
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.time.LocalDateTime
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AddWorkspaceCollaboratorCommandHandlerTest {
    private var eventPublisher: EventPublisher<WorkspaceCollaboratorAddedEvent> = mockk()
    private val workspaceRepository: WorkspaceRepository = mockk()
    private val workspaceCollaboratorCreator = WorkspaceCollaboratorCreator(workspaceRepository, eventPublisher)
    private val addWorkspaceCollaboratorCommandHandler =
        AddWorkspaceCollaboratorCommandHandler(workspaceCollaboratorCreator)

    @BeforeEach
    fun setUp() {
        coEvery { workspaceRepository.create(any(WorkspaceCollaborators::class)) } returns Unit
        coEvery { eventPublisher.publish(any(WorkspaceCollaboratorAddedEvent::class)) } returns Unit
    }

    @Test
    fun `handle should create workspace collaborate with provided command`() = runBlocking {
        val workspaceId = UUID.randomUUID().toString()
        val userId = UUID.randomUUID().toString()
        val role = "COLLABORATOR"
        val command = AddWorkspaceCollaboratorCommand(
            workspaceId = workspaceId,
            userId = userId,
            role = role,
            addedAt = LocalDateTime.now().toString(),
        )
        addWorkspaceCollaboratorCommandHandler.handle(command)

        coVerify(exactly = 1) { workspaceRepository.create(any(WorkspaceCollaborators::class)) }
        coVerify(exactly = 1) { eventPublisher.publish(any(WorkspaceCollaboratorAddedEvent::class)) }
    }
}
