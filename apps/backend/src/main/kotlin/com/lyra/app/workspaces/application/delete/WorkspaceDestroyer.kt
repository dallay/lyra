package com.lyra.app.workspaces.application.delete

import com.lyra.app.workspaces.domain.WorkspaceDestroyerRepository
import com.lyra.app.workspaces.domain.WorkspaceId
import com.lyra.app.workspaces.domain.event.WorkspaceDeletedEvent
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

/**
 * This service class is responsible for deleting workspaces.
 *
 * @property destroyer The repository that handles the deletion of workspaces.
 * @property eventPublisher The publisher that handles the broadcasting of workspace deletion events.
 */
@Service
class WorkspaceDestroyer(
    private val destroyer: WorkspaceDestroyerRepository,
    eventPublisher: EventPublisher<WorkspaceDeletedEvent>
) {
    private val eventPublisher = EventBroadcaster<WorkspaceDeletedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    /**
     * Deletes a workspace with the given id.
     *
     * @param id The id of the workspace to be deleted.
     */
    suspend fun delete(id: WorkspaceId) {
        log.debug("Deleting workspace with id: {}", id)
        destroyer.delete(id)
        eventPublisher.publish(WorkspaceDeletedEvent(id.value.toString()))
    }

    companion object {
        private val log = LoggerFactory.getLogger(WorkspaceDestroyer::class.java)
    }
}
