package com.lyra.app.team.application.delete

import com.lyra.app.team.domain.TeamId
import com.lyra.app.team.domain.TeamRemoverRepository
import com.lyra.app.team.domain.event.TeamRemovedEvent
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

/**
 * Service to delete a team. The team is identified by the teamId and the organizationId.
 *
 * @property teamRemoverRepository The repository to delete a team.
 * @property eventPublisher The publisher for workspace collaborator removed events.

 */
@Service
class TeamDeleter(
    private val teamRemoverRepository: TeamRemoverRepository,
    eventPublisher: EventPublisher<TeamRemovedEvent>
) {
    private val eventPublisher = EventBroadcaster<TeamRemovedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    /**
     * Deletes a team. The team is identified by the teamId and the organizationId.
     *
     * @param teamId The team id.
     */
    suspend fun delete(teamId: String) {
        log.debug("Delete a team with id: $teamId")
        teamRemoverRepository.delete(TeamId(teamId))
        eventPublisher.publish(TeamRemovedEvent(teamId))
    }

    companion object {
        private val log = LoggerFactory.getLogger(TeamDeleter::class.java)
    }
}
