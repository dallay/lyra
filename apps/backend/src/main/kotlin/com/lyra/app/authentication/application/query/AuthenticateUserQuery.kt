package com.lyra.app.authentication.application.query

import com.lyra.app.authentication.domain.AccessToken
import com.lyra.common.domain.bus.query.Query
import java.util.*

/**
 *
 * @created 31/7/23
 */
data class AuthenticateUserQuery(
    val id: UUID = UUID.randomUUID(),
    val username: String,
    val password: String
) : Query<AccessToken>
