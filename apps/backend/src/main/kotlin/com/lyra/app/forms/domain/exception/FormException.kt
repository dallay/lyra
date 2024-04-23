package com.lyra.app.forms.domain.exception

import com.lyra.common.domain.error.BusinessRuleValidationException

data class FormException(
    override val message: String,
    override val cause: Throwable? = null
) : BusinessRuleValidationException(message, cause)

data class FormNotFoundException(
    override val message: String,
    override val cause: Throwable? = null
) : BusinessRuleValidationException(message, cause)
