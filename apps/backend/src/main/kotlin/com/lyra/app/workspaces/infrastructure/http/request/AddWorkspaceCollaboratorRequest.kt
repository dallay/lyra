package com.lyra.app.workspaces.infrastructure.http.request

import jakarta.validation.constraints.NotBlank

/**
 * This data class represents the request body for adding a collaborator to a workspace.
 *
 * @property workspaceId The ID of the workspace to which a collaborator is being added. This field is mandatory.
 * @property userId The ID of the user who is being added as a collaborator. This field is mandatory.
 * @property role The role of the user in the workspace. This field is mandatory.
 */
data class AddWorkspaceCollaboratorRequest(
    @field:NotBlank(message = "Workspace Id is required")
    val workspaceId: String,
    @field:NotBlank(message = "User Id is required")
    val userId: String,
    @field:NotBlank(message = "Role is required")
    val role: String
)
