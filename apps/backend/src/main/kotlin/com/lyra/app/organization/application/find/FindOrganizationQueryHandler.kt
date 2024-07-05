package com.lyra.app.organization.application.find

import com.lyra.app.organization.application.OrganizationResponse
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.app.organization.domain.exception.OrganizationNotFoundException
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.query.QueryHandler
import org.slf4j.LoggerFactory

/**
 * Service class responsible for handling organization find queries.
 *
 * @property finder [OrganizationFinder] The service for finding organizations.
 */
@Service
class FindOrganizationQueryHandler(
    private val finder: OrganizationFinder
) : QueryHandler<FindOrganizationQuery, OrganizationResponse> {

    /**
     * Handles an organization find query.
     * Logs the id of the organization being found, finds the organization using the finder service,
     * and returns a [OrganizationResponse].
     * If the organization is not found, a [OrganizationNotFoundException] is thrown.
     *
     * @param query The organization find query to handle.
     * @return The [OrganizationResponse] for the found organization.
     * @throws [OrganizationNotFoundException] If the organization is not found.
     */
    override suspend fun handle(query: FindOrganizationQuery): OrganizationResponse {
        log.info("Finding organization with id: ${query.id}")
        val organizationId = OrganizationId(query.id)
        val organization = finder.find(organizationId) ?: throw OrganizationNotFoundException("Workspace not found")
        return OrganizationResponse.from(organization)
    }

    companion object {
        private val log = LoggerFactory.getLogger(FindOrganizationQueryHandler::class.java)
    }
}
