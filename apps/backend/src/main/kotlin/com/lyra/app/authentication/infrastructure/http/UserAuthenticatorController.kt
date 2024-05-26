package com.lyra.app.authentication.infrastructure.http

import com.lyra.app.authentication.application.AuthenticateUserQueryHandler
import com.lyra.app.authentication.application.query.AuthenticateUserQuery
import com.lyra.app.authentication.domain.AccessToken
import com.lyra.app.authentication.infrastructure.http.request.LoginRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

/**
 * This class is a controller responsible for handling user authentication related requests.
 *
 * @property authenticateUserQueryHandler The query handler used for authenticating users.
 * @created 31/7/23
 */
@RestController
@RequestMapping("/api", produces = ["application/vnd.api.v1+json"])
class UserAuthenticatorController(private val authenticateUserQueryHandler: AuthenticateUserQueryHandler) {

    /**
     * Logs in a user with the provided username and password.
     *
     * @param loginRequest The login request containing the username and password.
     * @return A Mono of ResponseEntity containing the response object with the access token.
     */
    @Operation(summary = "Login endpoint")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @PostMapping(LOGIN_ROUTE)
    suspend fun login(@Validated @RequestBody loginRequest: LoginRequest): Mono<ResponseEntity<AccessToken>> {
        log.debug("Logging a user in")
        val (username, password) = loginRequest
        val authenticateUserQuery = AuthenticateUserQuery(username = username, password = password)
        val accessToken = authenticateUserQueryHandler.handle(authenticateUserQuery)
        return Mono.just(ResponseEntity.ok(accessToken))
    }
    companion object {
        const val LOGIN_ROUTE = "/login"
        private val log = org.slf4j.LoggerFactory.getLogger(UserAuthenticatorController::class.java)
    }
}
