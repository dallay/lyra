package com.lyra.common.domain.bus.event

import kotlin.reflect.KClass

/**
 * A type match event filter that determines if an event is an instance of a specific class.
 *
 * @param T the type of events to filter
 * @property clazz the class to match against events
 * @constructor Creates a new instance of [TypeMatchEventFilter] with the given class.
 * @see EventFilter
 * @see EventPublisher
 * @see EventConsumer
 * @see EventMultiplexer
 *
 *
 */
class TypeMatchEventFilter<T : DomainEvent>(
    private val clazz: KClass<T>
) : EventFilter<T> {
    override suspend fun filter(event: T): Boolean = clazz.isInstance(event)
}
