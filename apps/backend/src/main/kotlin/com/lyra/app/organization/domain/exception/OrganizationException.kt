package com.lyra.app.organization.domain.exception

import com.lyra.common.domain.error.BusinessRuleValidationException
import com.lyra.common.domain.error.EntityNotFoundException

/**
 * This class represents a specific exception related to organization operations.
 * It extends the BusinessRuleValidationException class.
 *
 * @property message The detail message string of this throwable.
 * @property cause The cause of this throwable.
 */
data class OrganizationException(
    override val message: String,
    override val cause: Throwable? = null
) : BusinessRuleValidationException(message, cause)

/**
 * This class represents a specific exception that is thrown when an organization is not found.
 * It extends the EntityNotFoundException class.
 *
 * @property message The detail message string of this throwable.
 * @property cause The cause of this throwable.
 */
data class OrganizationNotFoundException(
    override val message: String,
    override val cause: Throwable? = null
) : EntityNotFoundException(message, cause)
