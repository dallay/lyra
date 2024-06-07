package com.lyra.app.workspaces.application.find.all

import com.lyra.app.workspaces.application.WorkspaceResponses
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.query.QueryHandler
import org.slf4j.LoggerFactory

/**
 * This class represents a query to find all workspaces.
 * @property finder The [AllWorkspaceFinder] to find all workspaces.
 */
@Service
class AllWorkspaceQueryHandler(
    private val finder: AllWorkspaceFinder
) : QueryHandler<AllWorkspaceQuery, WorkspaceResponses> {
    /**
     * Handles a query
     *
     * @param query the query to handle
     * @return the response to the query handled
     */
    override suspend fun handle(query: AllWorkspaceQuery): WorkspaceResponses {
        log.debug("Finding all workspaces")
        return finder.findAll()
    }

    companion object {
        private val log = LoggerFactory.getLogger(AllWorkspaceQueryHandler::class.java)
    }
}
