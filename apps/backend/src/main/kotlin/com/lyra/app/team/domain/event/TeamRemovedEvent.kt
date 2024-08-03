package com.lyra.app.team.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * Represents an event that indicates that a team has been removed.
 * @property teamId The unique identifier of the team.
 */
data class TeamRemovedEvent(
    val teamId: String
) : BaseDomainEvent()
