package com.lyra.app.forms.domain.exception

import com.lyra.common.domain.error.BusinessRuleValidationException

/**
 *
 * @created 21/4/24
 */
data class FormException(
    override val message: String,
    override val cause: Throwable? = null
) : BusinessRuleValidationException(message, cause)
