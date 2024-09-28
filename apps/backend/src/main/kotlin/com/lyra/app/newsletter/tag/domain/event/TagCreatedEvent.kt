package com.lyra.app.newsletter.tag.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

data class TagCreatedEvent(
    val tagId: String,
    val name: String,
    val color: String,
    val organizationId: String,
) : BaseDomainEvent()
