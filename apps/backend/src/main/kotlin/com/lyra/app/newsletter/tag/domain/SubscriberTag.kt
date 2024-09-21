package com.lyra.app.newsletter.tag.domain

import com.lyra.app.newsletter.tag.domain.event.SubscriberTaggedEvent
import com.lyra.common.domain.BaseEntity
import java.time.LocalDateTime

data class SubscriberTag(
    override val id: SubscriberTagId,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime? = LocalDateTime.now(),
) : BaseEntity<SubscriberTagId>() {
    companion object {
        fun create(id: SubscriberTagId): SubscriberTag {
            val event = SubscriberTaggedEvent(
                id.value.first.toString(),
                id.value.second.toString(),
            )
            val subscriberTag = SubscriberTag(id)
            subscriberTag.record(event)
            return subscriberTag
        }
    }
}
