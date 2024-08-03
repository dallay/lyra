package com.lyra.app.organization.infrastructure.persistence.entity

import com.lyra.common.domain.AuditableEntity
import java.time.LocalDateTime
import java.util.*
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table

/**
 * This data class represents an organization entity in the persistence layer.
 *
 * @property id The unique identifier of the organization. This field is mandatory.
 * @property name The name of the organization. This field is mandatory.
 * @property userId The unique identifier of the user who owns the organization. This field is mandatory.
 * @property createdAt The timestamp when the organization was created. This field is mandatory.
 * @property updatedAt The timestamp when the organization was last updated. This field is optional.
 */
@Table("organizations")
data class OrganizationEntity(
    @Id
    @JvmField
    val id: UUID,
    val name: String,
    val userId: UUID,
    override var createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime? = createdAt,
) : AuditableEntity(), Persistable<UUID> {
    /**
     * This method returns the unique identifier of the organization.
     *
     * @return The unique identifier of the organization.
     */
    override fun getId(): UUID = id

    /**
     * This method checks if the organization is new by comparing the creation and update timestamps.
     *
     * @return A boolean indicating whether the organization is new.
     */
    override fun isNew(): Boolean = createdAt == updatedAt
}
