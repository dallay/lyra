package com.lyra.app.newsletter.subscriber.domain.exceptions

import com.lyra.common.domain.error.BusinessRuleValidationException

/**
 * Exception thrown when the name is not valid
 *
 * @created 2/7/23
 */
sealed class InvalidArgumentNameException(
    override val message: String,
    override val cause: Throwable? = null
) : BusinessRuleValidationException(message, cause)

/**
 * Exception thrown when the name is not valid
 * @param id the name that is not valid
 * @param cause the cause of the exception
 */
data class FirstNameNotValidException(val id: String, override val cause: Throwable? = null) :
    InvalidArgumentNameException("The first name <$id> is not valid", cause)

/**
 * Exception thrown when the name is not valid
 * @param id the name that is not valid
 * @param cause the cause of the exception
 */
data class LastNameNotValidException(val id: String, override val cause: Throwable? = null) :
    InvalidArgumentNameException("The last name <$id> is not valid", cause)
