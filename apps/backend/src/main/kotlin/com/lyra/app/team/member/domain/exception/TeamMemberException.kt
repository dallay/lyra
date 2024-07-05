package com.lyra.app.team.member.domain.exception

import com.lyra.common.domain.error.BusinessRuleValidationException

/**
 * This class represents a specific exception related to workspace collaborator operations.
 * It extends the BusinessRuleValidationException class.
 *
 * @property message The detail message string of this throwable.
 * @property cause The cause of this throwable.
 */
data class TeamMemberException(
    override val message: String,
    override val cause: Throwable? = null
) : BusinessRuleValidationException(message, cause)
