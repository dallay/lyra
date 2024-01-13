package com.lyra.common.domain.bus.event

/**
 * Represents an event publisher that is capable of publishing events.
 *
 * @param E the type of event to be published
 *
 */
interface EventPublisher<E : DomainEvent> {
    /**
     * Publishes the given event.
     *
     * @param event The event to be published.
     */
    suspend fun publish(event: E)

    /**
     * Publishes multiple events.
     *
     * @param events The events to be published.
     */
    suspend fun publish(events: List<E>) = events.forEach { publish(it) }
}
