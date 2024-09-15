package com.lyra.app.newsletter.tag.application

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
     */
    suspend fun create(
        id: UUID,
        name: String,
        color: TagColor = TagColor.DEFAULT
    ) {
        log.debug("Creating tag with name: $name")
        val tag = Tag.create(id, name, color)
        tagRepository.create(tag)
        val domainEvents = tag.pullDomainEvents()
        domainEvents.forEach { eventPublisher.publish(it as TagCreatedEvent) }
    }

    companion object {
        private val log = LoggerFactory.getLogger(TagCreator::class.java)
    }
}
