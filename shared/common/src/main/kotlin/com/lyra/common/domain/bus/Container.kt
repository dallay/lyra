package com.lyra.common.domain.bus

import com.lyra.common.domain.bus.command.Command
import com.lyra.common.domain.bus.command.CommandHandler
import com.lyra.common.domain.bus.command.CommandProvider
import com.lyra.common.domain.bus.command.CommandWithResult
import com.lyra.common.domain.bus.command.CommandWithResultHandler
import com.lyra.common.domain.bus.command.CommandWithResultProvider
import com.lyra.common.domain.bus.notification.Notification
import com.lyra.common.domain.bus.notification.NotificationHandler
import com.lyra.common.domain.bus.notification.NotificationProvider
import com.lyra.common.domain.bus.query.Query
import com.lyra.common.domain.bus.query.QueryHandler
import com.lyra.common.domain.bus.query.QueryProvider

@Suppress("UNCHECKED_CAST")
internal class Container(dependencyProvider: DependencyProvider) : Registrar() {
    val commandMap = HashMap<Class<*>, CommandProvider<CommandHandler<Command>>>()
    val queryMap = HashMap<Class<*>, QueryProvider<QueryHandler<*, *>>>()
    val notificationMap =
        HashMap<Class<*>, MutableList<NotificationProvider<NotificationHandler<*>>>>()
    val pipelineSet = HashSet<PipelineProvider<*>>()
    val commandWithResultMap = HashMap<Class<*>, CommandWithResultProvider<*>>()

    init {

        registerFor<QueryHandler<Query<*>, *>, Query<*>>(dependencyProvider) { key, value ->
            queryMap[key] = QueryProvider(dependencyProvider, value as Class<QueryHandler<*, *>>)
        }

        registerFor<CommandHandler<Command>, Command>(dependencyProvider) { key, value ->
            commandMap[key] = CommandProvider(dependencyProvider, value)
        }

        registerFor<CommandWithResultHandler<CommandWithResult<*>, *>, CommandWithResult<*>>(
            dependencyProvider,
        ) { key, value ->
            commandWithResultMap[key] = CommandWithResultProvider(
                dependencyProvider,
                value as Class<CommandWithResultHandler<*, *>>,
            )
        }

        registerFor<NotificationHandler<Notification>, Notification>(dependencyProvider) { key, value ->
            notificationMap.getOrPut(key) { mutableListOf() }
                .add(NotificationProvider(dependencyProvider, value as Class<NotificationHandler<*>>))
        }

        registerFor<PipelineBehavior>(dependencyProvider) { handler ->
            pipelineSet.add(PipelineProvider(dependencyProvider, handler))
        }
    }
}
