package com.lyra.app.organization.application.delete

import com.lyra.UnitTest
import com.lyra.app.organization.domain.OrganizationRemoverRepository
import com.lyra.app.organization.domain.event.OrganizationDeletedEvent
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
internal class DeleteOrganizationCommandHandlerTest {
    private var eventPublisher: EventPublisher<OrganizationDeletedEvent> = mockk()
    private val destroyerRepository = mockkClass(OrganizationRemoverRepository::class)
    private val destroyer: OrganizationDestroyer =
        OrganizationDestroyer(destroyerRepository, eventPublisher)
    private val deleteOrganizationCommandHandler: DeleteOrganizationCommandHandler =
        DeleteOrganizationCommandHandler(destroyer)
    private val organizationId = UUID.randomUUID().toString()

    @BeforeEach
    fun setUp() {
        coEvery { destroyerRepository.delete(any()) } returns Unit
        coEvery { eventPublisher.publish(any(OrganizationDeletedEvent::class)) } returns Unit
    }

    @Test
    fun `should delete an organization`() = runBlocking {
        val command = DeleteOrganizationCommand(id = organizationId)
        deleteOrganizationCommandHandler.handle(command)

        coVerify(exactly = 1) { destroyerRepository.delete(any()) }
        coVerify(exactly = 1) { eventPublisher.publish(any(OrganizationDeletedEvent::class)) }
    }
}
