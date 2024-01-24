package com.lyra.spring.boot.repository

import kotlin.reflect.KClass
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.relational.core.query.Criteria
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ReactiveSearchRepository<T : Any> {
    fun findAll(criteria: Criteria, domainType: KClass<T>): Flux<T>

    fun findAll(criteria: Criteria, pageable: Pageable, domainType: KClass<T>): Mono<Page<T>>
}
