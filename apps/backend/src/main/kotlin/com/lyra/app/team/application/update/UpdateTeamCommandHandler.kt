package com.lyra.app.team.application.update

import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 * This class is responsible for handling the update team command.
 * It uses the [TeamUpdater] service to perform the update operation.
 *
 * @property teamUpdater The service used to update the team.
 */
@Service
class UpdateTeamCommandHandler(
    private val teamUpdater: TeamUpdater
) : CommandHandler<UpdateTeamCommand> {

    /**
     * This method handles the update team command.
     * It logs the operation and delegates the update operation to the [TeamUpdater] service.
     *
     * @param command The [UpdateTeamCommand] that triggers the update operation.
     */
    override suspend fun handle(command: UpdateTeamCommand) {
        log.debug("Updating team with id: ${command.teamId}")
        teamUpdater.update(command.teamId, command.name)
    }

    companion object {
        private val log = LoggerFactory.getLogger(UpdateTeamCommandHandler::class.java)
    }
}
