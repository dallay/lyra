package com.lyra.app.authentication.application

import com.lyra.app.authentication.domain.Role
import com.lyra.app.authentication.domain.Roles
import com.lyra.app.authentication.domain.Username
import com.lyra.app.authentication.domain.error.NotAuthenticatedUserException
import com.lyra.app.authentication.domain.error.UnknownAuthenticationException
import java.util.*
import java.util.stream.Collectors
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken

/**
 *
 * The AuthenticatedUser class provides methods to retrieve information about the authenticated user.
 * It handles various authentication schemes.
 * This is an utility class to get authenticated user information
 *
 */
object AuthenticatedUser {
    private const val PREFERRED_USERNAME = "preferred_username"

    /**
     * Get the authenticated user username
     * @return The authenticated user username
     * @throws NotAuthenticatedUserException if the user is not authenticated
     * @throws UnknownAuthenticationException if the user uses an unknown authentication scheme
     */
    fun username(): Username = optionalUsername().orElseThrow(::NotAuthenticatedUserException)

    /**
     * Get the authenticated user username
     * @return The authenticated user username or empty if the user is not authenticated
     * @throws UnknownAuthenticationException if the user uses an unknown authentication scheme
     */
    fun optionalUsername(): Optional<Username> = authentication().map(::readPrincipal).flatMap(Username::of)

    /**
     * Read user principal from authentication
     *
     * @param authentication authentication to read the principal from
     *
     * @return The user principal
     *
     * @throws UnknownAuthenticationException if the authentication can't be read (unknown token type)
     */
    private fun readPrincipal(authentication: Authentication): String {
        return when {
            authentication.principal is UserDetails -> (authentication.principal as UserDetails).username
            authentication is JwtAuthenticationToken -> authentication.token.claims[PREFERRED_USERNAME]?.toString()
                ?: ""

            authentication.principal is DefaultOidcUser -> (authentication.principal as DefaultOidcUser)
                .attributes[PREFERRED_USERNAME]?.toString()
                ?: ""

            authentication.principal is String -> authentication.principal as String
            else -> throw UnknownAuthenticationException()
        }
    }

    /**
     * Get the authenticated user roles
     * @return The authenticated user roles or empty roles if the user is not authenticated
     */
    fun roles(): Roles = authentication().map(::toRoles).orElse(Roles.EMPTY)

    /**
     * Converts an authentication object to a Roles object.
     *
     * @param authentication the authentication object to be converted
     * @return the Roles object containing the authorities from the authentication object
     */
    private fun toRoles(authentication: Authentication): Roles = Roles(
        authentication.authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .map(Role::from)
            .collect(Collectors.toSet()),
    )

    /**
     * Get the authenticated user token attributes
     *
     * @return The authenticated user token attributes
     * @throws NotAuthenticatedUserException if the user is not authenticated
     * @throws UnknownAuthenticationException if the authentication scheme is unknown
     */
    fun attributes(): Map<String, Any> {
        return when (val token = authentication().orElseThrow(::NotAuthenticatedUserException)) {
            is OAuth2AuthenticationToken -> token.principal.attributes
            is JwtAuthenticationToken -> token.tokenAttributes
            else -> throw UnknownAuthenticationException()
        }
    }

    /**
     * Retrieves the current authentication from the security context.
     *
     * @return An optional containing the authentication object if it is present, otherwise an empty optional.
     */
    private fun authentication(): Optional<Authentication> =
        Optional.ofNullable(SecurityContextHolder.getContext().authentication)
}
