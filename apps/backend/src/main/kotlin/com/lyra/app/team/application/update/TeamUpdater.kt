package com.lyra.app.team.application.update

import com.lyra.app.team.domain.TeamFinderRepository
import com.lyra.app.team.domain.TeamId
import com.lyra.app.team.domain.TeamRepository
import com.lyra.app.team.domain.event.TeamUpdatedEvent
import com.lyra.app.team.domain.exception.TeamNotFoundException
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

/**
 * Service class responsible for updating teams.
 *
 * @property teamRepository The repository for team data.
 * @property teamFinderRepository The repository for finding teams.
 * @property eventPublisher The publisher for team update events.
 */
@Service
class TeamUpdater(
    private val teamRepository: TeamRepository,
    private val teamFinderRepository: TeamFinderRepository,
    eventPublisher: EventPublisher<TeamUpdatedEvent>
) {
    private val eventPublisher = EventBroadcaster<TeamUpdatedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    /**
     * Updates a team with the given id and name.
     * @param id The id of the team to update.
     * @param name The new name of the team.
     * Throws a TeamNotFoundException if the team is not found.
     */
    suspend fun update(id: String, name: String) {
        log.debug("Updating team with id: $id")
        val teamId = TeamId(id)
        val team = teamFinderRepository.findById(teamId)
            ?: throw TeamNotFoundException("Team not found")
        team.updateName(name)
        teamRepository.update(team)
        val domainEvents = team.pullDomainEvents()
        domainEvents.forEach {
            eventPublisher.publish(it as TeamUpdatedEvent)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(TeamUpdater::class.java)
    }
}
