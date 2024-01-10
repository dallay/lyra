package com.lyra.spring.boot

import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.notification.Notification
import com.lyra.common.domain.bus.notification.NotificationHandler
import kotlin.test.assertTrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

var notificationTestCounter = 0
var asyncNotificationTestCounter = 0

@SpringBootTest(
    classes = [
        LyraAutoConfiguration::class,
        MyFirstNotificationHandler::class,
    ],
)
class NotificationHandlerTest {

    init {
        notificationTestCounter = 0
        asyncNotificationTestCounter = 0
    }

    @Autowired
    lateinit var mediator: Mediator

    @Test
    fun `async notificationHandler should be fired`() = runBlocking {
        mediator.publish(MyNotification())

        assertTrue {
            asyncNotificationTestCounter == 1
        }
    }
}

class MyNotification : Notification

class MyFirstNotificationHandler : NotificationHandler<MyNotification> {
    override suspend fun handle(notification: MyNotification) {
        delay(500)
        asyncNotificationTestCounter++
    }
}
