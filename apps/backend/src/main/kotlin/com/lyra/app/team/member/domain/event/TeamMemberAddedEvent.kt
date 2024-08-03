package com.lyra.app.team.member.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * Event to notify that a team member was added.
 * @property teamId The unique identifier of the team.
 * @property userId The unique identifier of the user.
 * @property role The role of the user in the team.
 * @property createdAt The date and time when the team member was added.
 */
data class TeamMemberAddedEvent(
    val teamId: String,
    val userId: String,
    val role: String,
    val createdAt: String
) : BaseDomainEvent()
