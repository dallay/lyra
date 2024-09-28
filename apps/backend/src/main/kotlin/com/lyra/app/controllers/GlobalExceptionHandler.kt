package com.lyra.app.controllers

import com.lyra.app.authentication.domain.UserAuthenticationException
import com.lyra.app.authentication.domain.UserRefreshTokenException
import com.lyra.app.authentication.domain.error.LogoutFailedException
import com.lyra.app.authentication.domain.error.MissingCookieException
import com.lyra.app.authentication.infrastructure.cookie.AuthCookieBuilder
import com.lyra.common.domain.error.BusinessRuleValidationException
import com.lyra.common.domain.error.EntityNotFoundException
import java.net.URI
import java.time.Instant
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler

private const val ERROR_PAGE = "https://lyra.com/errors"

private const val TIMESTAMP = "timestamp"

private const val ENTITY_NOT_FOUND = "Entity not found"

private const val ERROR_CATEGORY = "errorCategory"

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
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserAuthenticationException::class, UserRefreshTokenException::class)
    fun handleUserAuthenticationException(
        e: Exception,
        response: ServerHttpResponse
    ): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.message)
        problemDetail.title = "User authentication failed"
        problemDetail.setType(URI.create("$ERROR_PAGE/user-authentication-failed"))
        problemDetail.setProperty("errorCategory", "AUTHENTICATION")
        problemDetail.setProperty("timestamp", Instant.now())
        AuthCookieBuilder.clearCookies(response)
        return problemDetail
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(
        EntityNotFoundException::class,
    )
    fun handleEntityNotFound(e: Exception): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.message ?: ENTITY_NOT_FOUND)
        problemDetail.title = ENTITY_NOT_FOUND
        problemDetail.setType(URI.create("$ERROR_PAGE/entity-not-found"))
        problemDetail.setProperty(ERROR_CATEGORY, "NOT_FOUND")
        problemDetail.setProperty(TIMESTAMP, Instant.now())
        return problemDetail
    }

    /**
     * Handles IllegalArgumentExceptions by creating a ProblemDetail object with the appropriate status,
     * detail and properties.
     * @param e The IllegalArgumentException that was thrown.
     * @return The ProblemDetail object representing the exception.
     * @see ProblemDetail
     * @see HttpStatus
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        IllegalArgumentException::class,
        BusinessRuleValidationException::class,
        LogoutFailedException::class,
    )
    fun handleIllegalArgumentException(e: Exception): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.message ?: "Bad request")
        problemDetail.title = "Bad request"
        problemDetail.setType(URI.create("$ERROR_PAGE/bad-request"))
        problemDetail.setProperty(ERROR_CATEGORY, "BAD_REQUEST")
        problemDetail.setProperty(TIMESTAMP, Instant.now())
        return problemDetail
    }
    /**
     * Exception handler for missing cookies.
     *
     * @param e The MissingCookieException.
     * @param response The HTTP response where the error status will be set.
     */
    @ExceptionHandler(MissingCookieException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMissingCookieException(
        e: MissingCookieException,
        response: ServerHttpResponse
    ): ProblemDetail {
        response.statusCode = HttpStatus.BAD_REQUEST
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.message)
        problemDetail.title = "Missing cookie"
        problemDetail.setType(URI.create("$ERROR_PAGE/missing-cookie"))
        problemDetail.setProperty(ERROR_CATEGORY, "MISSING_COOKIE")
        problemDetail.setProperty(TIMESTAMP, Instant.now())
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
        problemDetail.setProperty(ERROR_CATEGORY, "INTERNAL_SERVER_ERROR")
        problemDetail.setProperty(TIMESTAMP, Instant.now())
        return problemDetail
    }
}
