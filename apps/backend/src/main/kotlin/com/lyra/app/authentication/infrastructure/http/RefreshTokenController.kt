package com.lyra.app.authentication.infrastructure.http

import com.lyra.app.authentication.application.RefreshTokenQueryHandler
import com.lyra.app.authentication.application.query.RefreshTokenQuery
import com.lyra.app.authentication.domain.AccessToken
import com.lyra.app.authentication.infrastructure.cookie.AuthCookieBuilder
import com.lyra.app.authentication.infrastructure.cookie.AuthCookieBuilder.buildCookies
import com.lyra.app.authentication.infrastructure.cookie.getCookie
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.*
import org.slf4j.LoggerFactory
import org.springframework.http.HttpCookie
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller class to handle refreshing access tokens.
 */
@RestController
@RequestMapping("/api", produces = ["application/vnd.api.v1+json"])
class RefreshTokenController(private val refreshTokenQueryHandler: RefreshTokenQueryHandler) {
    /**
     * Refreshes the access token.
     * @param request The ServerHttpRequest containing the refresh token.
     * @param response The ServerHttpResponse where the new access token will be stored.
     * @return The new access token.
     */
    @Operation(summary = "Refresh token endpoint")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @PostMapping(REFRESH_TOKEN_ROUTE)
    suspend fun refreshTokens(
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): ResponseEntity<AccessToken> {
        log.debug("Refreshing tokens")
        val refreshToken: HttpCookie = request.getCookie(AuthCookieBuilder.REFRESH_TOKEN)

        val accessToken = refreshTokenQueryHandler.handle(RefreshTokenQuery(UUID.randomUUID(), refreshToken.value))

        buildCookies(response, accessToken)

        return ResponseEntity.ok(accessToken)
    }

    companion object {
        const val REFRESH_TOKEN_ROUTE = "/refresh-token"
        private val log = LoggerFactory.getLogger(RefreshTokenController::class.java)
    }
}
