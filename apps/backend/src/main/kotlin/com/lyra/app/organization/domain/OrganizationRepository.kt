package com.lyra.app.organization.domain

/**
 * Represents an organization.
 */
interface OrganizationRepository {
    /**
     * Create a new organization.
     *
     * @param organization The organization to be created.
     */
    suspend fun create(organization: Organization)

    /**
     * Updates an organization.
     *
     * @param organization The organization to be updated.
     */
    suspend fun update(organization: Organization)
}
