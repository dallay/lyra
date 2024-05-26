@file:Suppress("UNCHECKED_CAST")

package com.lyra.app.authentication.infrastructure

import java.util.stream.Collectors
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

/**
 * The Claims class provides utility methods for extracting
 * authorization information from claims.
 *
 * @since 1.0.0
 */
object Claims {
    const val CLAIMS_NAMESPACE = "https://www.lyra.com/"

    /**
     * Extracts a list of granted authorities from the claims provided.
     *
     * @param claims the claims containing user roles
     * @return a list of granted authorities based on the roles extracted from the claims
     */
    fun extractAuthorityFromClaims(
        claims: Map<String, Any>
    ): List<GrantedAuthority> = mapRolesToGrantedAuthorities(getRolesFromClaims(claims))

    /**
     * Retrieves roles from claims.
     *
     * @param claims The map containing claims.
     * @return The collection of roles obtained from claims.
     */
    private fun getRolesFromClaims(claims: Map<String, Any>): Collection<String> {
        return claims["groups"] as? Collection<String>
            ?: claims["roles"] as? Collection<String>
            ?: claims[CLAIMS_NAMESPACE + "roles"] as? Collection<String>
            ?: emptyList()
    }

    /**
     * Maps roles to a list of GrantedAuthority objects.
     *
     * @param roles The collection of roles to map.
     * @return A list of GrantedAuthority objects representing the mapped roles.
     */
    private fun mapRolesToGrantedAuthorities(roles: Collection<String>): List<GrantedAuthority> {
        return roles.stream().filter { role: String ->
            role.startsWith(
                "ROLE_",
            )
        }.map { role: String? ->
            SimpleGrantedAuthority(
                role,
            )
        }.collect(Collectors.toList())
    }
}
