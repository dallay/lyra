package com.lyra.app.workspaces.application

import com.lyra.app.workspaces.domain.WorkspaceCollaborators
import com.lyra.app.workspaces.domain.WorkspaceRepository
import com.lyra.app.workspaces.domain.event.WorkspaceCollaboratorAddedEvent
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

/**
 * Service class responsible for creating workspace collaborators.
 * It uses a [WorkspaceRepository] to create a workspace collaborator and publishes
 * a [WorkspaceCollaboratorAddedEvent] for each created workspace collaborator.
 */
@Service
class WorkspaceCollaboratorCreator(
    /**
     * The [WorkspaceRepository] used to create workspace collaborators.
     */
    private val workspaceRepository: WorkspaceRepository,
    eventPublisher: EventPublisher<WorkspaceCollaboratorAddedEvent>
) {
    private val eventPublisher = EventBroadcaster<WorkspaceCollaboratorAddedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    /**
     * Creates a workspace collaborator using the [WorkspaceRepository] and publishes
     * a [WorkspaceCollaboratorAddedEvent] for the created workspace collaborator.
     *
     * @param workspace The [WorkspaceCollaborators] to be created.
     */
    suspend fun create(workspace: WorkspaceCollaborators) {
        log.debug("Creating workspace collaborator with id: {}", workspace.id)
        workspaceRepository.create(workspace)
        val domainEvents = workspace.pullDomainEvents()
        domainEvents.forEach {
            eventPublisher.publish(it as WorkspaceCollaboratorAddedEvent)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(WorkspaceCollaboratorCreator::class.java)
    }
}
