package com.lyra.app.team.application

import com.lyra.app.team.domain.Team
import com.lyra.app.team.domain.TeamRepository
import com.lyra.app.team.domain.event.TeamAddedEvent
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

@Service
class TeamCreator(
    private val teamRepository: TeamRepository,
    eventPublisher: EventPublisher<TeamAddedEvent>
) {
    private val eventPublisher = EventBroadcaster<TeamAddedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }
    suspend fun create(team: Team) {
        log.debug("Creating team collaborator with id: {}", team.id)
        teamRepository.create(team)
        val domainEvents = team.pullDomainEvents()
        domainEvents.forEach {
            eventPublisher.publish(it as TeamAddedEvent)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(TeamCreator::class.java)
    }
}
