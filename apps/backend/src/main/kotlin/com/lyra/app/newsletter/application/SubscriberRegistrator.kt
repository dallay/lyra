package com.lyra.app.newsletter.application

import com.lyra.app.newsletter.domain.Name
import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.app.newsletter.domain.SubscriberId
import com.lyra.app.newsletter.domain.SubscriberRepository
import com.lyra.app.newsletter.domain.event.SubscriberCreatedEvent
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import com.lyra.common.domain.email.Email
import org.slf4j.LoggerFactory

/**
 *
 * @created 6/1/24
 */
@Service
class SubscriberRegistrator(
    private val subscriberRepository: SubscriberRepository,
    eventPublisher: EventPublisher<SubscriberCreatedEvent>
) {
    private val eventPublisher = EventBroadcaster<SubscriberCreatedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    suspend fun register(id: SubscriberId, email: Email, name: Name) {
        log.info("Registering subscriber with email: $email")

        val subscriber = Subscriber.create(id, email, name)
        subscriberRepository.create(subscriber)
        val domainEvents = subscriber.pullDomainEvents()
        log.debug("Pulling {} events from subscriber", domainEvents.size)

        domainEvents.forEach {
            eventPublisher.publish(it as SubscriberCreatedEvent)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(SubscriberRegistrator::class.java)
    }
}
