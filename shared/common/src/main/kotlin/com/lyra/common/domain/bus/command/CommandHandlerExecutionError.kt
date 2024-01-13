package com.lyra.common.domain.bus.command

class CommandHandlerExecutionError(cause: Throwable) : RuntimeException(cause)
