package com.lyra.app.newsletter.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 *
 * @created 6/1/24
 */
data class SubscriberCreatedEvent(
    val subscriberId: String,
    val email: String,
    val name: String,
    val status: String
) : BaseDomainEvent()
