package com.lyra.app.team.member.application.remove

import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 * Command handler for removing a team member from a team.
 * @property teamMemberRemover The service that removes a team member.
 */
class RemoveTeamMemberCommandHandler(
    private val teamMemberRemover: TeamMemberRemover
) : CommandHandler<RemoveTeamMemberCommand> {
    /**
     * Handles the removal of a team member.
     * @param command The [RemoveTeamMemberCommand] containing the information needed to remove a team member.
     */
    override suspend fun handle(command: RemoveTeamMemberCommand) {
        log.debug(
            "Removing team member with teamId: {} and userId: {}",
            command.teamId, command.userId,
        )
        teamMemberRemover.remove(command.teamId, command.userId)
    }

    companion object {
        private val log = LoggerFactory.getLogger(RemoveTeamMemberCommandHandler::class.java)
    }
}
