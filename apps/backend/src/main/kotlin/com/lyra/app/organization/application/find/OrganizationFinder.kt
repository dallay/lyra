package com.lyra.app.organization.application.find

import com.lyra.app.organization.domain.OrganizationFinderRepository
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.Service

/**
 * This is a service class that handles the finding of organizations.
 * It uses a [OrganizationFinderRepository] to find an organization by its ID.
 *
 * @property finder [OrganizationFinderRepository] The repository to use for finding organizations.
 */
@Service
class OrganizationFinder(private val finder: OrganizationFinderRepository) {

    /**
     * This function is used to find an organization by its ID.
     * It is a suspending function, meaning it can be paused and resumed at a later time.
     * This makes it suitable for use in a coroutine context, where it can be used for non-blocking IO operations.
     *
     * @param id The ID of the organization to find.
     * @return The organization found, or null if no organization was found with the given ID.
     */
    suspend fun find(id: OrganizationId) = finder.findById(id)
}
