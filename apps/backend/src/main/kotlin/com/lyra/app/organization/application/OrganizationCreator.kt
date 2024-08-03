package com.lyra.app.organization.application

import com.lyra.app.organization.domain.Organization
import com.lyra.app.organization.domain.OrganizationRepository
import com.lyra.app.organization.domain.event.OrganizationCreatedEvent
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

/**
 * OrganizationCreator is a service class responsible for creating organizations.
 * It uses an organizationCreator to create an organization and publishes an organizationCreatedEvent
 * for each created organization.
 *
 * @property organizationRepository The OrganizationCreator used to create organizations.
 * @property eventPublisher The EventPublisher used to publish OrganizationCreatedEvents.
 */
@Service
class OrganizationCreator(
    private val organizationRepository: OrganizationRepository,
    eventPublisher: EventPublisher<OrganizationCreatedEvent>
) {
    private val eventPublisher = EventBroadcaster<OrganizationCreatedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    /**
     * Creates an organization using the OrganizationCreator and publishes a
     * [OrganizationCreatedEvent] for the created organization.
     *
     * @param organization The organization to be created.
     */
    suspend fun create(organization: Organization) {
        log.debug("Creating organization with id: {}", organization.id)
        organizationRepository.create(organization)
        val domainEvents = organization.pullDomainEvents()
        domainEvents.forEach {
            eventPublisher.publish(it as OrganizationCreatedEvent)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(OrganizationRepository::class.java)
    }
}
