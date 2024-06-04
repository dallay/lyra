package com.lyra.app.workspaces.infrastructure.persistence.entity

import com.lyra.common.domain.AuditableEntity
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table

/**
 * This data class represents a workspace collaborator entity in the persistence layer.
 *
 * @property workspaceId The unique identifier of the workspace. This field is mandatory.
 * @property userId The unique identifier of the user who is a collaborator. This field is mandatory.
 * @property role The role of the user in the workspace. This field is mandatory.
 * @property addedAt The timestamp when the user was added as a collaborator. This field is mandatory.
 */
@Table("workspace_collaborators")
data class WorkspaceCollaboratorEntity(
    val workspaceId: UUID,
    val userId: UUID,
    val role: String,
    val addedAt: LocalDateTime = LocalDateTime.now(),
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
     * This method checks if the workspace collaborator is new by comparing the creation and update timestamps.
     *
     * @return A boolean indicating whether the workspace collaborator is new.
     */
    override fun isNew(): Boolean = createdAt == updatedAt
}

data class WorkspaceCollaboratorId(val workspaceId: UUID, val userId: UUID) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is WorkspaceCollaboratorId) return false

        if (workspaceId != other.workspaceId) return false
        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = workspaceId.hashCode()
        result = 31 * result + userId.hashCode()
        return result
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
