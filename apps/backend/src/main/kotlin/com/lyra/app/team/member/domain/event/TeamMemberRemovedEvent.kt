package com.lyra.app.team.member.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

data class TeamMemberRemovedEvent(val teamId: String, val userId: String) : BaseDomainEvent()
