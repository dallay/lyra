package com.lyra.app.notifications.application

import com.lyra.app.newsletter.application.SubscriberResponse
import com.lyra.app.newsletter.application.search.SearchAllSubscribersQuery
import com.lyra.app.notifications.domain.EmailMessageId
import com.lyra.app.notifications.domain.EmailSender
import com.lyra.app.notifications.domain.VerifySubscriberNewsletter
import com.lyra.app.notifications.domain.event.VerifySubscriberNewsletterEmailSent
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import java.util.*
import org.slf4j.LoggerFactory

@Service
class VerifySubscriberNewsletterSender(
    private val sender: EmailSender,
    private val mediator: Mediator,
    eventPublisher: EventPublisher<VerifySubscriberNewsletterEmailSent>
) {

    private val eventPublisher = EventBroadcaster<VerifySubscriberNewsletterEmailSent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    suspend fun send() {
        val allSubscribers = mediator.send(SearchAllSubscribersQuery())
        allSubscribers.subscribers.forEach {
            send(it)
        }
    }

    suspend fun send(subscriber: SubscriberResponse) {
        log.info("Sending verify subscriber newsletter to ${subscriber.email}")
        val verifySubscriberNewsletter = VerifySubscriberNewsletter.send(EmailMessageId(UUID.randomUUID()), subscriber)
        sender.send(verifySubscriberNewsletter)
    }

    companion object {
        private val log = LoggerFactory.getLogger(VerifySubscriberNewsletterSender::class.java)
    }
}
