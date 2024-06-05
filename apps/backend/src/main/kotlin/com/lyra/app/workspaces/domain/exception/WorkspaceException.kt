package com.lyra.app.workspaces.domain.exception

import com.lyra.common.domain.error.BusinessRuleValidationException
import com.lyra.common.domain.error.EntityNotFoundException

/**
 * This class represents a specific exception related to workspace operations.
 * It extends the BusinessRuleValidationException class.
 *
 * @property message The detail message string of this throwable.
 * @property cause The cause of this throwable.
 */
data class WorkspaceException(
    override val message: String,
    override val cause: Throwable? = null
) : BusinessRuleValidationException(message, cause)

/**
 * This class represents a specific exception related to workspace collaborator operations.
 * It extends the BusinessRuleValidationException class.
 *
 * @property message The detail message string of this throwable.
 * @property cause The cause of this throwable.
 */
data class WorkspaceCollaboratorException(
    override val message: String,
    override val cause: Throwable? = null
) : BusinessRuleValidationException(message, cause)

/**
 * This class represents a specific exception that is thrown when a workspace is not found.
 * It extends the EntityNotFoundException class.
 *
 * @property message The detail message string of this throwable.
 * @property cause The cause of this throwable.
 */
data class WorkspaceNotFoundException(
    override val message: String,
    override val cause: Throwable? = null
) : EntityNotFoundException(message, cause)
