package com.lyra.app.organization.application

import com.lyra.UnitTest
import com.lyra.app.organization.domain.OrganizationRepository
import com.lyra.app.organization.domain.event.OrganizationCreatedEvent
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class CreateOrganizationCommandHandlerTest {
    private lateinit var eventPublisher: EventPublisher<OrganizationCreatedEvent>
    private lateinit var organizationRepository: OrganizationRepository
    private lateinit var organizationCreator: OrganizationCreator
    private lateinit var createOrganizationCommandHandler: CreateOrganizationCommandHandler

    @BeforeEach
    fun setUp() {
        eventPublisher = mockk()
        organizationRepository = mockk()
        organizationCreator = OrganizationCreator(organizationRepository, eventPublisher)
        createOrganizationCommandHandler = CreateOrganizationCommandHandler(organizationCreator)

        coEvery { organizationRepository.create(any()) } returns Unit
        coEvery { eventPublisher.publish(any<OrganizationCreatedEvent>()) } returns Unit
    }

    @Test
    fun `should create organization and publish event when handle is called`() = runBlocking {
        // Given
        val organizationId = UUID.randomUUID().toString()
        val userId = UUID.randomUUID().toString()
        val name = "Test Organization"
        val command = OrganizationCommand(
            id = organizationId,
            name = name,
            userId = userId,
        )

        // When
        createOrganizationCommandHandler.handle(command)

        // Then
        coVerify {
            organizationRepository.create(
                withArg {
                    assert(it.id.value.toString() == organizationId)
                    assert(it.name == name)
                    assert(it.userId.value.toString() == userId)
                },
            )
        }
        coVerify { eventPublisher.publish(ofType<OrganizationCreatedEvent>()) }
    }
}
