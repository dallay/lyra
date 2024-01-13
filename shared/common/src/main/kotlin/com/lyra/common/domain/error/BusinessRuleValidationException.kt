package com.lyra.common.domain.error

import java.lang.Exception

/**
 * An abstract class representing a business rule validation exception.
 *
 * This class is used to represent exceptions that occur during the validation of business rules.
 * It extends the Throwable class and provides a message and an optional cause for the exception.
 *
 * @property message The detail message of the exception.
 * @property cause The cause of the exception, if any.
 */
abstract class BusinessRuleValidationException(
    override val message: String,
    override val cause: Throwable? = null
) : Exception(message, cause)
