package com.lyra.app.newsletter.domain.exceptions

import com.lyra.common.domain.error.BusinessRuleValidationException

/**
 *
 * @created 21/4/24
 */
data class SubscriberException(
    override val message: String,
    override val cause: Throwable? = null
) : BusinessRuleValidationException(message, cause)
