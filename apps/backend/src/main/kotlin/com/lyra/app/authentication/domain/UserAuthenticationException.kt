package com.lyra.app.authentication.domain

import com.lyra.common.domain.error.BusinessRuleValidationException

/**
 * Exception thrown when the user is not valid
 * @author acosta
 * @created 29/6/23
 */

abstract class UserException(
    override val message: String,
    override val cause: Throwable? = null
) : BusinessRuleValidationException(message, cause)

/**
 * Exception thrown when there is an error during user authentication.
 *
 * @param message The detail message describing the exception.
 * @param cause The cause of the exception.
 */
class UserAuthenticationException(message: String, cause: Throwable? = null) : UserException(message, cause)

/**
 * Exception thrown when the user is not valid
 * @param message the user that is not valid
 * @param cause the cause of the exception
 */
class UserRefreshTokenException(message: String, cause: Throwable? = null) : UserException(message, cause)
