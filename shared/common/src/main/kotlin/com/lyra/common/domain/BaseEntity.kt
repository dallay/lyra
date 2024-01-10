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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseEntity<*>

        return id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0

    fun record(event: DomainEvent) = domainEvents.add(event)
    fun pullDomainEvents(): List<DomainEvent> {
        val events = domainEvents.toList()
        clearDomainEvents()
        return events
    }

    private fun clearDomainEvents() = domainEvents.clear()
}
