package com.lyra.app.workspaces.application.collaborator.remove

import com.lyra.UnitTest
import com.lyra.app.users.domain.UserId
import com.lyra.app.workspaces.domain.WorkspaceCollaboratorRemoverRepository
import com.lyra.app.workspaces.domain.WorkspaceId
import com.lyra.app.workspaces.domain.event.WorkspaceCollaboratorRemovedEvent
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
class RemoveWorkspaceCollaboratorCommandHandlerTest {
    private val eventPublisher: EventPublisher<WorkspaceCollaboratorRemovedEvent> = mockk()
    private val workspaceCollaboratorRemoverRepository: WorkspaceCollaboratorRemoverRepository =
        mockk()
    private val workspaceCollaboratorRemover: WorkspaceCollaboratorRemover =
        WorkspaceCollaboratorRemover(workspaceCollaboratorRemoverRepository, eventPublisher)
    private val removeWorkspaceCollaboratorCommandHandler: RemoveWorkspaceCollaboratorCommandHandler =
        RemoveWorkspaceCollaboratorCommandHandler(workspaceCollaboratorRemover)

    @BeforeEach
    fun setUp() {
        coEvery {
            workspaceCollaboratorRemoverRepository.removeCollaborator(
                any(WorkspaceId::class),
                any(UserId::class),
            )
        } returns Unit
        coEvery { eventPublisher.publish(any(WorkspaceCollaboratorRemovedEvent::class)) } returns Unit
    }

    @Test
    fun `should remove a collaborator from a workspace`(): Unit = runBlocking {
        val command = RemoveWorkspaceCollaboratorCommand(
            workspaceId = UUID.randomUUID().toString(),
            userId = UUID.randomUUID().toString(),
        )
        removeWorkspaceCollaboratorCommandHandler.handle(command)

        coEvery {
            workspaceCollaboratorRemoverRepository.removeCollaborator(
                any(WorkspaceId::class),
                any(UserId::class),
            )
        }
        coEvery { eventPublisher.publish(any(WorkspaceCollaboratorRemovedEvent::class)) }
    }
}
