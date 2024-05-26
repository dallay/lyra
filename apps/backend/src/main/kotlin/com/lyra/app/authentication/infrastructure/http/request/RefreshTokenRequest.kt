package com.lyra.app.authentication.infrastructure.http.request

import com.lyra.app.authentication.application.query.RefreshTokenQuery
import jakarta.validation.constraints.NotBlank

/**
 * Represents a refresh token request.
 *
 * @param refreshToken The refresh token to be used for refreshing the access token.
 */
data class RefreshTokenRequest(
    @field:NotBlank(message = "Refresh token cannot be blank")
    val refreshToken: String,
) {
    fun toQuery(): RefreshTokenQuery {
        return RefreshTokenQuery(
            refreshToken = refreshToken,
        )
    }
}
