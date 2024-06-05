package com.lyra.app.workspaces.application.update

import com.lyra.UnitTest
import com.lyra.app.workspaces.domain.WorkspaceFinderRepository
import com.lyra.app.workspaces.domain.WorkspaceRepository
import com.lyra.app.workspaces.domain.event.WorkspaceUpdatedEvent
import com.lyra.app.workspaces.infrastructure.WorkspaceStub
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
class UpdateWorkspaceCommandHandlerTest {
    private var eventPublisher: EventPublisher<WorkspaceUpdatedEvent> = mockk()
    private var workspaceRepository = mockkClass(WorkspaceRepository::class)
    private var workspaceFinderRepository = mockkClass(WorkspaceFinderRepository::class)
    private val workspaceUpdater: WorkspaceUpdater =
        WorkspaceUpdater(workspaceRepository, workspaceFinderRepository, eventPublisher)
    private val updateWorkspaceCommandHandler: UpdateWorkspaceCommandHandler =
        UpdateWorkspaceCommandHandler(workspaceUpdater)
    private val workspace = WorkspaceStub.create()

    @BeforeEach
    fun setUp() {
        coEvery { workspaceRepository.update(any()) } returns Unit
        coEvery { workspaceFinderRepository.findById(eq(workspace.id)) } returns workspace
        coEvery { eventPublisher.publish(any(WorkspaceUpdatedEvent::class)) } returns Unit
    }
    @Test
    fun `should update a workspace`() = runBlocking {
        val command = UpdateWorkspaceCommand(
            id = workspace.id.value.toString(),
            name = "New Workspace Name",
        )
        updateWorkspaceCommandHandler.handle(command)

        coVerify(exactly = 1) { workspaceRepository.update(any()) }
        coVerify(exactly = 1) { eventPublisher.publish(any(WorkspaceUpdatedEvent::class)) }
    }
}
