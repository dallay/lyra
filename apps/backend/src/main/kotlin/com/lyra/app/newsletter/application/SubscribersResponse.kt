package com.lyra.app.newsletter.application

import com.lyra.app.newsletter.domain.Attributes
import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.common.domain.bus.query.Response

data class SubscribersResponse(val subscribers: List<SubscriberResponse>) : Response

data class SubscriberResponse(
    val id: String,
    val email: String,
    val name: String,
    val status: String,
    val attributes: Attributes? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
) : Response {
    companion object {
        fun from(subscriber: Subscriber) = SubscriberResponse(
            id = subscriber.id.value.toString(),
            email = subscriber.email.value,
            name = subscriber.name.fullName(),
            status = subscriber.status.name,
            attributes = subscriber.attributes,
            createdAt = subscriber.createdAt.toString(),
            updatedAt = subscriber.updatedAt?.toString(),
        )
    }
}
