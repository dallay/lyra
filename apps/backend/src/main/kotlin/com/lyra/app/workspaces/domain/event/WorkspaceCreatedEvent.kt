package com.lyra.app.workspaces.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * WorkspaceCreatedEvent is a data class representing the event of a workspace being created.
 * It extends BaseDomainEvent.
 *
 * @property id The unique identifier of the workspace that was created.
 * @property workspaceName The name of the workspace that was created.
 * @property userId The unique identifier of the user who created the workspace.
 */
data class WorkspaceCreatedEvent(
    val id: String,
    val workspaceName: String,
    val userId: String
) :
    BaseDomainEvent()
