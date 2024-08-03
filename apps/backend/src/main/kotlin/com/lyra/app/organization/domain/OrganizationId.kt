package com.lyra.app.organization.domain

import com.lyra.common.domain.BaseId
import java.util.*

data class OrganizationId(private val id: UUID) : BaseId<UUID>(id) {
    constructor(id: String) : this(UUID.fromString(id))
    companion object {
        fun create() = OrganizationId(UUID.randomUUID())
    }
}
