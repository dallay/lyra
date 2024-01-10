package com.lyra.common.domain.bus.command

import java.util.*

class CommandHandlerExecutionError(cause: Throwable) : RuntimeException(cause)

class CommandNotRegisteredError(command: Class<out Command>) : Exception(
    String.format(
        Locale.ROOT,
        "The command <%s> hasn't a command handler associated",
        command.toString(),
    ),
)
