package com.lyra.app.newsletter.subscriber.domain

import com.lyra.common.domain.BaseId
import java.util.*

class SubscriptionListId(private val id: UUID) : BaseId<UUID>(id) {
    constructor(id: String) : this(UUID.fromString(id))
}
