package com.lyra.app.workspaces.infrastructure.persistence.entity

import com.lyra.common.domain.AuditableEntity
import java.time.LocalDateTime
import java.util.*
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table

/**
 * This data class represents a workspace entity in the persistence layer.
 *
 * @property workspaceId The unique identifier of the workspace. This field is mandatory.
 * @property workspaceName The name of the workspace. This field is mandatory.
 * @property userId The unique identifier of the user who owns the workspace. This field is mandatory.
 * @property createdAt The timestamp when the workspace was created. This field is mandatory.
 * @property updatedAt The timestamp when the workspace was last updated. This field is optional.
 */
@Table("workspaces")
data class WorkspaceEntity(
    @Id
    @JvmField
    val workspaceId: UUID,
    val workspaceName: String,
    val userId: UUID,
    override var createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime? = createdAt,
) : AuditableEntity(), Persistable<UUID> {
    /**
     * This method returns the unique identifier of the workspace.
     *
     * @return The unique identifier of the workspace.
     */
    override fun getId(): UUID = workspaceId

    /**
     * This method checks if the workspace is new by comparing the creation and update timestamps.
     *
     * @return A boolean indicating whether the workspace is new.
     */
    override fun isNew(): Boolean = createdAt == updatedAt
}
