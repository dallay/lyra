package com.lyra.app.organization.domain

import com.lyra.app.users.domain.UserId

/**
 * Repository to find organizations.
 */
interface OrganizationFinderRepository {
    /**
     * Find an organization by its unique identifier.
     *
     * @param id The unique identifier of the organization.
     * @return The organization with the given unique identifier.
     */
    suspend fun findById(id: OrganizationId): Organization?

    /**
     * Find all organizations that belong to a user.
     * Should return an empty list if the user does not belong to any organization.
     * @param userId The unique identifier of the user.
     * @return The list of organizations that belong to the user.
     */
    suspend fun findAll(userId: UserId): List<Organization>
}
