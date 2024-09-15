package com.lyra.app.newsletter.tag.infrastructure.persistence.entity

import com.lyra.common.domain.AuditableEntity
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.relational.core.mapping.Table

@Table("tags")
data class TagEntity(
    val id: UUID,
    val name: String,
    val color: String,
    override var createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime? = LocalDateTime.now(),
) : AuditableEntity() {

    companion object {
        fun create(
            id: UUID,
            name: String,
            color: String,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime?,
        ): TagEntity {
            return TagEntity(
                id = id,
                name = name,
                color = color,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}
