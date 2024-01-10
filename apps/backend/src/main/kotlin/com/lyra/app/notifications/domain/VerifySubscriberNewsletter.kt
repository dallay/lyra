package com.lyra.app.notifications.domain

import com.lyra.app.newsletter.application.SubscriberResponse
import com.lyra.app.notifications.domain.event.VerifySubscriberNewsletterEmailSent
import com.lyra.common.domain.email.Email

/**
 *
 * @created 9/1/24
 */
class VerifySubscriberNewsletter(
    override val id: EmailMessageId,
    subscriber: SubscriberResponse
) : EmailMessage(
    id,
    Email("news@lyra.com"),
    Email(subscriber.email),
    "Welcome to Lyra",
    subscriber.name.plus(" welcome to Lyra!"),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VerifySubscriberNewsletter) return false
        if (!super.equals(other)) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

    companion object {
        fun send(id: EmailMessageId, subscriber: SubscriberResponse): VerifySubscriberNewsletter {
            val emailMessage = VerifySubscriberNewsletter(id, subscriber)
            emailMessage.record(
                VerifySubscriberNewsletterEmailSent(
                    id.value.toString(),
                    subscriber.id, subscriber.email,
                ),
            )
            return emailMessage
        }
    }
}
