package com.lyra.common.domain.bus

import com.lyra.common.domain.bus.notification.Notification
import com.lyra.common.domain.bus.notification.NotificationHandler
import com.lyra.common.domain.error.AggregateException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContinueOnExceptionPublishStrategy : PublishStrategy {

    override suspend fun <T : Notification> publish(
        notification: T,
        notificationHandlers: Collection<NotificationHandler<T>>,
        dispatcher: CoroutineDispatcher,
    ) {
        coroutineScope {
            withContext(dispatcher) {
                val exceptions = mutableListOf<Throwable>()
                @Suppress("TooGenericExceptionCaught")
                notificationHandlers.forEach {
                    try {
                        it.handle(notification)
                    } catch (e: Exception) {
                        exceptions.add(e)
                    }
                }
                if (exceptions.isNotEmpty()) {
                    throw AggregateException(exceptions)
                }
            }
        }
    }
}

class StopOnExceptionPublishStrategy : PublishStrategy {

    override suspend fun <T : Notification> publish(
        notification: T,
        notificationHandlers: Collection<NotificationHandler<T>>,
        dispatcher: CoroutineDispatcher,
    ) {
        coroutineScope {
            withContext(dispatcher) {
                notificationHandlers.forEach { it.handle(notification) }
            }
        }
    }
}

class ParallelNoWaitPublishStrategy : PublishStrategy {

    override suspend fun <T : Notification> publish(
        notification: T,
        notificationHandlers: Collection<NotificationHandler<T>>,
        dispatcher: CoroutineDispatcher,
    ) {
        coroutineScope {
            withContext(dispatcher) {
                notificationHandlers.forEach { launch { it.handle(notification) } }
            }
        }
    }
}

class ParallelWhenAllPublishStrategy : PublishStrategy {

    override suspend fun <T : Notification> publish(
        notification: T,
        notificationHandlers: Collection<NotificationHandler<T>>,
        dispatcher: CoroutineDispatcher,
    ) {
        coroutineScope {
            withContext(dispatcher) {
                val deferredResults = notificationHandlers.map { async { it.handle(notification) } }
                deferredResults.awaitAll()
            }
        }
    }
}
