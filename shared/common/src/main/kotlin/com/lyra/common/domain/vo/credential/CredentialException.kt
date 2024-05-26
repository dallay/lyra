package com.lyra.common.domain.vo.credential

import com.lyra.common.domain.error.BusinessRuleValidationException

class CredentialException(
    override val message: String,
    override val cause: Throwable? = null
) : BusinessRuleValidationException(message, cause)
