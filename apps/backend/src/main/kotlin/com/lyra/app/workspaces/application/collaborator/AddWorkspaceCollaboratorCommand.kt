package com.lyra.app.workspaces.application.collaborator

import com.lyra.common.domain.bus.command.Command

/**
 * Represents a command to create a workspace collaborator.
 * @property workspaceId The unique identifier of the workspace.
 * @property userId The unique identifier of the user to be added as a collaborator.
 * @property role The role of the user in the workspace.
 * @property addedAt The date and time when the user was added as a collaborator.
 */
data class AddWorkspaceCollaboratorCommand(
    val workspaceId: String,
    val userId: String,
    val role: String,
    val addedAt: String
) : Command
