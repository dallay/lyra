package com.lyra.app.newsletter.tag.infrastructure.persistence.entity

import com.lyra.common.domain.AuditableEntity
import java.time.LocalDateTime
import java.util.UUID

data class TagWithSubscribersEntity(
    val id: UUID,
    val name: String,
    val color: String,
    val organizationId: UUID,
    val subscribers: Set<String>? = emptySet(),
    override var createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime? = LocalDateTime.now(),
) : AuditableEntity()
