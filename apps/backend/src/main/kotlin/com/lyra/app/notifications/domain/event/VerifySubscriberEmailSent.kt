package com.lyra.app.notifications.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 *
 * @created 9/1/24
 */
data class VerifySubscriberEmailSent(
    val id: String,
    val subscriberId: String,
    val email: String,
) : BaseDomainEvent()
