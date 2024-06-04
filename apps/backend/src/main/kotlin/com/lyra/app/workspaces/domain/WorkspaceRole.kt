package com.lyra.app.workspaces.domain

/**
 * WorkspaceRole is an enum representing the different roles a user can have in a workspace.
 *
 * @property OWNER Represents the owner of the workspace. The owner has full control over the workspace.
 * @property COLLABORATOR Represents a collaborator in the workspace.
 * A collaborator can contribute to the workspace but does not have the same level of control as the owner.
 */
enum class WorkspaceRole {
    OWNER,
    COLLABORATOR
}
