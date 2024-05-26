package com.lyra.app.authentication.domain.error

/**
 * Represents an exception that occurs during the authentication process.
 *
 * This class is a sealed class, meaning it can only be extended within the same file.
 * It extends the RuntimeException class which is an unchecked exception.
 */
sealed class AuthenticationException : RuntimeException()

/**
 * An exception that is thrown when a user is not authenticated.
 * @see AuthenticationException for more information.
 */
class NotAuthenticatedUserException : AuthenticationException()

/**
 * An exception that is thrown when an unknown authentication scheme is used.
 * @see AuthenticationException for more information.
 */
class UnknownAuthenticationException : AuthenticationException()
