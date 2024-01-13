package com.lyra.common.domain.bus.event

import java.time.LocalDateTime

/**
 * Represents a domain event.
 *
 */
interface DomainEvent {
    /**
     * Returns the version of the event.
     *
     * @return The version of the event.
     */
    fun eventVersion(): Int

    /**
     * Returns the LocalDateTime when this event occurred.
     *
     * @return the LocalDateTime when this event occurred
     */
    fun occurredOn(): LocalDateTime?
}
