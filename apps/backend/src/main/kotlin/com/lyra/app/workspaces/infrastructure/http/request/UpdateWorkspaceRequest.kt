package com.lyra.app.workspaces.infrastructure.http.request

import jakarta.validation.constraints.NotBlank

data class UpdateWorkspaceRequest(
    @field:NotBlank
    val name: String,
)
