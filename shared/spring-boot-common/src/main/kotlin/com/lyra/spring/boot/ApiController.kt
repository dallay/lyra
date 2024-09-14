package com.lyra.spring.boot

import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.command.Command
import com.lyra.common.domain.bus.command.CommandHandlerExecutionError
import com.lyra.common.domain.bus.query.Query
import com.lyra.common.domain.bus.query.QueryHandlerExecutionError
import com.lyra.common.domain.bus.query.Response
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.apache.commons.text.StringEscapeUtils
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder

/**
 * Abstract base class for API controllers.
 * Provides common functionality for handling commands, queries, and authentication.
 *
 * @property mediator The mediator used for sending commands and queries.
 */
@SecurityRequirement(name = "Keycloak")
abstract class ApiController(
    private val mediator: Mediator
) {

    /**
     * Dispatches a command using the mediator.
     *
     * @param command The command to be dispatched.
     * @throws CommandHandlerExecutionError if an error occurs while handling the command.
     */
    @Throws(CommandHandlerExecutionError::class)
    protected suspend fun dispatch(command: Command) = mediator.send(command)

    /**
     * Sends a query using the mediator and returns the response.
     *
     * @param TResponse The type of the response.
     * @param query The query to be sent.
     * @return The response from the query.
     * @throws QueryHandlerExecutionError if an error occurs while handling the query.
     */
    @Throws(QueryHandlerExecutionError::class)
    protected suspend fun <TResponse : Response> ask(query: Query<TResponse>): TResponse = mediator.send(query)

    /**
     * Retrieves the current authentication information.
     *
     * @return The current authentication, or null if not authenticated.
     */
    protected suspend fun authentication(): Authentication? {
        val authentication = ReactiveSecurityContextHolder.getContext()
            .map { it.authentication }
            .awaitSingleOrNull()
        return authentication
    }

    /**
     * Sanitizes a path variable to prevent injection attacks.
     *
     * @param pathVariable The path variable to sanitize.
     * @return The sanitized path variable.
     */
    protected fun sanitizePathVariable(pathVariable: String): String = StringEscapeUtils.escapeJava(pathVariable)

    /**
     * Sanitizes and joins multiple path variables into a single string.
     *
     * @param pathVariables The path variables to sanitize and join.
     * @return A JSON string containing the sanitized and joined path variables.
     */
    protected fun sanitizeAndJoinPathVariables(vararg pathVariables: String): String {
        val sanitizedVariables = pathVariables.map { sanitizePathVariable(it) }
        return sanitizedVariables.joinToString(" | ", prefix = "{", postfix = "}")
    }
}
