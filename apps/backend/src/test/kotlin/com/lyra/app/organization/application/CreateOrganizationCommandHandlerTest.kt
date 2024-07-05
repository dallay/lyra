package com.lyra.app.organization.application

import com.lyra.app.organization.domain.Organization
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

internal class CreateOrganizationCommandHandlerTest {
    private var eventPublisher: EventPublisher<OrganizationCreatedEvent> = mockk()
    private val organizationRepository: OrganizationRepository = mockk()
    private val organizationCreator = OrganizationCreator(organizationRepository, eventPublisher)
    private val createOrganizationCommandHandler = CreateOrganizationCommandHandler(organizationCreator)

    @BeforeEach
    fun setUp() {
        coEvery { organizationRepository.create(any(Organization::class)) } returns Unit
        coEvery { eventPublisher.publish(any(OrganizationCreatedEvent::class)) } returns Unit
    }

    @Test
    fun `handle should create workspace with provided command`() = runBlocking {
        val workspaceId = UUID.randomUUID().toString()
        val userId = UUID.randomUUID().toString()
        val name = "Test Workspace"
        val command = OrganizationWorkspaceCommand(
            id = workspaceId,
            name = name,
            userId = userId,
        )
        createOrganizationCommandHandler.handle(command)

        coVerify(exactly = 1) { organizationRepository.create(any(Organization::class)) }
        coVerify(exactly = 1) { eventPublisher.publish(any(OrganizationCreatedEvent::class)) }
    }
}
