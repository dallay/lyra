package com.lyra.app.workspaces.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * WorkspaceCollaboratorUpdatedEvent is a data class representing the event of a workspace collaborator being updated.
 * It extends BaseDomainEvent.
 *
 * @property workspaceId The unique identifier of the workspace where the collaborator was updated.
 * @property userId The unique identifier of the user whose role was updated.
 * @property role The new role of the user in the workspace.
 * @property added A string representing whether the user was added to the workspace or not.
 */
data class WorkspaceCollaboratorUpdatedEvent(
    val workspaceId: String,
    val userId: String,
    val role: String,
    val addedAt: String
) : BaseDomainEvent()
