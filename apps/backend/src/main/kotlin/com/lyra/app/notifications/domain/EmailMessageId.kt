package com.lyra.app.notifications.domain

import com.lyra.common.domain.BaseId
import java.util.*

class EmailMessageId(private val id: UUID) : BaseId<UUID>(id) {
    constructor(id: String) : this(UUID.fromString(id))
}
