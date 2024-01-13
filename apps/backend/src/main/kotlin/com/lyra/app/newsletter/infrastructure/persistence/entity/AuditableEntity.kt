package com.lyra.app.newsletter.infrastructure.persistence.entity

import java.time.LocalDateTime

/**
 *
 * @created 7/1/24
 */
abstract class AuditableEntity(
    open var createdAt: LocalDateTime = LocalDateTime.now(),
    open var updatedAt: LocalDateTime? = null
)
