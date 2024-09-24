package com.lyra.app.newsletter.tag.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

open class TagUpdatedEvent(
    val tagId: String,
    val name: String,
    val color: String,
    val organizationId: String,
) : BaseDomainEvent()

class TagSubscriberUpdatedEvent(
    val subscribers: Set<String>,
    tagId: String,
    name: String,
    color: String,
    organizationId: String
) : TagUpdatedEvent(tagId, name, color, organizationId)

class TagSubscriberDeletedEvent(
    val subscribers: Set<String>,
    tagId: String,
    name: String,
    color: String,
    organizationId: String
) : TagUpdatedEvent(tagId, name, color, organizationId)
