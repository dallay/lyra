package com.lyra.app.notifications.application

import com.lyra.app.newsletter.application.SubscriberResponse
import com.lyra.app.newsletter.application.search.active.SearchAllActiveSubscribersQuery
import com.lyra.app.notifications.domain.EmailMessageId
import com.lyra.app.notifications.domain.EmailSender
import com.lyra.app.notifications.domain.NewContentNewsletter
import com.lyra.app.notifications.domain.event.NewContentNewsletterEmailSent
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

/**
 *
 * @created 12/1/24
 */
@Service
class NewContentNewsletterSender(
    private val emailSender: EmailSender,
    private val mediator: Mediator,
    private val eventPublisher: EventPublisher<NewContentNewsletterEmailSent>
) {

    suspend fun sendNewContentNewsletter() {
        log.info("Sending new content newsletter")
        val subscribers = mediator.send(SearchAllActiveSubscribersQuery())
            .also { log.info("Found ${it.subscribers.size} subscribers") }.subscribers

        subscribers.forEach {
            send(it)
        }
    }

    private suspend fun send(subscriber: SubscriberResponse) {
        val contentNewsletter = NewContentNewsletter.send(
            id = EmailMessageId(subscriber.id),
            subscriber = subscriber,
        )
        emailSender.send(contentNewsletter)
        eventPublisher.publish(
            NewContentNewsletterEmailSent(
                id = contentNewsletter.id.value.toString(),
                subscriberId = subscriber.id,
                email = subscriber.email,
            ),
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(NewContentNewsletterSender::class.java)
    }
}
