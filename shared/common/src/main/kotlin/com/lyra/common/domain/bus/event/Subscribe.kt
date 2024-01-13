package com.lyra.common.domain.bus.event

import kotlin.reflect.KClass

/**
 * Annotation used to mark a method as a subscriber for events.
 *
 * This annotation should be applied to methods that should be invoked when events of a certain type are
 * published. The annotated method should have exactly one parameter to receive the event object.
 *
 * @param filterBy Specifies the type of events that the annotated method should subscribe to.
 *                 Only events of the specified type or its subclasses will be delivered to the method.
 *                 If no type is specified, the method will be subscribed to all events.
 *
 */
annotation class Subscribe(
    val filterBy: KClass<*>
)
