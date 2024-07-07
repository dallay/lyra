package com.lyra.app.organization.application.update

import com.lyra.UnitTest
import com.lyra.app.organization.domain.Organization
import com.lyra.app.organization.domain.OrganizationFinderRepository
import com.lyra.app.organization.domain.OrganizationRepository
import com.lyra.app.organization.domain.event.OrganizationUpdatedEvent
import com.lyra.app.organization.infrastructure.OrganizationStub
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class UpdateOrganizationCommandHandlerTest {
    private lateinit var eventPublisher: EventPublisher<OrganizationUpdatedEvent>
    private lateinit var organizationRepository: OrganizationRepository
    private lateinit var organizationFinderRepository: OrganizationFinderRepository
    private lateinit var organizationUpdater: OrganizationUpdater
    private lateinit var updateOrganizationCommandHandler: UpdateOrganizationCommandHandler
    private lateinit var organization: Organization
    @BeforeEach
    fun setUp() {
        eventPublisher = mockk()
        organizationRepository = mockk()
        organizationFinderRepository = mockk()
        organizationUpdater = OrganizationUpdater(organizationRepository, organizationFinderRepository, eventPublisher)
        updateOrganizationCommandHandler = UpdateOrganizationCommandHandler(organizationUpdater)
        organization = OrganizationStub.create()

        coEvery { organizationRepository.update(any()) } returns Unit
        coEvery { organizationFinderRepository.findById(any()) } returns organization
        coEvery { eventPublisher.publish(any(OrganizationUpdatedEvent::class)) } returns Unit
    }

    @Test
    fun `should update an organization`() = runBlocking {
        // Given
        val command = UpdateOrganizationCommand(
            id = organization.id.value.toString(),
            name = organization.name,
        )

        // When
        updateOrganizationCommandHandler.handle(command)

        // Then
        coVerify(exactly = 1) {
            organizationRepository.update(
                withArg {
                    assert(it.id.value.toString() == organization.id.value.toString())
                    assert(it.name == organization.name)
                    assert(it.userId == organization.userId)
                    assert(it.teams.isEmpty())
                },
            )
        }
        coVerify(exactly = 1) { eventPublisher.publish(ofType<OrganizationUpdatedEvent>()) }
    }
}
