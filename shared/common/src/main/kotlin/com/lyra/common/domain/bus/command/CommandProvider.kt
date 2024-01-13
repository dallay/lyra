package com.lyra.common.domain.bus.command

import com.lyra.common.domain.bus.DependencyProvider

/**
 * CommandProvider creates a command handler with enabled spring injection.
 *
 * @param <H> type of handler
</H> */
internal class CommandProvider<H : CommandHandler<*>>(
    private val dependencyProvider: DependencyProvider,
    private val type: Class<H>,
) {

    fun get(): H = dependencyProvider.getSingleInstanceOf(type)
}

/**
 * CommandProvider creates a command handler with enabled spring injection.
 *
 * @since 1.0.16
 * @param <H> type of handler
</H> */
internal class CommandWithResultProvider<H : CommandWithResultHandler<*, *>>(
    private val dependencyProvider: DependencyProvider,
    private val type: Class<H>,
) {

    fun get(): H = dependencyProvider.getSingleInstanceOf(type)
}
