package com.lyra.app.workspaces.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * This data class represents the event of a collaborator being added to a workspace.
 *
 * @property workspaceId The ID of the workspace to which a collaborator has been added.
 * @property userId The ID of the user who has been added as a collaborator.
 * @property role The role of the user in the workspace.
 * @property addedAt The timestamp when the user was added as a collaborator.
 */
data class WorkspaceCollaboratorAddedEvent(
    val workspaceId: String,
    val userId: String,
    val role: String,
    val addedAt: String
) : BaseDomainEvent()
