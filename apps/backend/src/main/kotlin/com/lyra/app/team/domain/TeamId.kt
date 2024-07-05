package com.lyra.app.team.domain

import com.lyra.common.domain.BaseId
import java.util.*

data class TeamId(private val id: UUID) : BaseId<UUID>(id) {
    constructor(id: String) : this(UUID.fromString(id))
    companion object {
        fun create() = TeamId(UUID.randomUUID())
    }
}
