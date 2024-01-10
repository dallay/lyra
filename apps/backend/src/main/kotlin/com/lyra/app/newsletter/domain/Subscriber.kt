package com.lyra.app.newsletter.domain

import com.lyra.app.newsletter.domain.event.SubscriberCreatedEvent
import com.lyra.common.domain.BaseEntity
import com.lyra.common.domain.email.Email
import java.util.*

class Subscriber(
    override val id: SubscriberId,
    val email: Email,
    var name: Name,
    var status: SubscriberStatus = SubscriberStatus.DISABLED
) : BaseEntity<SubscriberId>() {
    fun updateName(name: Name) {
        this.name = name
    }

    fun updateStatus(status: SubscriberStatus) {
        this.status = status
    }

    companion object {
        fun create(
            id: SubscriberId,
            email: Email,
            name: Name,
            status: SubscriberStatus = SubscriberStatus.DISABLED
        ): Subscriber {
            val subscriber = Subscriber(
                id = id,
                email = email,
                name = name,
                status = status,
            )
            subscriber.record(
                SubscriberCreatedEvent(
                    subscriber.id.toString(),
                    subscriber.email.email,
                    subscriber.name.fullName(),
                    subscriber.status.name,
                ),
            )
            return subscriber
        }
        fun create(
            email: String,
            firstname: String,
            lastname: String,
            status: SubscriberStatus = SubscriberStatus.DISABLED
        ): Subscriber = create(
            id = SubscriberId(UUID.randomUUID().toString()),
            email = Email(email),
            name = Name(FirstName(firstname), LastName(lastname)),
            status = status,
        )
    }
}
