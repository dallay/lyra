package com.lyra.app.authentication.domain

import com.lyra.app.authentication.domain.error.UsernameException
import com.lyra.common.domain.BaseValidateValueObject
import java.util.*

private const val MAX_LENGTH = 100
private const val MIN_LENGTH = 3

/**
 * Represents a username. A username is a string that identifies a user.
 *
 * @param username the username string
 * @constructor Creates a new instance of the [Username] class.
 * @throws UsernameException if the username is blank or its length is not between 3 and 100 characters.
 * @see [BaseValidateValueObject] for more information about the base class.
 * @see [UsernameException] for more information about the exception.
 * @see [Username.of] for more information about the factory method.
 */
data class Username(val username: String) : BaseValidateValueObject<String>(username) {

    /**
     * Validates the given username. A username is valid if it is not blank
     * and its length is between 3 and 100 characters.
     *
     * @param value the username to validate
     * @throws UsernameException if the username is blank or if it doesn't have the required length
     */
    override fun validate(value: String) {
        if (value.isBlank()) {
            throw UsernameException("Username cannot be blank")
        }
        if (value.length !in MIN_LENGTH..MAX_LENGTH) {
            throw UsernameException("Username must be between $MIN_LENGTH and $MAX_LENGTH characters")
        }
    }

    companion object {
        /**
         * Returns an Optional containing the Username object for the given username.
         * If the username is invalid and throws a UsernameException, it returns an empty Optional.
         *
         * @param username the string representation of the username
         * @return an Optional<Username> object representing the username,
         *         or an empty Optional if the username is invalid
         */
        fun of(username: String): Optional<Username> {
            return try {
                Optional.of(Username(username))
            } catch (_: UsernameException) {
                Optional.empty()
            }
        }
    }
}
