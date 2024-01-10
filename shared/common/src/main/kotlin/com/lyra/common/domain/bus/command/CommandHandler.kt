package com.lyra.common.domain.bus.command

interface CommandHandler<T : Command> {
    suspend fun handle(command: T)
}
