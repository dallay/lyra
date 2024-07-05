package com.lyra.common.domain.bus.query

/**
 * This interface represents a response to a query. It is a marker interface.
 * It is used to represent the response of a query.
 *
 * @see Query
 */
interface Response

/**
 * Generic response to a query.
 *
 * @property T The type of the response.
 * @property response The response of the query.
 */
data class QueryResponse<T>(val response: T) : Response
