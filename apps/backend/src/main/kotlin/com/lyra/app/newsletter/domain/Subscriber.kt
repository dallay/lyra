package com.lyra.app.newsletter.domain

import com.lyra.app.newsletter.domain.event.SubscriberCreatedEvent
import com.lyra.common.domain.BaseEntity
import com.lyra.common.domain.vo.email.Email
import java.time.LocalDateTime
import java.util.*

data class Subscriber(
    override val id: SubscriberId,
    val email: Email,
    var name: Name,
    var status: SubscriberStatus = SubscriberStatus.ENABLED,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime? = null,
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
            status: SubscriberStatus = SubscriberStatus.ENABLED,
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime? = null,
        ): Subscriber {
            val subscriber = Subscriber(
                id = id,
                email = email,
                name = name,
                status = status,
                createdAt = createdAt,
                updatedAt = updatedAt,
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
            status: SubscriberStatus = SubscriberStatus.ENABLED,
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime? = null,
        ): Subscriber = create(
            id = SubscriberId(UUID.randomUUID().toString()),
            email = Email(email),
            name = Name(FirstName(firstname), LastName(lastname)),
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}
