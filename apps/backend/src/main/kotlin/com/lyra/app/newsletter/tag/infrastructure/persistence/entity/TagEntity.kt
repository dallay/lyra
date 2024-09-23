package com.lyra.app.newsletter.tag.infrastructure.persistence.entity

import com.lyra.common.domain.AuditableEntity
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table

@Table("tags")
data class TagEntity(
    @Id
    @JvmField
    val id: UUID,
    val name: String,
    val color: String,
    val organizationId: UUID,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime? = createdAt,
) : AuditableEntity(createdAt, updatedAt), Persistable<UUID> {
    override fun getId(): UUID = id

    override fun isNew(): Boolean = createdAt == updatedAt
}
