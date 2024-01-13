package com.lyra.common.domain.bus.notification

import com.lyra.common.domain.bus.DependencyProvider

internal class NotificationProvider<H : NotificationHandler<*>>(
    private val dependencyProvider: DependencyProvider,
    private val type: Class<H>,
) {

    fun get(): H = dependencyProvider.getSingleInstanceOf(type)
}
