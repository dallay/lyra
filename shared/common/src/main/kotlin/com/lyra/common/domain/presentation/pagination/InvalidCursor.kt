package com.lyra.common.domain.presentation.pagination

import com.lyra.common.domain.error.BusinessRuleValidationException

/**
 *  An exception representing an invalid cursor. This exception is thrown when a cursor is invalid.
 * @created 13/2/24
 */
data class InvalidCursor(override val message: String, override val cause: Throwable? = null) :
    BusinessRuleValidationException(message, cause)
