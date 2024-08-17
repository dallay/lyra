package com.lyra.common.domain

import java.io.Serializable
import java.time.LocalDateTime

/**
 * Base class for entities that need to be audited.
 * @property createdAt The date and time when the entity was created.
 * @property updatedAt The date and time when the entity was last updated.
 * @constructor Creates an auditable entity.
 * @param createdAt The date and time when the entity was created.
 * @param updatedAt The date and time when the entity was last updated.
 * @see LocalDateTime
 * @see LocalDateTime.now
 */
abstract class AuditableEntity(
    open val createdAt: LocalDateTime = LocalDateTime.now(),
    open var updatedAt: LocalDateTime? = null
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
