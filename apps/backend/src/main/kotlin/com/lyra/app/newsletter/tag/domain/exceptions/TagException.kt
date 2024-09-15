package com.lyra.app.newsletter.tag.domain.exceptions

import com.lyra.common.domain.error.BusinessRuleValidationException

/**
 * Exception class for handling tag-related business rule validation errors.
 *
 * @property message The detail message string of the exception.
 * @property cause The cause of the exception, which can be null.
 * @created 15/9/24
 */
data class TagException(
    override val message: String,
    override val cause: Throwable? = null
) : BusinessRuleValidationException(message, cause)
