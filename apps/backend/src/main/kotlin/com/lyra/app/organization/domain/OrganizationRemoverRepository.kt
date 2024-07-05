package com.lyra.app.organization.domain

/**
 * Repository to delete a workspace.
 */
interface OrganizationRemoverRepository {
    /**
     * Deletes a workspace.
     *
     * @param id The workspace id.
     */
    suspend fun delete(id: OrganizationId)
}
