package com.lyra.app.workspaces.application.collaborator.remove

import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 * This service class is responsible for handling the removal of collaborators from workspaces.
 * It implements the CommandHandler interface with [RemoveWorkspaceCollaboratorCommand] as the command type.
 *
 * @property workspaceCollaboratorRemover An instance of [WorkspaceCollaboratorRemover] used to remove the collaborator.
 */
@Service
class RemoveWorkspaceCollaboratorCommandHandler(
    private val workspaceCollaboratorRemover: WorkspaceCollaboratorRemover
) :
    CommandHandler<RemoveWorkspaceCollaboratorCommand> {

    /**
     * Handles the removal of a collaborator from a workspace.
     * Logs the id of the workspace and the collaborator being removed and calls
     * the workspaceCollaboratorRemover's remove method.
     *
     * @param command The command object containing the id of the workspace and the collaborator to be removed.
     */
    override suspend fun handle(command: RemoveWorkspaceCollaboratorCommand) {
        log.debug("Removing collaborator with id: ${command.userId} from workspace with id: ${command.workspaceId}")
        workspaceCollaboratorRemover.remove(command.workspaceId, command.userId)
    }

    companion object {
        private val log = LoggerFactory.getLogger(RemoveWorkspaceCollaboratorCommandHandler::class.java)
    }
}
