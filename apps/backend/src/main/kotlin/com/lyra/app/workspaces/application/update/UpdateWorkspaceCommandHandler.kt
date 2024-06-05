package com.lyra.app.workspaces.application.update

import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 * This class is responsible for handling the update workspace command.
 * It uses the WorkspaceUpdater service to perform the update operation.
 *
 * @property workspaceUpdater The service used to update the workspace.
 */
@Service
class UpdateWorkspaceCommandHandler(
    private val workspaceUpdater: WorkspaceUpdater
) : CommandHandler<UpdateWorkspaceCommand> {

    /**
     * This method handles the update workspace command.
     * It logs the operation and delegates the update operation to the WorkspaceUpdater service.
     *
     * @param command The [UpdateWorkspaceCommand] that triggers the update operation.
     */
    override suspend fun handle(command: UpdateWorkspaceCommand) {
        log.debug("Updating workspace with id: ${command.id}")
        workspaceUpdater.update(command.id, command.name)
    }

    companion object {
        private val log = LoggerFactory.getLogger(UpdateWorkspaceCommandHandler::class.java)
    }
}
