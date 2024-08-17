package com.lyra.common.domain

import com.lyra.common.domain.bus.event.DomainEvent
import java.io.Serializable
import java.time.LocalDateTime

/**
 * A base class for entities with a generic identifier.
 *
 * @param ID the type of the identifier
 * @property id The unique identifier of the entity.
 */
abstract class BaseEntity<ID> : Serializable {
    abstract val id: ID
    open val createdAt: LocalDateTime = LocalDateTime.now()
    open var updatedAt: LocalDateTime? = null
    private val domainEvents: MutableList<DomainEvent> = mutableListOf()

    fun record(event: DomainEvent) = domainEvents.add(event)
    fun pullDomainEvents(): List<DomainEvent> {
        val events = domainEvents.toList()
        clearDomainEvents()
        return events
    }

    private fun clearDomainEvents() = domainEvents.clear()
    @Generated
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseEntity<*>) return false

        if (id != other.id) return false
        if (domainEvents != other.domainEvents) return false

        return true
    }

    @Generated
    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + domainEvents.hashCode()
        return result
    }

    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
