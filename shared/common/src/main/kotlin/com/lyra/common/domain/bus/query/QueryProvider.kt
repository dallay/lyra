package com.lyra.common.domain.bus.query

import com.lyra.common.domain.bus.DependencyProvider

/**
 * QueryProvider creates a query handler with enabled spring injection.
 *
 * @param <H> type of handler
</H> */
internal class QueryProvider<H : QueryHandler<*, *>>(
    private val dependencyProvider: DependencyProvider,
    private val type: Class<H>,
) {

    fun get(): H = dependencyProvider.getSingleInstanceOf(type)
}
