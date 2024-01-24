package com.lyra.spring.boot.presentation

import kotlin.reflect.KClass
import org.springframework.web.reactive.HandlerResult
import org.springframework.web.server.ServerWebExchange

interface Presenter<T : Any> {
    val type: KClass<T>

    suspend fun present(exchange: ServerWebExchange, result: HandlerResult)
}
