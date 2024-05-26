package com.lyra.app.users.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * User created event is published when a new user is created.
 *
 * @created 2/7/23
 */
data class UserCreatedEvent(
    val userId: String,
    val email: String,
    val username: String,
    val firstname: String?,
    val lastname: String?
) : BaseDomainEvent()
