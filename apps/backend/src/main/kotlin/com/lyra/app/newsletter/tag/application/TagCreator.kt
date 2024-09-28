package com.lyra.app.newsletter.tag.application

import com.lyra.app.newsletter.subscriber.application.SubscribersResponse
import com.lyra.app.newsletter.subscriber.application.search.email.GetAllSubscribersByEmailService
import com.lyra.app.newsletter.tag.domain.SubscriberTag
import com.lyra.app.newsletter.tag.domain.SubscriberTagId
import com.lyra.app.newsletter.tag.domain.Tag
import com.lyra.app.newsletter.tag.domain.TagColor
import com.lyra.app.newsletter.tag.domain.TagRepository
import com.lyra.app.newsletter.tag.domain.event.TagCreatedEvent
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import java.util.UUID
import org.slf4j.LoggerFactory

/**
 * Service class responsible for creating tags.
 *
 * @property tagRepository The repository for managing Tag entities.
 * @property eventPublisher The publisher for broadcasting TagCreatedEvent events.
 * @created 15/9/24
 */
@Service
class TagCreator(
    private val tagRepository: TagRepository,
    private val getAllSubscribersByEmailService: GetAllSubscribersByEmailService,
    private val subscriberTagCreator: SubscriberTagCreator,
    eventPublisher: EventPublisher<TagCreatedEvent>
) {
    private val eventPublisher = EventBroadcaster<TagCreatedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    /**
     * Creates a new tag and publishes the corresponding domain events.
     *
     * @param id The unique identifier of the tag.
     * @param name The name of the tag.
     * @param color The color of the tag. Defaults to [TagColor.DEFAULT].
     * @param organizationId The identifier of the organization the tag belongs to.
     * @param subscribers The set of emails subscribed to the tag.
     */
    suspend fun create(
        id: UUID,
        name: String,
        color: TagColor = TagColor.DEFAULT,
        organizationId: UUID,
        subscribers: Set<String>?
    ) {
        log.debug("Creating tag with name {} for organization {}", name, organizationId)
        val subscriberByEmails = subscribers?.let {
            getAllSubscribersByEmailService.searchAllByEmails(
                organizationId.toString(),
                it,
            )
        }

        val tag = Tag.create(id, name, color, organizationId)
        tagRepository.create(tag)
        val domainEvents = tag.pullDomainEvents()
        domainEvents.forEach { eventPublisher.publish(it as TagCreatedEvent) }
        val subscriberTags = toSubscriberTags(subscriberByEmails, id)
        subscriberTags?.forEach {
            subscriberTagCreator.create(it.id.value.first, it.id.value.second)
        }
    }

    private fun toSubscriberTags(
        response: SubscribersResponse?,
        tagId: UUID
    ) = response?.subscribers?.map {
        SubscriberTag(
            SubscriberTagId.create(UUID.fromString(it.id), tagId),
        )
    }?.toMutableSet()

    companion object {
        private val log = LoggerFactory.getLogger(TagCreator::class.java)
    }
}
