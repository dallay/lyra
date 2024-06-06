package com.lyra.app.workspaces.application.delete

import com.lyra.app.workspaces.domain.WorkspaceId
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 * This service class is responsible for handling the deletion of workspaces.
 * It implements the CommandHandler interface with [DeleteWorkspaceCommand] as the command type.
 *
 * @property workspaceDestroyer An instance of [WorkspaceDestroyer] used to delete the workspace.
 */
@Service
class DeleteWorkspaceCommandHandler(
    private val workspaceDestroyer: WorkspaceDestroyer
) : CommandHandler<DeleteWorkspaceCommand> {

    /**
     * Handles the deletion of a workspace.
     * Logs the id of the workspace being deleted and calls the workspaceDestroyer's delete method.
     *
     * @param command The command object containing the id of the workspace to be deleted.
     */
    override suspend fun handle(command: DeleteWorkspaceCommand) {
        log.debug("Deleting workspace with id: ${command.id}")
        workspaceDestroyer.delete(WorkspaceId(command.id))
    }

    companion object {
        private val log = LoggerFactory.getLogger(DeleteWorkspaceCommandHandler::class.java)
    }
}
