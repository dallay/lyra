package com.lyra.app.team.domain.exception

import com.lyra.common.domain.error.BusinessRuleValidationException
import com.lyra.common.domain.error.EntityNotFoundException

data class TeamException(
    override val message: String,
    override val cause: Throwable? = null
) : BusinessRuleValidationException(message, cause)

data class TeamNotFoundException(
    override val message: String,
    override val cause: Throwable? = null
) : EntityNotFoundException(message, cause)
