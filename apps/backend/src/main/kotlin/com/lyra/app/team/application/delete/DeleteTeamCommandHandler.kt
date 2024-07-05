package com.lyra.app.team.application.delete

import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 * This class is responsible for handling the delete team command.
 * It uses the [TeamDeleter] service to perform the delete operation.
 *
 * @property teamDeleter The service used to delete the team.
 */
@Service
class DeleteTeamCommandHandler(
    private val teamDeleter: TeamDeleter
) :
    CommandHandler<DeleteTeamCommand> {

    /**
     * This method handles the delete team command.
     * It logs the operation and delegates the delete operation to the [TeamDeleter] service.
     *
     * @param command The [DeleteTeamCommand] that triggers the delete operation.
     */
    override suspend fun handle(command: DeleteTeamCommand) {
        log.debug("Removing team with id: ${command.teamId}")
        teamDeleter.delete(command.teamId)
    }

    companion object {
        private val log = LoggerFactory.getLogger(DeleteTeamCommandHandler::class.java)
    }
}
