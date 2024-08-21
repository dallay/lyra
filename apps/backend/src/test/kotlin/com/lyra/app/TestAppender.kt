package com.lyra.app

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase

class TestAppender : AppenderBase<ILoggingEvent>() {
    val events = mutableListOf<ILoggingEvent>()

    override fun append(eventObject: ILoggingEvent) {
        events.add(eventObject)
    }
}
