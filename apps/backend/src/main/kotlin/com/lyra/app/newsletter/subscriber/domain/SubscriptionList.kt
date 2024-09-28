package com.lyra.app.newsletter.subscriber.domain

import com.lyra.common.domain.BaseEntity
import java.util.*

/**
 * A subscription list is a list of subscribers.
 * It can be used to send newsletters to a group of subscribers.
 * @created 6/1/24
 */
data class SubscriptionList(
    override val id: SubscriptionListId,
    var name: String,
    var description: String? = null,
    val subscribers: MutableList<Subscriber> = mutableListOf()
) : BaseEntity<SubscriptionListId>() {
    fun addSubscriber(subscriber: Subscriber) {
        subscribers.add(subscriber)
    }

    fun removeSubscriber(subscriber: Subscriber) {
        subscribers.remove(subscriber)
    }

    fun updateName(name: String) {
        this.name = name
    }

    fun updateDescription(description: String) {
        this.description = description
    }

    companion object {
        fun create(name: String, description: String? = null): SubscriptionList {
            return SubscriptionList(
                id = SubscriptionListId(UUID.randomUUID()),
                name = name,
                description = description,
            )
        }
    }
}
