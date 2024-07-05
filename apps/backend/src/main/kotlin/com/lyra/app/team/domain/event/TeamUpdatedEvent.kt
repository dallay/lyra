package com.lyra.app.team.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * Event to notify that a team was updated.
 * @property teamId The team id.
 * @property organizationId The organization id.
 * @property name The new name of the team.
 */
data class TeamUpdatedEvent(
    val teamId: String,
    val organizationId: String,
    val name: String
) : BaseDomainEvent()
