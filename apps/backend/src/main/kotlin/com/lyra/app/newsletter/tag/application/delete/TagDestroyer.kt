package com.lyra.app.newsletter.tag.application.delete

import com.lyra.app.newsletter.tag.domain.TagId
import com.lyra.app.newsletter.tag.domain.TagRepository
import com.lyra.app.newsletter.tag.domain.event.DeleteTagEvent
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

/**
 * Service class responsible for deleting tags.
 *
 * @property tagRepository The repository used to manage Tag entities.
 */
@Service
class TagDestroyer(
    private val tagRepository: TagRepository,
    eventPublisher: EventPublisher<DeleteTagEvent>
) {

    private val eventPublisher = EventBroadcaster<DeleteTagEvent>().apply {
        use(eventPublisher)
    }

    /**
     * Deletes a tag from the repository.
     *
     * @param organizationId The ID of the organization to which the tag belongs.
     * @param tagId The ID of the Tag entity to be deleted.
     */
    suspend fun delete(organizationId: OrganizationId, tagId: TagId) {
        log.debug("Deleting tag with id {} for organization {}", tagId.value, organizationId.value)
        tagRepository.delete(organizationId, tagId)
        eventPublisher.publish(
            DeleteTagEvent(
                organizationId.value.toString(),
                tagId.value.toString(),
            ),
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(TagDestroyer::class.java)
    }
}
