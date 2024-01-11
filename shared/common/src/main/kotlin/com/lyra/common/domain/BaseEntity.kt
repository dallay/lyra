package com.lyra.common.domain

import com.lyra.common.domain.bus.event.DomainEvent

/**
 * A base class for entities with a generic identifier.
 *
 * @param ID the type of the identifier
 * @property id The unique identifier of the entity.
 */
abstract class BaseEntity<ID> {
    abstract val id: ID
    private val domainEvents: MutableList<DomainEvent> = mutableListOf()

    fun record(event: DomainEvent) = domainEvents.add(event)
    fun pullDomainEvents(): List<DomainEvent> {
        val events = domainEvents.toList()
        clearDomainEvents()
        return events
    }

    private fun clearDomainEvents() = domainEvents.clear()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseEntity<*>) return false

        if (id != other.id) return false
        if (domainEvents != other.domainEvents) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + domainEvents.hashCode()
        return result
    }
}
