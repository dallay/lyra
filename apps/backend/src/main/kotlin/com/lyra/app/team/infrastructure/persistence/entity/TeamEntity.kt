package com.lyra.app.team.infrastructure.persistence.entity

import com.lyra.common.domain.AuditableEntity
import java.time.LocalDateTime
import java.util.*
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table

/**
 * This data class represents a team entity in the persistence layer.
 *
 * @property teamId The unique identifier of the team. This field is mandatory.
 * @property organizationId The unique identifier of the organization. This field is mandatory.
 * @property name The name of the team. This field is mandatory.
 * @property createdAt The timestamp when the team was created. This field is mandatory.
 * @property updatedAt The timestamp when the team was last updated. This field is optional.
 */
@Table("teams")
data class TeamEntity(
    @Id
    @JvmField
    val teamId: UUID,
    val organizationId: UUID,
    val name: String,
    override var createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime? = createdAt,
) : AuditableEntity(createdAt, updatedAt), Persistable<UUID> {
    /**
     * This method returns the unique identifier of the team.
     *
     * @return The unique identifier of the team.
     */
    override fun getId(): UUID = teamId

    /**
     * This method checks if the team is new by comparing the creation and update timestamps.
     *
     * @return A boolean indicating whether the team is new.
     */
    override fun isNew(): Boolean = createdAt == updatedAt
}
