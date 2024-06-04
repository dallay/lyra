package com.lyra.app.workspaces.application

import com.lyra.app.workspaces.domain.Workspace
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import java.util.*
import org.slf4j.LoggerFactory

/**
 * [CreateWorkspaceCommandHandler] is a class responsible for handling the creation of workspaces.
 * It implements the [CommandHandler] interface with [CreateWorkspaceCommand] as the command type.
 *
 * @property workspaceCreator The [WorkspaceCreator] used to create workspaces.
 */
@Service
class CreateWorkspaceCommandHandler(
    private val workspaceCreator: WorkspaceCreator
) : CommandHandler<CreateWorkspaceCommand> {

    /**
     * Handles the creation of a workspace.
     * It logs the creation process, creates a new workspace using the [WorkspaceCreator],
     * and then creates the workspace.
     *
     * @param command The [CreateWorkspaceCommand] containing the information needed to create a workspace.
     */
    override suspend fun handle(command: CreateWorkspaceCommand) {
        log.debug("Creating workspace with name: ${command.name}")
        val workspace = Workspace.create(
            id = UUID.fromString(command.id),
            name = command.name,
            userId = UUID.fromString(command.userId),
        )
        workspaceCreator.create(workspace)
    }

    companion object {
        private val log = LoggerFactory.getLogger(CreateWorkspaceCommandHandler::class.java)
    }
}
