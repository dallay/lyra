package com.lyra.app.workspaces.infrastructure.http.request

import jakarta.validation.constraints.NotBlank

/**
 * This data class represents the request body for creating a workspace.
 *
 * @property name The name of the workspace to be created. This field is mandatory.
 * @property userId The ID of the user who is creating the workspace. This field is mandatory.
 */
data class CreateWorkspaceRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,
    @field:NotBlank(message = "User Id is required")
    val userId: String,
)
