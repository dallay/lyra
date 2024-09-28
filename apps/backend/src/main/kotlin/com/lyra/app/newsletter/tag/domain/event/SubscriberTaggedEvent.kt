package com.lyra.app.newsletter.tag.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

data class SubscriberTaggedEvent(
    val subscriberId: String,
    val tagId: String,
) : BaseDomainEvent()
