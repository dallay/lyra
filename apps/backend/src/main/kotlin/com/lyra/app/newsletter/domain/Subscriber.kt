package com.lyra.app.newsletter.domain

import com.lyra.app.newsletter.domain.event.SubscriberCreatedEvent
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.BaseEntity
import com.lyra.common.domain.vo.email.Email
import java.time.LocalDateTime
import java.util.*

data class Subscriber(
    override val id: SubscriberId,
    val email: Email,
    var name: Name,
    var status: SubscriberStatus = SubscriberStatus.ENABLED,
    val organizationId: OrganizationId,
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
            id: UUID,
            email: String,
            firstname: String,
            lastname: String? = null,
            status: SubscriberStatus = SubscriberStatus.ENABLED,
            workspaceId: UUID,
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime? = null,
        ): Subscriber {
            val subscriberId = SubscriberId(id)
            val subscriberEmail = Email(email)
            val subscriberName = Name(firstname, lastname)
            val subscriberOrganizationId = OrganizationId(workspaceId)

            val subscriber = Subscriber(
                id = subscriberId,
                email = subscriberEmail,
                name = subscriberName,
                status = status,
                organizationId = subscriberOrganizationId,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
            subscriber.record(
                SubscriberCreatedEvent(
                    subscriber.id.toString(),
                    subscriber.email.email,
                    subscriber.name.fullName(),
                    subscriber.status.name,
                    subscriber.organizationId.toString(),
                ),
            )
            return subscriber
        }
    }
}
