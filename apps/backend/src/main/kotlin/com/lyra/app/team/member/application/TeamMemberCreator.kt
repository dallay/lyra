package com.lyra.app.team.member.application

import com.lyra.app.team.member.domain.TeamMember
import com.lyra.app.team.member.domain.TeamMemberRepository
import com.lyra.app.team.member.domain.event.TeamMemberAddedEvent
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import org.slf4j.LoggerFactory

/**
 * Service that creates a team member.
 * @property teamMemberRepository The repository for team members.
 * @property eventPublisher The event publisher for team members.
 */
@Service
class TeamMemberCreator(
    private val teamMemberRepository: TeamMemberRepository,
    eventPublisher: EventPublisher<TeamMemberAddedEvent>
) {
    private val eventPublisher = EventBroadcaster<TeamMemberAddedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    /**
     * Creates a team member using the [TeamMemberRepository] and publishes
     * a [TeamMemberAddedEvent] for the created team member.
     *
     * @param teamMember The [TeamMember] to be created.
     */
    suspend fun create(teamMember: TeamMember) {
        log.debug("Creating team member with id: {}", teamMember.id)
        teamMemberRepository.create(teamMember)
        val domainEvents = teamMember.pullDomainEvents()
        domainEvents.forEach {
            eventPublisher.publish(it as TeamMemberAddedEvent)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(TeamMemberCreator::class.java)
    }
}
