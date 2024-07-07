package com.lyra.app.organization.application.delete

import com.lyra.UnitTest
import com.lyra.app.organization.domain.OrganizationRemoverRepository
import com.lyra.app.organization.domain.event.OrganizationDeletedEvent
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class DeleteCreateOrganizationCommandHandlerTest {
    private lateinit var eventPublisher: EventPublisher<OrganizationDeletedEvent>
    private lateinit var destroyerRepository: OrganizationRemoverRepository
    private lateinit var destroyer: OrganizationDestroyer
    private lateinit var deleteOrganizationCommandHandler: DeleteOrganizationCommandHandler
    private lateinit var organizationId: String

    @BeforeEach
    fun setUp() {
        eventPublisher = mockk()
        destroyerRepository = mockk()
        destroyer = OrganizationDestroyer(destroyerRepository, eventPublisher)
        deleteOrganizationCommandHandler = DeleteOrganizationCommandHandler(destroyer)
        organizationId = UUID.randomUUID().toString()

        coEvery { destroyerRepository.delete(any()) } returns Unit
        coEvery { eventPublisher.publish(any<OrganizationDeletedEvent>()) } returns Unit
    }

    @Test
    fun `should delete an organization and publish event when handle is called`() = runBlocking {
        // Given
        val command = DeleteOrganizationCommand(id = organizationId)

        // When
        deleteOrganizationCommandHandler.handle(command)

        // Then
        coVerify {
            destroyerRepository.delete(
                withArg {
                    assert(it.value.toString() == organizationId)
                },
            )
        }
        coVerify { eventPublisher.publish(ofType<OrganizationDeletedEvent>()) }
    }
}
