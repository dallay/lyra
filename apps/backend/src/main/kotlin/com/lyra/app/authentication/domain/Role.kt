package com.lyra.app.authentication.domain

/**
 * Represents the role of a user in the system.
 *
 * The Role class is an enum class that defines four roles: ADMIN, USER, ANONYMOUS, and UNKNOWN.
 *
 * Roles can be converted to their corresponding key representation using the `key()` method,
 * which returns the role name prefixed with "ROLE_".
 *
 * The companion object provides utility methods to convert a string representation of a role
 * to a Role object using the `from()` method. If the provided role is blank, it returns UNKNOWN.
 *
 * Example usage:
 * ```kotlin
 * val role = Role.ADMIN
 * val roleKey = role.key()     // returns "ROLE_ADMIN"
 * val retrievedRole = Role.from("ROLE_USER")   // returns Role.USER
 * ```
 *
 * @see Role.ADMIN for more information about the ADMIN role.
 * @see Role.USER for more information about the USER role.
 * @see Role.ANONYMOUS for more information about the ANONYMOUS role.
 * @see Role.UNKNOWN for more information about the UNKNOWN role.
 * @see Role.key for more information about the key() method.
 * @see Role.from for more information about the from() method.
 *
 */

enum class Role {
    ADMIN,
    USER,
    ANONYMOUS,
    UNKNOWN;

    /**
     * Returns the key associated with the current object.
     *
     * @return the key as a string.
     */
    fun key(): String = PREFIX + name

    companion object {
        private const val PREFIX = "ROLE_"
        private val ROLES: Map<String, Role> = buildRoles()
        private fun buildRoles(): Map<String, Role> = entries.associateBy { it.key() }

        /**
         * Returns a [Role] object based on the provided role string. If the provided role string is blank,
         * it returns [Role.UNKNOWN].
         *
         * @param role the role string to convert into a [Role] object
         * @return the corresponding [Role] object based on the provided role string
         */
        fun from(role: String): Role {
            if (role.isBlank()) {
                return UNKNOWN
            }
            return ROLES[role] ?: UNKNOWN
        }
    }
}
