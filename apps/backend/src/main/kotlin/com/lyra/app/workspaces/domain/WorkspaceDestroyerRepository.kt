package com.lyra.app.workspaces.domain

/**
 * Repository to delete a workspace.
 */
interface WorkspaceDestroyerRepository {
    /**
     * Deletes a workspace.
     *
     * @param id The workspace id.
     */
    suspend fun delete(id: WorkspaceId)
}
