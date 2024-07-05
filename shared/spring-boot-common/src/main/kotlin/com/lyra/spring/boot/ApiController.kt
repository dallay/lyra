package com.lyra.spring.boot

import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.command.Command
import com.lyra.common.domain.bus.command.CommandHandlerExecutionError
import com.lyra.common.domain.bus.query.Query
import com.lyra.common.domain.bus.query.QueryHandlerExecutionError
import com.lyra.common.domain.bus.query.Response
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder

@SecurityRequirement(name = "Keycloak")
abstract class ApiController(
    private val mediator: Mediator
) {

    @Throws(CommandHandlerExecutionError::class)
    protected suspend fun dispatch(command: Command) = mediator.send(command)

    @Throws(QueryHandlerExecutionError::class)
    protected suspend fun <TResponse : Response>ask(query: Query<TResponse>): Response = mediator.send(query)

    protected suspend fun authentication(): Authentication? {
        val authentication = ReactiveSecurityContextHolder.getContext()
            .map { it.authentication }
            .awaitSingleOrNull()
        return authentication
    }
}
