package com.lyra.app.authentication.infrastructure.http

import com.lyra.app.authentication.application.RefreshTokenQueryHandler
import com.lyra.app.authentication.domain.AccessToken
import com.lyra.app.authentication.infrastructure.http.request.RefreshTokenRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

/**
 * Controller class to handle refreshing access tokens.
 */
@RestController
@RequestMapping("/api", produces = ["application/vnd.api.v1+json"])
class RefreshTokenController(private val refreshTokenQueryHandler: RefreshTokenQueryHandler) {

    /**
     * Refreshes the access token.
     * @param refreshTokenRequest The refresh token request.
     * @return A Mono of ResponseEntity containing the response object with the access token.
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
        @Validated @RequestBody refreshTokenRequest: RefreshTokenRequest
    ): Mono<ResponseEntity<AccessToken>> {
        log.debug("Refreshing tokens")
        val token = refreshTokenQueryHandler.handle(refreshTokenRequest.toQuery())
        return Mono.just(ResponseEntity.ok(token))
    }

    companion object {
        const val REFRESH_TOKEN_ROUTE = "/refresh-token"
        private val log = LoggerFactory.getLogger(RefreshTokenController::class.java)
    }
}
