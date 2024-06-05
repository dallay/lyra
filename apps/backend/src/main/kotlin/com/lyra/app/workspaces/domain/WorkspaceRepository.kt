package com.lyra.app.workspaces.domain

/**
 * Represents a workspace.
 */
interface WorkspaceRepository {
    /**
     * Create a new workspace.
     *
     * @param workspace The workspace to be created.
     */
    suspend fun create(workspace: Workspace)
    /**
     * Creates a new workspace collaborator.
     *
     * @param workspace The workspace collaborator to create.
     */
    suspend fun create(workspace: WorkspaceCollaborators)

    /**
     * Updates a workspace.
     *
     * @param workspace The workspace to be updated.
     */
    suspend fun update(workspace: Workspace)
}
