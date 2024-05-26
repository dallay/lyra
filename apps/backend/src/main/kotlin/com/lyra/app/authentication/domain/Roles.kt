package com.lyra.app.authentication.domain

import java.util.stream.Stream

/**
 * Represents a collection of roles.
 *
 * @property roles The set of roles in the collection.
 * @constructor Creates a new instance of the [Roles] class.
 */
data class Roles(val roles: Set<Role>) {
    /**
     * Returns true if the collection is empty, false otherwise.
     *
     * @return true if the collection is empty, false otherwise.
     * @see [Set.isEmpty]
     * @see [Roles.roles]
     */
    fun hasRole(): Boolean = roles.isNotEmpty()

    /**
     * Returns true if the collection contains the provided role, false otherwise.
     *
     * @param role the role to check for
     * @return true if the collection contains the provided role, false otherwise.
     * @see [Set.contains]
     * @see [Roles.roles]
     */
    fun hasRole(role: Role): Boolean = roles.contains(role)

    /**
     * Returns true if the collection contains any of the provided roles, false otherwise.
     * @param roles the roles to check for
     * @return true if the collection contains any of the provided roles, false otherwise.
     * @see [Roles.hasRole]
     */
    fun hasAnyRole(vararg roles: Role): Boolean = roles.any { hasRole(it) }

    /**
     * Returns a stream of Role objects.
     *
     * @return a Stream of Role objects.
     */
    fun stream(): Stream<Role> = roles.stream()

    companion object {
        val EMPTY = Roles(setOf())
    }
}
