package com.lyra.app.workspaces.application.find.all

import com.lyra.app.workspaces.application.WorkspaceResponses
import com.lyra.app.workspaces.domain.WorkspaceFinderRepository
import com.lyra.common.domain.Service
import org.slf4j.LoggerFactory

/**
 * This service is responsible for finding all workspaces.
 *
 * @property finder The repository used to find all workspaces.
 */
@Service
class AllWorkspaceFinder(private val finder: WorkspaceFinderRepository) {

    /**
     * This function is used to find all workspaces.
     *
     * It logs the action and then uses the finder repository to find all workspaces.
     * It then maps the result to a list of [WorkspaceResponses] objects.
     *
     * @return A list of WorkspaceResponse objects.
     */
    suspend fun findAll(): WorkspaceResponses {
        log.debug("Finding all workspaces")
        val all = finder.findAll()
        return WorkspaceResponses.from(all)
    }

    companion object {
        private val log = LoggerFactory.getLogger(AllWorkspaceFinder::class.java)
    }
}
