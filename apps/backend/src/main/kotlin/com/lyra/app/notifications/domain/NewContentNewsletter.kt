package com.lyra.app.notifications.domain

import com.lyra.app.newsletter.subscriber.application.SubscriberResponse
import com.lyra.app.notifications.domain.event.NewContentNewsletterEmailSent
import com.lyra.common.domain.Generated
import com.lyra.common.domain.vo.email.Email

class NewContentNewsletter(
    override val id: EmailMessageId,
    subscriber: SubscriberResponse
) : EmailMessage(
    id,
    Email("news@lyra.com"),
    Email(subscriber.email),
    "Welcome to Lyra",
    subscriber.name.plus(" welcome to Lyra!"),
) {
    @Generated
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NewContentNewsletter) return false
        if (!super.equals(other)) return false

        if (id != other.id) return false

        return true
    }

    @Generated
    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

    companion object {
        fun send(id: EmailMessageId, subscriber: SubscriberResponse): NewContentNewsletter {
            val emailMessage = NewContentNewsletter(id, subscriber)
            emailMessage.record(
                NewContentNewsletterEmailSent(
                    id.value.toString(),
                    subscriber.id, subscriber.email,
                ),
            )
            return emailMessage
        }
    }
}
