package com.lyra.app.team.member.application.remove

import com.lyra.app.team.domain.TeamId
import com.lyra.app.team.member.domain.TeamMemberRemoverRepository
import com.lyra.app.team.member.domain.event.TeamMemberRemovedEvent
import com.lyra.app.users.domain.UserId
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

/**
 * Service that removes a team member.
 * @property teamMemberRemoverRepository The repository for team members.
 * @property eventPublisher The event publisher for team members.
 */
@Service
class TeamMemberRemover(
    private val teamMemberRemoverRepository: TeamMemberRemoverRepository,
    eventPublisher: EventPublisher<TeamMemberRemovedEvent>
) {
    private val eventPublisher = EventBroadcaster<TeamMemberRemovedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    /**
     * Removes a team member using the [TeamMemberRemoverRepository] and publishes
     * a [TeamMemberRemovedEvent] for the removed team member.
     * @param teamId The unique identifier of the team.
     * @param userId The unique identifier of the user.
     */
    suspend fun remove(teamId: String, userId: String) {
        log.debug("Removing team member with teamId: {} and userId: {}", teamId, userId)
        teamMemberRemoverRepository.remove(TeamId(teamId), UserId(userId))
    }

    companion object {
        private val log = LoggerFactory.getLogger(TeamMemberRemover::class.java)
    }
}
