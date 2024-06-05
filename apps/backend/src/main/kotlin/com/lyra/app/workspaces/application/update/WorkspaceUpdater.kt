package com.lyra.app.workspaces.application.update

import com.lyra.app.workspaces.domain.WorkspaceFinderRepository
import com.lyra.app.workspaces.domain.WorkspaceId
import com.lyra.app.workspaces.domain.WorkspaceRepository
import com.lyra.app.workspaces.domain.event.WorkspaceUpdatedEvent
import com.lyra.app.workspaces.domain.exception.WorkspaceNotFoundException
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

/**
 * Service class responsible for updating workspaces.
 *
 * @property workspaceRepository The repository for workspace data.
 * @property workspaceFinderRepository The repository for finding workspaces.
 * @property eventPublisher The publisher for workspace update events.
 */
@Service
class WorkspaceUpdater(
    private val workspaceRepository: WorkspaceRepository,
    private val workspaceFinderRepository: WorkspaceFinderRepository,
    eventPublisher: EventPublisher<WorkspaceUpdatedEvent>
) {
    private val eventPublisher = EventBroadcaster<WorkspaceUpdatedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    /**
     * Updates a workspace with the given id and name.
     * Throws a WorkspaceNotFoundException if the workspace is not found.
     *
     * @param id The id of the workspace to update.
     * @param name The new name of the workspace.
     */
    suspend fun update(id: String, name: String) {
        log.info("Updating workspace with id: $id")
        val workspaceId = WorkspaceId(id)
        val workspace = workspaceFinderRepository.findById(workspaceId)
            ?: throw WorkspaceNotFoundException("Workspace not found")
        workspace.update(name)
        workspaceRepository.update(workspace)
        val domainEvents = workspace.pullDomainEvents()
        domainEvents.forEach {
            eventPublisher.publish(it as WorkspaceUpdatedEvent)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(WorkspaceUpdater::class.java)
    }
}
