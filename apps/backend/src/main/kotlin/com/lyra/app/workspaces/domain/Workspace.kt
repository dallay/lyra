package com.lyra.app.workspaces.domain

import com.lyra.app.users.domain.UserId
import com.lyra.app.workspaces.domain.event.WorkspaceCollaboratorAddedEvent
import com.lyra.app.workspaces.domain.event.WorkspaceCollaboratorRemovedEvent
import com.lyra.app.workspaces.domain.event.WorkspaceCreatedEvent
import com.lyra.app.workspaces.domain.event.WorkspaceUpdatedEvent
import com.lyra.common.domain.AggregateRoot
import java.time.LocalDateTime
import java.util.*

/**
 * Workspace is a domain class representing a workspace entity.
 * It extends AggregateRoot with WorkspaceId as the identifier.
 *
 * @property id The unique identifier of the workspace.
 * @property name The name of the workspace.
 * @property userId The unique identifier of the user who owns the workspace.
 * @property createdAt The date and time when the workspace was created.
 * @property updatedAt The date and time when the workspace was last updated.
 */
data class Workspace(
    override val id: WorkspaceId,
    var name: String,
    val userId: UserId,
    private val collaborators: MutableList<WorkspaceCollaborators> = mutableListOf(),
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime? = createdAt
) : AggregateRoot<WorkspaceId>() {

    /**
     * Updates the name of the workspace and records a WorkspaceUpdatedEvent.
     *
     * @param name The new name of the workspace.
     */
    fun update(name: String) {
        this.name = name
        this.updatedAt = LocalDateTime.now()
        record(WorkspaceUpdatedEvent(id.value.toString(), name, userId.value.toString()))
    }

    /**
     * Adds a collaborator to the workspace.
     *
     * @param userId The unique identifier of the user to add as a collaborator.
     * @param role The role of the collaborator.
     */
    fun addCollaborator(userId: UserId, role: WorkspaceRole) {
        // Check if the user is already a collaborator. If so, do nothing.
        if (collaborators.any { it.userId == userId }) return
        val workspaceCollaborator = WorkspaceCollaborators(id, userId, role)
        collaborators.add(workspaceCollaborator)
        record(
            WorkspaceCollaboratorAddedEvent(
                id.value.toString(),
                userId.value.toString(),
                role.toString(),
                workspaceCollaborator.addedAt.toString(),
            ),
        )
    }

    /**
     * Removes a collaborator from the workspace.
     *
     * @param userId The unique identifier of the user to remove as a collaborator.
     */
    fun removeCollaborator(userId: UserId) {
        collaborators.removeIf { it.userId == userId }
        record(
            WorkspaceCollaboratorRemovedEvent(
                id.value.toString(),
                userId.value.toString(),
            ),
        )
    }

    /**
     * Updates the role of a collaborator in the workspace.
     *
     * @param userId The unique identifier of the user whose role is to be updated.
     * @param role The new role of the collaborator.
     */
    fun updateCollaboratorRole(userId: UserId, role: WorkspaceRole) {
        collaborators.find { it.userId == userId }?.updateRole(role)
    }

    /**
     * Gets the list of collaborators in the workspace.
     */
    fun collaborators(): List<WorkspaceCollaborators> = collaborators.toList()

    companion object {
        /**
         * Factory method to create a new Workspace instance and record a WorkspaceCreatedEvent.
         *
         * @param id The unique identifier of the workspace.
         * @param name The name of the workspace.
         * @param userId The unique identifier of the user who owns the workspace.
         * @param createdAt The date and time when the workspace was created.
         * @param updatedAt The date and time when the workspace was last updated.
         * @return A new Workspace instance.
         */
        fun create(
            id: UUID,
            name: String,
            userId: UUID,
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime? = createdAt
        ): Workspace {
            val workspaceId = WorkspaceId(id)
            val customerUserId = UserId(userId)
            val workspace = Workspace(
                workspaceId,
                name,
                customerUserId,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
            workspace.record(
                WorkspaceCreatedEvent(
                    workspaceId.value.toString(),
                    name,
                    customerUserId.value.toString(),
                ),
            )
            return workspace
        }
    }
}
