package com.lyra.app.workspaces.domain

import com.lyra.app.users.domain.UserId

/**
 * Repository to delete a workspace.
 */
interface WorkspaceCollaboratorRemoverRepository {
    /**
     * Deletes a workspace.
     *
     * @param id The workspace id.
     * @param userId The user id.
     */
    suspend fun removeCollaborator(id: WorkspaceId, userId: UserId)
}
