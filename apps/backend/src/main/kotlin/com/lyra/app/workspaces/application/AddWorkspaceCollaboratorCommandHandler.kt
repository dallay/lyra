package com.lyra.app.workspaces.application

import com.lyra.app.workspaces.domain.WorkspaceCollaborators
import com.lyra.app.workspaces.domain.WorkspaceRole
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 * [AddWorkspaceCollaboratorCommandHandler] is a class responsible for handling the creation
 * of workspaces collaborators.
 * It implements the [CommandHandler] interface with [AddWorkspaceCollaboratorCommand] as the command type.
 *
 * @property workspaceCollaboratorCreator The [WorkspaceCreator] used to create workspaces collaborators.
 */
@Service
class AddWorkspaceCollaboratorCommandHandler(
    private val workspaceCollaboratorCreator: WorkspaceCollaboratorCreator
) : CommandHandler<AddWorkspaceCollaboratorCommand> {

    /**
     * Handles the creation of a workspace collaborator.
     * @param command The [AddWorkspaceCollaboratorCommand] containing the information needed to create a workspace.
     */
    override suspend fun handle(command: AddWorkspaceCollaboratorCommand) {
        log.debug(
            "Creating workspace Collaborator with workspaceId: {} and userId: {}",
            command.workspaceId, command.userId,
        )
        val workspaceCollaborator = WorkspaceCollaborators.create(
            workspaceId = command.workspaceId,
            userId = command.userId,
            role = WorkspaceRole.valueOf(command.role),
        )
        workspaceCollaboratorCreator.create(workspaceCollaborator)
    }

    companion object {
        private val log = LoggerFactory.getLogger(AddWorkspaceCollaboratorCommandHandler::class.java)
    }
}
