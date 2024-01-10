package com.lyra.common.domain.bus.query

import java.util.*

class QueryHandlerExecutionError(cause: Throwable) : RuntimeException(cause)

class QueryNotRegisteredError(query: Class<out Query<Response>>) :
    Exception(String.format(Locale.ROOT, "The query <%s> hasn't a query handler associated", query.toString()))
