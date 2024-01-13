package com.lyra.app.controllers

import com.lyra.common.domain.error.BusinessRuleValidationException
import java.net.URI
import java.time.Instant
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler

private const val ERROR_PAGE = "https://lyra.io/errors"

/**
 * This class provides a global exception handling mechanism for the application.
 *
 * It extends the [ResponseEntityExceptionHandler] class to handle exceptions and return appropriate responses.
 *
 * @created 4/8/23
 */
@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    /**
     * Handles the [UserAuthenticationException] by creating a ProblemDetail object with the appropriate status,
     * detail and properties.
     *
     * @param e The UserAuthenticationException that was thrown.
     * @return The ProblemDetail object representing the exception.
     */
//  @ResponseStatus(HttpStatus.UNAUTHORIZED)
//  @ExceptionHandler(UserAuthenticationException::class)
//  fun handleUserAuthenticationException(e: UserAuthenticationException): ProblemDetail {
//    val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.message)
//    problemDetail.title = "User authentication failed"
//    problemDetail.setType(URI.create("$ERROR_PAGE/user-authentication-failed"))
//    problemDetail.setProperty("errorCategory", "AUTHENTICATION")
//    problemDetail.setProperty("timestamp", Instant.now())
//    return problemDetail
//  }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        IllegalArgumentException::class,
        BusinessRuleValidationException::class,
//    UserRefreshTokenException::class
    )
    fun handleIllegalArgumentException(e: Exception): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.message ?: "Bad request")
        problemDetail.title = "Bad request"
        problemDetail.setType(URI.create("$ERROR_PAGE/bad-request"))
        problemDetail.setProperty("errorCategory", "BAD_REQUEST")
        problemDetail.setProperty("timestamp", Instant.now())
        return problemDetail
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            e.message ?: "Internal server error",
        )
        problemDetail.title = "Internal server error"
        problemDetail.setType(URI.create("$ERROR_PAGE/internal-server-error"))
        problemDetail.setProperty("errorCategory", "INTERNAL_SERVER_ERROR")
        problemDetail.setProperty("timestamp", Instant.now())
        return problemDetail
    }
}
