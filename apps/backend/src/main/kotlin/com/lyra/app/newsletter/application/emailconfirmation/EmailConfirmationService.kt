package com.lyra.app.newsletter.application.emailconfirmation

import com.lyra.app.newsletter.domain.event.SubscriberCreatedEvent
import com.lyra.app.notifications.application.VerifySubscriberNewsletterCommand
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.event.EventConsumer
import com.lyra.common.domain.bus.event.Subscribe
import org.slf4j.LoggerFactory

@Subscribe(filterBy = SubscriberCreatedEvent::class)
@Service
class EmailConfirmationService(
    private val mediator: Mediator,
) : EventConsumer<SubscriberCreatedEvent> {

    /**
     * Consume method for handling events.
     *
     * @param event The event to be consumed.
     */
    override suspend fun consume(event: SubscriberCreatedEvent) {
        log.debug("\uD83D\uDFE2 \uD83D\uDE80 Email confirmation sent to ${event.email}")
        mediator.send(VerifySubscriberNewsletterCommand(event.subscriberId))
    }

    companion object {
        private val log = LoggerFactory.getLogger(EmailConfirmationService::class.java)
    }
}
