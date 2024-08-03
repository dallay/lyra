package com.lyra.app.team.member.application

import com.lyra.app.team.member.domain.TeamMember
import com.lyra.app.team.member.domain.TeamMemberRole
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 * Command handler for adding a team member to a team.
 * @property teamMemberCreator The service that creates a team member.
 */
@Service
class AddTeamMemberCommandHandler(
    private val teamMemberCreator: TeamMemberCreator
) : CommandHandler<AddTeamMemberCommand> {

    /**
     * Handles the creation of a team collaborator.
     * @param command The [AddTeamMemberCommand] containing the information needed to create a team.
     */
    override suspend fun handle(command: AddTeamMemberCommand) {
        log.debug(
            "Creating team Collaborator with teamId: {} and userId: {}",
            command.teamId, command.userId,
        )
        val teamCollaborator = TeamMember.create(
            teamId = command.teamId,
            userId = command.userId,
            role = TeamMemberRole.valueOf(command.role),
        )
        teamMemberCreator.create(teamCollaborator)
    }

    companion object {
        private val log = LoggerFactory.getLogger(AddTeamMemberCommandHandler::class.java)
    }
}
