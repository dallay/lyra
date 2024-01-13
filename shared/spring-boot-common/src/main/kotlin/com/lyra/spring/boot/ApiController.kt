package com.lyra.spring.boot

import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.command.Command
import com.lyra.common.domain.bus.command.CommandHandlerExecutionError
import com.lyra.common.domain.bus.query.Query
import com.lyra.common.domain.bus.query.QueryHandlerExecutionError
import com.lyra.common.domain.bus.query.Response

abstract class ApiController(
    private val mediator: Mediator
) {

    @Throws(CommandHandlerExecutionError::class)
    protected suspend fun dispatch(command: Command) = mediator.send(command)

    @Throws(QueryHandlerExecutionError::class)
    protected suspend fun ask(query: Query<Response>): Response = mediator.send(query)
}
