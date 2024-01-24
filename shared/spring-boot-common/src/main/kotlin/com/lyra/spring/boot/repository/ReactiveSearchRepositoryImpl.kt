package com.lyra.spring.boot.repository

import kotlin.reflect.KClass
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query.query
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2

class ReactiveSearchRepositoryImpl<T : Any>(
    private val r2dbcTemplate: R2dbcEntityTemplate
) : ReactiveSearchRepository<T> {
    override fun findAll(criteria: Criteria, domainType: KClass<T>): Flux<T> {
        return r2dbcTemplate.select(domainType.java)
            .matching(query(criteria))
            .all()
    }

    override fun findAll(
        criteria: Criteria,
        pageable: Pageable,
        domainType: KClass<T>
    ): Mono<Page<T>> {
        val list = r2dbcTemplate.select(domainType.java)
            .matching(query(criteria).with(pageable))
            .all()
            .collectList()
        val count = r2dbcTemplate.select(domainType.java)
            .matching(query(criteria))
            .count()
        return Mono.zip<List<T>, Long>(list, count)
            .map { tuple: Tuple2<List<T>, Long> ->
                PageImpl(
                    tuple.t1,
                    pageable,
                    tuple.t2,
                )
            }
    }
}
