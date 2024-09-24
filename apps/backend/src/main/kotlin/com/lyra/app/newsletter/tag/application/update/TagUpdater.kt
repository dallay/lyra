package com.lyra.app.newsletter.tag.application.update

import com.lyra.app.newsletter.subscriber.application.SubscribersResponse
import com.lyra.app.newsletter.subscriber.application.search.email.GetAllSubscribersByEmailService
import com.lyra.app.newsletter.tag.application.SubscriberTagCreator
import com.lyra.app.newsletter.tag.domain.SubscriberTag
import com.lyra.app.newsletter.tag.domain.SubscriberTagId
import com.lyra.app.newsletter.tag.domain.SubscriberTagRepository
import com.lyra.app.newsletter.tag.domain.Tag
import com.lyra.app.newsletter.tag.domain.TagColor
import com.lyra.app.newsletter.tag.domain.TagId
import com.lyra.app.newsletter.tag.domain.TagRepository
import com.lyra.app.newsletter.tag.domain.TagSearchRepository
import com.lyra.app.newsletter.tag.domain.event.TagSubscriberDeletedEvent
import com.lyra.app.newsletter.tag.domain.event.TagSubscriberUpdatedEvent
import com.lyra.app.newsletter.tag.domain.event.TagUpdatedEvent
import com.lyra.app.newsletter.tag.domain.exceptions.TagNotFoundException
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import com.lyra.common.domain.vo.email.Email
import java.util.UUID
import org.slf4j.LoggerFactory

/**
 * Service responsible for updating a tag and handling subscribers associated with the tag.
 *
 * @param tagRepository Repository for updating the tag entity.
 * @param tagSearchRepository Repository for finding the tag entity.
 * @param allSubscribersByEmailService Service to fetch subscribers by email.
 * @param subscriberTagCreator Service to create subscriber tags.
 * @param subscriberTagRepository Repository for managing subscriber tags.
 * @param eventPublisher Event publisher for broadcasting domain events.
 */
@Service
class TagUpdater(
    private val tagRepository: TagRepository,
    private val tagSearchRepository: TagSearchRepository,
    private val allSubscribersByEmailService: GetAllSubscribersByEmailService,
    private val subscriberTagCreator: SubscriberTagCreator,
    private val subscriberTagRepository: SubscriberTagRepository,
    eventPublisher: EventPublisher<TagUpdatedEvent>
) {
    private val eventPublisher = EventBroadcaster<TagUpdatedEvent>().apply {
        use(eventPublisher)
    }

    /**
     * Updates a tag's name, color, and subscribers list.
     *
     * @param id Tag ID to be updated.
     * @param name New name for the tag (optional).
     * @param color New color for the tag (optional).
     * @param organizationId Organization ID associated with the tag.
     * @param subscribers Set of subscriber emails to update the tag with (optional).
     */
    suspend fun update(
        id: UUID,
        name: String?,
        color: TagColor?,
        organizationId: UUID,
        subscribers: Set<String>?
    ) {
        log.debug("Updating tag with id {} and name {} for organization {}", id, name, organizationId)
        val tag = tagSearchRepository.findById(OrganizationId(organizationId), TagId(id))
        if (tag == null) {
            log.warn("Tag with id {} not found for organization {}", id, organizationId)
            throw TagNotFoundException("Tag with id $id not found for organization $organizationId")
        }
        val subscriberEmails = subscribers?.map(::Email)?.toMutableSet() ?: mutableSetOf()

        tag.update(name, color)
        tagRepository.update(tag)
        eventPublisher.publish(tag.pullDomainEvents().map { it as TagUpdatedEvent })

        val currentSubscribers = tag.subscribers ?: mutableSetOf()
        handleSubscribers(tag, currentSubscribers, subscriberEmails, organizationId, id)
    }

    /**
     * Handles adding and removing subscribers from the tag.
     */
    private suspend fun handleSubscribers(
        tag: Tag,
        currentSubscribers: Set<Email>,
        newSubscribers: Set<Email>,
        organizationId: UUID,
        tagId: UUID
    ) {
        val subscribersToAdd = newSubscribers - currentSubscribers
        if (subscribersToAdd.isNotEmpty()) {
            createSubscribersWithTag(subscribersToAdd, organizationId, tagId)
            tag.addSubscriberEmails(subscribersToAdd)
            eventPublisher.publish(tag.pullDomainEvents().map { it as TagSubscriberUpdatedEvent })
        }

        val subscribersToRemove = currentSubscribers - newSubscribers
        if (subscribersToRemove.isNotEmpty()) {
            deleteSubscribersWithTag(subscribersToRemove, organizationId, tagId)
            tag.removeSubscriberEmails(subscribersToRemove)
            eventPublisher.publish(tag.pullDomainEvents().map { it as TagSubscriberDeletedEvent })
        }
    }

    /**
     * Adds new subscribers to the tag.
     */
    private suspend fun createSubscribersWithTag(
        subscribersToAdd: Set<Email>,
        organizationId: UUID,
        id: UUID
    ) {
        val subscriberByEmails = allSubscribersByEmailService.searchAllByEmails(
            organizationId.toString(),
            subscribersToAdd.map(Email::value).toSet(),
        )
        toSubscriberTags(subscriberByEmails, id)?.forEach {
            subscriberTagCreator.create(it.id.value.first, it.id.value.second)
        }
    }

    /**
     * Removes subscribers from the tag.
     */
    private suspend fun deleteSubscribersWithTag(
        subscribersToRemove: Set<Email>,
        organizationId: UUID,
        id: UUID
    ) {
        val subscriberByEmails = allSubscribersByEmailService.searchAllByEmails(
            organizationId.toString(),
            subscribersToRemove.map(Email::value).toSet(),
        )
        toSubscriberTags(subscriberByEmails, id)?.forEach { subscriberTag ->
            subscriberTagRepository.delete(subscriberTag)
        }
    }

    /**
     * Maps the subscribers response to a set of SubscriberTag entities.
     */
    private fun toSubscriberTags(
        response: SubscribersResponse?,
        tagId: UUID
    ): Set<SubscriberTag>? {
        return response?.subscribers?.map {
            SubscriberTag(SubscriberTagId.create(UUID.fromString(it.id), tagId))
        }?.toSet()
    }

    companion object {
        private val log = LoggerFactory.getLogger(TagUpdater::class.java)
    }
}
