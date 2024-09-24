package com.lyra.app.newsletter.tag.domain.exceptions

import com.lyra.common.domain.error.BusinessRuleValidationException

/**
 * Exception class for handling tag-related business rule validation errors.
 *
 * @property message The detail message string of the exception.
 * @property cause The cause of the exception, which can be null.
 * @constructor Creates a new instance of TagException.
 */
open class TagException(
    override val message: String,
    override val cause: Throwable? = null
) : BusinessRuleValidationException(message, cause)

/**
 * Exception class for handling cases where a tag is not found.
 *
 * @property message The detail message string of the exception.
 * @property cause The cause of the exception, which can be null.
 * @constructor Creates a new instance of TagNotFoundException.
 */
data class TagNotFoundException(
    override val message: String,
    override val cause: Throwable? = null
) : TagException(message, cause)
