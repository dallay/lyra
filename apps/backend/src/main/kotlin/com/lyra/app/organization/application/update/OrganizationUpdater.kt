package com.lyra.app.organization.application.update

import com.lyra.app.organization.domain.OrganizationFinderRepository
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.app.organization.domain.OrganizationRepository
import com.lyra.app.organization.domain.event.OrganizationUpdatedEvent
import com.lyra.app.organization.domain.exception.OrganizationNotFoundException
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

/**
 * Service class responsible for updating organizations.
 *
 * @property organizationRepository The repository for organization data.
 * @property organizationFinderRepository The repository for finding organizations.
 * @property eventPublisher The publisher for organization update events.
 */
@Service
class OrganizationUpdater(
    private val organizationRepository: OrganizationRepository,
    private val organizationFinderRepository: OrganizationFinderRepository,
    eventPublisher: EventPublisher<OrganizationUpdatedEvent>
) {
    private val eventPublisher = EventBroadcaster<OrganizationUpdatedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    /**
     * Updates an organization with the given id and name.
     * Throws a [OrganizationNotFoundException] if the organization is not found.
     *
     * @param teamId The id of the organization to update.
     * @param name The new name of the organization.
     */
    suspend fun update(teamId: String, name: String) {
        log.info("Updating organization with id: $teamId")
        val organizationId = OrganizationId(teamId)
        val organization = organizationFinderRepository.findById(organizationId)
            ?: throw OrganizationNotFoundException("Organization not found")
        organization.update(name)
        organizationRepository.update(organization)
        val domainEvents = organization.pullDomainEvents()
        domainEvents.forEach {
            eventPublisher.publish(it as OrganizationUpdatedEvent)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(OrganizationUpdater::class.java)
    }
}
