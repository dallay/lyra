package com.lyra.app.organization.domain

/**
 * Repository to delete an organization.
 */
interface OrganizationRemoverRepository {
    /**
     * Deletes an organization.
     *
     * @param id The id of the organization to delete.
     */
    suspend fun delete(id: OrganizationId)
}
