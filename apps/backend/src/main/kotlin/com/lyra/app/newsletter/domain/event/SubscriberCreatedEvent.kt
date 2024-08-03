package com.lyra.app.newsletter.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * Data class representing the event of a subscriber being created.
 *
 * @property subscriberId The unique identifier of the subscriber.
 * @property email The email address of the subscriber.
 * @property name The name of the subscriber.
 * @property status The status of the subscriber.
 * @property organizationId The identifier of the organization the subscriber belongs to.
 */
data class SubscriberCreatedEvent(
    val subscriberId: String,
    val email: String,
    val name: String,
    val status: String,
    val organizationId: String,
) : BaseDomainEvent()
