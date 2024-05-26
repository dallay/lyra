package com.lyra.app.authentication.domain

/**
 * Represents a refresh token used for authorization.
 *
 * @property value The value of the refresh token.
 */
data class RefreshToken(
    val value: String,
)
