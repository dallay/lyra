package com.lyra.app.newsletter.subscriber.infrastructure.persistence.entity

/**
 * Projection entity for counting subscribers by their status.
 *
 * @property status The status of the subscribers.
 * @property count The count of subscribers with the specified status.
 * @created 9/9/24
 */
data class CountByStatusEntity(
    val status: String,
    val count: Long
)
