package com.lyra.spring.boot.repository

import com.lyra.common.domain.presentation.pagination.Cursor
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.spring.boot.presentation.sort.toSpringSort
import kotlin.reflect.KClass
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query.query
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2

class ReactiveSearchRepositoryImpl<T : Any>(
    private val r2dbcTemplate: R2dbcEntityTemplate
) : ReactiveSearchRepository<T> {
    /**
     * Fetches all entities that match the given criteria.
     *
     * @param criteria The criteria to match.
     * @param domainType The class of the entity.
     * @return A Flux of entities that match the criteria.
     */
    override fun findAll(criteria: Criteria, domainType: KClass<T>): Flux<T> {
        return r2dbcTemplate.select(domainType.java)
            .matching(query(criteria))
            .all()
    }

    /**
     * Fetches all entities that match the given criteria, with support for pagination.
     *
     * @param criteria The criteria to match.
     * @param pageable The pagination information.
     * @param domainType The class of the entity.
     * @return A Mono of a Page of entities that match the criteria.
     */
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

    /**
     * Fetches all entities that match the given criteria, with support for pagination and a cursor.
     *
     * @param criteria The criteria to match.
     * @param size The number of entities to fetch. Default is 10.
     * @param domainType The class of the entity.
     * @param cursor The cursor to use for pagination.
     * @return A Mono of a [CursorPageResponse] of entities that match the criteria.
     */
    override fun findAllByCursor(
        criteria: Criteria,
        size: Int,
        domainType: KClass<T>,
        sort: Sort,
        cursor: Cursor
    ): Mono<CursorPageResponse<T>> {
        val pageSize = size + 1
        val cursorCriteriaParsed = R2DBCCriteriaParser(domainType).parse(cursor.getCriteria())
        val criteriaSpringSort = cursor.getSort().toSpringSort()
        // Combine the criteria separately
        val combinedCriteria = Criteria.empty()
            .and(criteria)
            .and(cursorCriteriaParsed)
        val query = query(combinedCriteria)
            .with(PageRequest.of(0, pageSize, sort.and(criteriaSpringSort)))
        val list = r2dbcTemplate.select(domainType.java).matching(query).all().collectList()

        return list.flatMap { entities ->
            val content = entities.take(size)
            if (content.isEmpty()) {
                Mono.just(CursorPageResponse(emptyList(), null))
            } else {
                val hasNextPage = entities.size > size
                val nextCursor = if (hasNextPage) cursor.serialize(content.last()) else null
                Mono.just(CursorPageResponse(content, nextCursor))
            }
        }
    }
}
