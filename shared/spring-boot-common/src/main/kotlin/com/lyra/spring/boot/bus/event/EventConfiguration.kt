package com.lyra.spring.boot.bus.event

import com.lyra.common.domain.bus.event.DomainEvent
import com.lyra.common.domain.bus.event.EventConsumer
import com.lyra.common.domain.bus.event.EventFilter
import com.lyra.common.domain.bus.event.Subscribe
import com.lyra.common.domain.bus.event.TypeMatchEventFilter
import kotlin.reflect.KClass
import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration

/**
 * Configuration class for handling event configuration in the application.
 *
 * @param applicationContext the application context
 * @constructor Creates a new configuration class for handling event configuration in the application.
 * @property applicationContext the application context
 *
 */
@Suppress("UNCHECKED_CAST")
@Configuration
open class EventConfiguration(
    private val applicationContext: ApplicationContext
) {
    /**
     * Configures the event emitter by subscribing all event consumers to the given event emitter.
     *
     * @param eventEmitter The event emitter to which the event consumers should be subscribed.
     */
    @Autowired(required = true)
    fun configEventEmitter(eventEmitter: EventEmitter<DomainEvent>) {
        applicationContext.getBeansOfType(EventConsumer::class.java).values.forEach {
            it.javaClass.annotations.filterIsInstance<Subscribe>()
                .forEach { annotation ->
                    val filter = createFilter<DomainEvent>(annotation)
                    eventEmitter.on(filter, it)
                }
        }
    }

    /**
     * Creates an EventFilter for a specified DomainEvent type based on the given Subscribe mapping.
     * If the filterBy class is a valid bean in the applicationContext, it will be used.
     * Otherwise, a TypeMatchEventFilter will be created using the filterBy class.
     *
     * @param mapping The Subscribe mapping that contains the filterBy class.
     * @return An EventFilter for the specified DomainEvent type based on the given mapping.
     */
    private inline fun <reified T : DomainEvent> createFilter(mapping: Subscribe): EventFilter<T> {
        val filterBeen = try {
            applicationContext.getBean(mapping.filterBy.java)
        } catch (_: BeansException) {
            TypeMatchEventFilter(mapping.filterBy as KClass<T>)
        }

        return if (filterBeen is EventFilter<*>) {
            filterBeen as EventFilter<T>
        } else {
            TypeMatchEventFilter(mapping.filterBy as KClass<T>)
        }
    }
}
