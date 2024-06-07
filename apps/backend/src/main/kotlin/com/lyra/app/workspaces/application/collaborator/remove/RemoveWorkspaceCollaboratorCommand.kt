package com.lyra.app.workspaces.application.collaborator.remove

import com.lyra.common.domain.bus.command.Command

/**
 * Represents a command to remove a collaborator from a workspace.
 *
 * @property workspaceId The ID of the workspace from which the collaborator is to be removed.
 * @property userId The ID of the user to be removed as a collaborator.
 */
data class RemoveWorkspaceCollaboratorCommand(
    val workspaceId: String,
    val userId: String
) : Command
