package com.lyra.app.authentication.infrastructure.http.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * Data class representing a login request.
 *
 * This class is used for encapsulating the username and password
 * required for authenticating a user's login.
 *
 * @property username The username provided by the user.
 * @property password The password provided by the user.
 * @created 31/7/23
 */
data class LoginRequest(
    @field:NotBlank(message = "Username cannot be blank")
    @field:Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
    val username: String,
    @field:NotBlank(message = "Password cannot be blank")
    @field:Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    val password: String
)
