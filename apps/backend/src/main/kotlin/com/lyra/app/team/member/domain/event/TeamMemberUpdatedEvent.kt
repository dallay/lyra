package com.lyra.app.team.member.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * Event to notify that a team member was updated.
 * @property teamId The unique identifier of the team.
 * @property userId The unique identifier of the user.
 * @property role The role of the user in the team.
 */
data class TeamMemberUpdatedEvent(val teamId: String, val userId: String, val role: String) :
    BaseDomainEvent()
