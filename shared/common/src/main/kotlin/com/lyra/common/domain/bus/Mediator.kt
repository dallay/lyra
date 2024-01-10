package com.lyra.common.domain.bus

import com.lyra.common.domain.bus.command.Command
import com.lyra.common.domain.bus.command.CommandWithResult
import com.lyra.common.domain.bus.notification.Notification
import com.lyra.common.domain.bus.query.Query

interface Mediator {

    suspend fun <TQuery : Query<TResponse>, TResponse> send(query: TQuery): TResponse

    suspend fun <TCommand : Command> send(command: TCommand)

    suspend fun <TCommand : CommandWithResult<TResult>, TResult> send(command: TCommand): TResult

    /**
     * Publishes the given notification to appropriate notification handlers
     *
     * @since 1.0.9
     * @param T  any [Notification] subclass to publish
     */
    suspend fun <T : Notification> publish(notification: T)

    suspend fun <T : Notification> publish(notification: T, publishStrategy: PublishStrategy)
}
