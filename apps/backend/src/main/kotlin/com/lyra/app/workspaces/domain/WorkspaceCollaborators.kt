package com.lyra.app.workspaces.domain

import com.lyra.app.users.domain.UserId
import com.lyra.app.workspaces.domain.event.WorkspaceCollaboratorAddedEvent
import com.lyra.app.workspaces.domain.event.WorkspaceCollaboratorUpdatedEvent
import com.lyra.common.domain.BaseEntity
import java.time.LocalDateTime

class WorkspaceCollaborators(
    override val id: WorkspaceId,
    val userId: UserId,
    var role: WorkspaceRole,
    val addedAt: LocalDateTime = LocalDateTime.now()
) : BaseEntity<WorkspaceId>() {
    fun updateRole(role: WorkspaceRole) {
        this.role = role
        record(
            WorkspaceCollaboratorUpdatedEvent(
                id.value.toString(),
                userId.value.toString(),
                role.toString(),
                addedAt.toString(),
            ),
        )
    }

    companion object {
        fun create(workspaceId: String, userId: String, role: WorkspaceRole): WorkspaceCollaborators {
            val wId = WorkspaceId(workspaceId)
            val uId = UserId(userId)
            val workspaceCollaborator = WorkspaceCollaborators(wId, uId, role)
            workspaceCollaborator.record(
                WorkspaceCollaboratorAddedEvent(
                    wId.value.toString(),
                    uId.value.toString(),
                    role.toString(),
                    workspaceCollaborator.addedAt.toString(),
                ),
            )
            return workspaceCollaborator
        }
    }
}
