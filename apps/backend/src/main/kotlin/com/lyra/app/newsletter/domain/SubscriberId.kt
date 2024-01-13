package com.lyra.app.newsletter.domain

import com.lyra.common.domain.BaseId
import java.util.*

class SubscriberId(private val id: UUID) : BaseId<UUID>(id) {
    constructor(id: String) : this(UUID.fromString(id))
}
