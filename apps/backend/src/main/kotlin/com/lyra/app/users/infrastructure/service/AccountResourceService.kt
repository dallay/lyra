package com.lyra.app.users.infrastructure.service

import com.lyra.app.authentication.infrastructure.Claims
import com.lyra.app.users.application.response.UserResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * The [AccountResourceService] class is responsible for retrieving user account information.
 * @created 21/8/23
 */
@Service
class AccountResourceService {
    /**
     * Retrieves the user account information based on the provided authentication token.
     *
     * @param authToken The authentication token. Must be an instance of AbstractAuthenticationToken.
     * @return A Mono containing the user account information as a UserResponse object.
     *         The UserResponse object contains the username, email, firstname, lastname, and authorities of the user.
     * @throws IllegalArgumentException if the authentication token is not an instance of OAuth2AuthenticationToken
     * or JwtAuthenticationToken.
     */
    fun getAccount(authToken: AbstractAuthenticationToken): Mono<UserResponse> {
        log.debug("Getting user account information")
        val attributes: Map<String, Any> = when (authToken) {
            is OAuth2AuthenticationToken -> authToken.principal.attributes

            is JwtAuthenticationToken -> authToken.tokenAttributes

            else -> throw IllegalArgumentException("Authentication token is not OAuth2 or JWT")
        }

        val authorities = Claims.extractAuthorityFromClaims(attributes)
        return Mono.just(
            UserResponse(
                username = attributes["preferred_username"] as String,
                email = attributes["email"] as String,
                firstname = attributes["given_name"] as String,
                lastname = attributes["family_name"] as String,
                authorities = authorities.map { it.authority }.toSet(),
            ),
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(AccountResourceService::class.java)
    }
}
