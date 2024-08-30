package com.lyra.app.newsletter.application

import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.app.newsletter.domain.SubscriberRepository
import com.lyra.app.newsletter.domain.event.SubscriberCreatedEvent
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import java.util.*
import org.slf4j.LoggerFactory

/**
 * Service class responsible for registering subscribers.
 *
 * @property subscriberRepository The repository for managing subscribers.
 * @property eventPublisher The publisher for broadcasting domain events.
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

    /**
     * Function to register a new subscriber.
     *
     * @param id The unique identifier of the subscriber.
     * @param email The email address of the subscriber.
     * @param firstName The first name of the subscriber.
     * @param lastName The last name of the subscriber. This can be null.
     * @param organizationId The identifier of the organization the subscriber belongs to.
     */
    suspend fun register(
        id: UUID,
        email: String,
        firstName: String? = null,
        lastName: String? = null,
        organizationId: UUID
    ) {
        log.debug("Registering subscriber with email: $email")

        val subscriber = Subscriber.create(id, email, firstName, lastName, organizationId = organizationId)
        subscriberRepository.create(subscriber)
        val domainEvents = subscriber.pullDomainEvents()

        domainEvents.forEach {
            eventPublisher.publish(it as SubscriberCreatedEvent)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(SubscriberRegistrator::class.java)
    }
}
