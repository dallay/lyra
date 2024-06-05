package com.lyra.app.workspaces.application.delete

import com.lyra.UnitTest
import com.lyra.app.workspaces.domain.WorkspaceDestroyerRepository
import com.lyra.app.workspaces.domain.event.WorkspaceDeletedEvent
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkClass
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class DeleteWorkspaceCommandHandlerTest {
    private var eventPublisher: EventPublisher<WorkspaceDeletedEvent> = mockk()
    private val destroyerRepository = mockkClass(WorkspaceDestroyerRepository::class)
    private val destroyer: WorkspaceDestroyer =
        WorkspaceDestroyer(destroyerRepository, eventPublisher)
    private val deleteWorkspaceCommandHandler: DeleteWorkspaceCommandHandler =
        DeleteWorkspaceCommandHandler(destroyer)
    private val workspaceId = UUID.randomUUID().toString()

    @BeforeEach
    fun setUp() {
        coEvery { destroyerRepository.delete(any()) } returns Unit
        coEvery { eventPublisher.publish(any(WorkspaceDeletedEvent::class)) } returns Unit
    }

    @Test
    fun `should delete a workspace`() = runBlocking {
        val command = DeleteWorkspaceCommand(id = workspaceId)
        deleteWorkspaceCommandHandler.handle(command)

        coVerify(exactly = 1) { destroyerRepository.delete(any()) }
        coVerify(exactly = 1) { eventPublisher.publish(any(WorkspaceDeletedEvent::class)) }
    }
}
