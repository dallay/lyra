package com.lyra.app.team.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * Event that represents a team being added to an organization.
 * @property teamId The team id.
 * @property organizationId The organization id.
 * @property name The team name.
 */
data class TeamAddedEvent(
    val teamId: String,
    val organizationId: String,
    val name: String
) : BaseDomainEvent()
