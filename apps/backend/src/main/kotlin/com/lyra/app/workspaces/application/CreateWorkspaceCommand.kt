package com.lyra.app.workspaces.application

import com.lyra.common.domain.bus.command.Command

/**
 * Represents a command to create a workspace.
 *
 * @property id Unique identifier for the workspace.
 * @property name Name of the workspace.
 * @property userId The user ID of the workspace creator.
 */
data class CreateWorkspaceCommand(
    val id: String,
    val name: String,
    val userId: String,
) : Command
