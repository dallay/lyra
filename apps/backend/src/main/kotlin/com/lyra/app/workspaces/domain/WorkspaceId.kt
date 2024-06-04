package com.lyra.app.workspaces.domain

import com.lyra.common.domain.BaseId
import java.util.*

data class WorkspaceId(private val id: UUID) : BaseId<UUID>(id) {
    constructor(id: String) : this(UUID.fromString(id))
    companion object {
        fun create() = WorkspaceId(UUID.randomUUID())
    }
}
