package com.lyra.app.workspaces.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * Event that represents a workspace being deleted.
 *
 * @property workspaceId The id of the workspace that was deleted.
 */
data class WorkspaceDeletedEvent(
    val workspaceId: String
) : BaseDomainEvent()
