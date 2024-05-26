package com.lyra.app.users.application.response

import java.io.Serializable

/**
 * Represents a response containing user information.
 *
 * @author acosta
 * @created 29/6/23
 *
 * @property username The username of the user.
 * @property email The email address of the user.
 * @property firstname The first name of the user, nullable.
 * @property lastname The last name of the user, nullable.
 */
data class UserResponse(
    val username: String,
    val email: String,
    val firstname: String?,
    val lastname: String?,
    val authorities: Set<String> = emptySet()
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}
