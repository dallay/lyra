package com.lyra.app.workspaces.application.collaborator.remove

import com.lyra.app.users.domain.UserId
import com.lyra.app.workspaces.domain.WorkspaceCollaboratorRemoverRepository
import com.lyra.app.workspaces.domain.WorkspaceId
import com.lyra.app.workspaces.domain.event.WorkspaceCollaboratorRemovedEvent
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

/**
 * This service class is responsible for removing collaborators from workspaces.
 *
 * @property workspaceCollaboratorRemoverRepository The repository that handles
 * the removal of collaborators from workspaces.
 * @property eventPublisher The publisher that handles the broadcasting of workspace collaborator removal events.
 */
@Service
class WorkspaceCollaboratorRemover(
    private val workspaceCollaboratorRemoverRepository: WorkspaceCollaboratorRemoverRepository,
    eventPublisher: EventPublisher<WorkspaceCollaboratorRemovedEvent>
) {
    private val eventPublisher = EventBroadcaster<WorkspaceCollaboratorRemovedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    /**
     * Removes a collaborator from a workspace.
     *
     * @param workspaceId The ID of the workspace from which the collaborator is to be removed.
     * @param userId The ID of the user to be removed as a collaborator.
     */
    suspend fun remove(workspaceId: String, userId: String) {
        log.debug("Removing collaborator with id: $userId from workspace with id: $workspaceId")
        workspaceCollaboratorRemoverRepository.removeCollaborator(WorkspaceId(workspaceId), UserId(userId))
        eventPublisher.publish(WorkspaceCollaboratorRemovedEvent(workspaceId, userId))
    }

    companion object {
        private val log = LoggerFactory.getLogger(WorkspaceCollaboratorRemover::class.java)
    }
}
