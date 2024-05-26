package com.lyra.app.authentication.infrastructure

import com.lyra.app.authentication.domain.error.NotAuthenticatedUserException
import com.lyra.app.authentication.domain.error.UnknownAuthenticationException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

private const val DEFAULT_PRECEDENCE = 2000

/**
 * This class handles exceptions related to authentication.
 *
 * It provides advice for exceptions of type NotAuthenticatedUserException and UnknownAuthenticationException.
 * Orders the execution of advice methods according to precedence.
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - DEFAULT_PRECEDENCE)
internal class AuthenticationExceptionAdvice {
    /**
     * Handles the [NotAuthenticatedUserException] and returns a ProblemDetail object.
     *
     * @return A [ProblemDetail] object with the status code set to UNAUTHORIZED, title set to "not authenticated",
     * and the property [MESSAGE_KEY] set to "error.http.401".
     */
    @ExceptionHandler(NotAuthenticatedUserException::class)
    fun NotAuthenticatedUserException.handleNotAuthenticateUser(): ProblemDetail {
        val detail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED)
        detail.title = "not authenticated"
        detail.setProperty(MESSAGE_KEY, "error.http.401")
        return detail
    }

    /**
     * Handles [UnknownAuthenticationException] and returns a [ProblemDetail] object with appropriate details.
     *
     * @return The [ProblemDetail] object representing the unknown authentication error.
     */
    @ExceptionHandler(UnknownAuthenticationException::class)
    fun UnknownAuthenticationException.handleUnknownAuthentication(): ProblemDetail {
        val detail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        detail.title = "unknown authentication"
        detail.setProperty(MESSAGE_KEY, "error.http.500")
        return detail
    }

    companion object {
        private const val MESSAGE_KEY = "message"
    }
}
