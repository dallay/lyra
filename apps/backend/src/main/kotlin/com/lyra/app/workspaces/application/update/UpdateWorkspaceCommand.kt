package com.lyra.app.workspaces.application.update

import com.lyra.common.domain.bus.command.Command

/**
 * Represents a command to update a workspace.
 * @property id The unique identifier of the workspace.
 * @property name The new name of the workspace.
 */
data class UpdateWorkspaceCommand(val id: String, val name: String) : Command
