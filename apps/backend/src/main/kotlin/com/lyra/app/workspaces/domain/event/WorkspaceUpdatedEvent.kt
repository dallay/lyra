package com.lyra.app.workspaces.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * WorkspaceUpdatedEvent is a data class representing the event of a workspace being updated.
 * It extends BaseDomainEvent.
 *
 * @property id The unique identifier of the workspace that was updated.
 * @property workspaceName The new name of the workspace after the update.
 * @property userId The unique identifier of the user who updated the workspace.
 */
data class WorkspaceUpdatedEvent(
    val id: String,
    val workspaceName: String,
    val userId: String
) : BaseDomainEvent()
