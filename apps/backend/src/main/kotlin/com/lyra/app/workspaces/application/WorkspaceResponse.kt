package com.lyra.app.workspaces.application

import com.lyra.app.workspaces.domain.Workspace
import com.lyra.common.domain.bus.query.Response

/**
 * Represents a workspace response.
 */
data class WorkspaceResponse(
    val id: String,
    val name: String,
    val userId: String,
    val createdAt: String,
    val updatedAt: String?
) : Response {
    companion object {
        fun from(workspace: Workspace) = WorkspaceResponse(
            id = workspace.id.value.toString(),
            name = workspace.name,
            userId = workspace.userId.value.toString(),
            createdAt = workspace.createdAt.toString(),
            updatedAt = workspace.updatedAt?.toString(),
        )
    }
}

/**
 * Represents a list of workspace responses.
 */
data class WorkspaceResponses(val data: List<WorkspaceResponse>) : Response {
    companion object {
        fun from(workspaces: List<Workspace>) = WorkspaceResponses(
            data = workspaces.map { WorkspaceResponse.from(it) },
        )
    }
}
