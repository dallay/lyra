package com.lyra.app.workspaces.domain

/**
 * Represents a workspace finder repository.
 */
interface WorkspaceFinderRepository {
    /**
     * Find a workspace by its unique identifier.
     *
     * @param id The unique identifier of the workspace.
     * @return The workspace with the given unique identifier.
     */
    suspend fun findById(id: WorkspaceId): Workspace?
}
