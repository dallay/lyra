package com.lyra.common.domain.bus.event

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter

/**
 * EventMultiplexer class is responsible for multiplexing events to multiple consumers based on provided filters.
 * It implements the EventConsumer interface and consumes events of a specific type.
 *
 * @param E The type of events this multiplexer consumes.
 *
 */
class EventMultiplexer<E : DomainEvent> : EventConsumer<E> {
    private val consumers = mutableListOf<Pair<EventFilter<E>, EventConsumer<E>>>()

    /**
     * Adds a consumer to the list of consumers.
     *
     * @param filter The filter to be used for this consumer.
     * @param consumer The consumer to be added.
     */
    fun on(filter: EventFilter<E>, consumer: EventConsumer<E>) {
        consumers.add(filter to consumer)
    }

    /**
     * Consumes the given event by passing it to all consumers that pass the filter.
     *
     * @param event The event to be consumed.
     */
    override suspend fun consume(event: E) {
        consumers.asFlow()
            .filter { (filter, _) -> filter.filter(event) }
            .collect { (_, consumer) -> consumer.consume(event) }
    }
}
