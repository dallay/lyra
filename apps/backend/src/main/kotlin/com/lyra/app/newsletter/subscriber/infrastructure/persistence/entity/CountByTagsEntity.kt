package com.lyra.app.newsletter.subscriber.infrastructure.persistence.entity

/**
 * Projection entity for counting subscribers by tags.
 *
 * This entity represents a projection of the subscriber entity, containing the tags of the subscribers
 * and the count of subscribers with that tags.
 *
 * @property tag The tags of the subscribers.
 * @property count The count of subscribers with the specified tags.
 */
data class CountByTagsEntity(
    val tag: String,
    val count: Long
)
