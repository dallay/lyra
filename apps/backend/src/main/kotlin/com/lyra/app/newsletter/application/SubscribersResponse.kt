package com.lyra.app.newsletter.application

import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.common.domain.bus.query.Response

data class SubscribersResponse(val subscribers: List<SubscriberResponse>) : Response

data class SubscriberResponse(
    val id: String,
    val email: String,
    val name: String,
    val status: String,
) : Response {
    companion object {
        fun from(subscriber: Subscriber): SubscriberResponse =
            SubscriberResponse(
                id = subscriber.id.toString(),
                email = subscriber.email.email,
                name = subscriber.name.fullName(),
                status = subscriber.status.name,
            )
    }
}
