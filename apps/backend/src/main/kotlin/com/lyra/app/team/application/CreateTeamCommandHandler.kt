package com.lyra.app.team.application

import com.lyra.app.team.domain.Team
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 * This class is responsible for handling the create team command.
 * It uses the [TeamCreator] service to create the team.
 *
 * @property teamCreator The service used to create the team.
 */
@Service
class CreateTeamCommandHandler(
    private val teamCreator: TeamCreator
) : CommandHandler<CreateTeamCommand> {

    /**
     * This method handles the create team command.
     * It logs the operation and delegates the create operation to the [TeamCreator] service.
     * @param command The [CreateTeamCommand] that triggers the create operation.
     */
    override suspend fun handle(command: CreateTeamCommand) {
        log.debug("Creating team with team id: {} and organization id: {}", command.teamId, command.organizationId)
        val team = Team.create(
            teamId = command.teamId,
            organizationId = command.organizationId,
            name = command.name,
        )
        teamCreator.create(team)
    }

    companion object {
        private val log = LoggerFactory.getLogger(CreateTeamCommandHandler::class.java)
    }
}
