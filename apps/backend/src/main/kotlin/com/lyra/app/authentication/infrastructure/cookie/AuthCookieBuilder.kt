package com.lyra.app.authentication.infrastructure.cookie

import com.lyra.app.authentication.domain.AccessToken
import org.springframework.http.ResponseCookie
import org.springframework.http.server.reactive.ServerHttpResponse

/**
 * [AuthCookieBuilder] is a utility class that builds cookies for the authentication process.
 * It provides a method to build cookies for the access token.
 */
object AuthCookieBuilder {
    private const val ACCESS_TOKEN = "ACCESS_TOKEN"
    const val REFRESH_TOKEN = "REFRESH_TOKEN"
    private const val SESSION = "SESSION"

    /**
     * Builds cookies for the access token.
     * Adds the access token, refresh token, and session state cookies to the response.
     * @param response The ServerHttpResponse to which the cookies are added.
     * @param accessToken The access token containing the token, refresh token, and session state.
     * @return Unit
     */
    fun buildCookies(
        response: ServerHttpResponse,
        accessToken: AccessToken
    ) {
        // Add the cookie to the response
        response.addCookie(
            ResponseCookie.from(ACCESS_TOKEN, accessToken.token)
                .path("/")
                .maxAge(accessToken.expiresIn)
                .httpOnly(true)
                .secure(true)
                .build(),
        )
        response.addCookie(
            ResponseCookie.from(REFRESH_TOKEN, accessToken.refreshToken)
                .path("/")
                .maxAge(accessToken.expiresIn)
                .httpOnly(true)
                .secure(true)
                .build(),
        )
        if (accessToken.sessionState != null) {
            response.addCookie(
                ResponseCookie.from(SESSION, accessToken.sessionState)
                    .path("/")
                    .maxAge(accessToken.expiresIn)
                    .httpOnly(true)
                    .secure(true)
                    .build(),
            )
        }
    }
}
