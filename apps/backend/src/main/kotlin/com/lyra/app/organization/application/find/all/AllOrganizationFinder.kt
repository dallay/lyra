package com.lyra.app.organization.application.find.all

import com.lyra.app.organization.application.OrganizationResponses
import com.lyra.app.organization.domain.OrganizationFinderRepository
import com.lyra.app.users.domain.UserId
import com.lyra.common.domain.Service
import org.slf4j.LoggerFactory

/**
 * This service is responsible for finding all organizations.
 *
 * @property finder The repository used to find all organizations.
 */
@Service
class AllOrganizationFinder(private val finder: OrganizationFinderRepository) {

    /**
     * Finds all organizations.
     * @param userId The unique identifier of the user.
     *
     * @throws Exception If an error occurs while finding all organizations.
     * @return The [OrganizationResponses] containing all organizations.
     */
    suspend fun findAll(userId: String): OrganizationResponses {
        log.debug("Finding all organizations for user with id: $userId")
        val all = finder.findAll(
            UserId(userId),
        )
        return OrganizationResponses.from(all)
    }

    companion object {
        private val log = LoggerFactory.getLogger(AllOrganizationFinder::class.java)
    }
}
