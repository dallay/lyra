package com.lyra.app.organization.application.find.all

import com.lyra.app.organization.application.OrganizationResponses
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.query.QueryHandler
import org.slf4j.LoggerFactory

/**
 * This class represents a query to find all organizations.
 * @property finder The [AllOrganizationFinder] to find all organizations.
 */
@Service
class AllOrganizationsQueryHandler(
    private val finder: AllOrganizationFinder
) : QueryHandler<AllOrganizationQuery, OrganizationResponses> {
    /**
     * Handles a query
     *
     * @param query the query to handle
     * @return the response to the query handled
     */
    override suspend fun handle(query: AllOrganizationQuery): OrganizationResponses {
        log.debug("Handling query: {}", query)
        return finder.findAll(query.userId)
    }

    companion object {
        private val log = LoggerFactory.getLogger(AllOrganizationsQueryHandler::class.java)
    }
}
