package com.lyra.app.workspaces.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * This data class represents the event of a collaborator being removed from a workspace.
 *
 * @property workspaceId The ID of the workspace from which a collaborator has been removed. This field is mandatory.
 * @property userId The ID of the user who has been removed as a collaborator. This field is mandatory.
 */
data class WorkspaceCollaboratorRemovedEvent(
    val workspaceId: String,
    val userId: String
) : BaseDomainEvent()
