package com.lyra.app.organization.application.delete

import com.lyra.app.organization.domain.OrganizationId
import com.lyra.app.organization.domain.OrganizationRemoverRepository
import com.lyra.app.organization.domain.event.OrganizationDeletedEvent
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

/**
 * This service class is responsible for deleting organizations.
 *
 * @property destroyer The repository that handles the deletion of organizations.
 * @property eventPublisher The publisher that handles the broadcasting of organization deletion events.
 */
@Service
class OrganizationDestroyer(
    private val destroyer: OrganizationRemoverRepository,
    eventPublisher: EventPublisher<OrganizationDeletedEvent>
) {
    private val eventPublisher = EventBroadcaster<OrganizationDeletedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    /**
     * Deletes an organization with the given id.
     *
     * @param id The id of the organization to be deleted.
     */
    suspend fun delete(id: OrganizationId) {
        log.debug("Deleting organization with id: {}", id)
        destroyer.delete(id)
        eventPublisher.publish(OrganizationDeletedEvent(id.value.toString()))
    }

    companion object {
        private val log = LoggerFactory.getLogger(OrganizationDestroyer::class.java)
    }
}
