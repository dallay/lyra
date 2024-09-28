package com.lyra.app.newsletter.subscriber.domain.exceptions

import com.lyra.common.domain.error.BusinessRuleValidationException

/**
 * Exception class for handling subscriber-related business rule validation errors.
 *
 * @property message The detail message string of the exception.
 * @property cause The cause of the exception, which can be null.
 * @created 21/4/24
 */
data class SubscriberException(
    override val message: String,
    override val cause: Throwable? = null
) : BusinessRuleValidationException(message, cause)
