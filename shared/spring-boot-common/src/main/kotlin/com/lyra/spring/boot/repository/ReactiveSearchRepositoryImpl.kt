package com.lyra.spring.boot.repository

import com.lyra.common.domain.presentation.pagination.Cursor
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.spring.boot.presentation.sort.toSpringSort
import kotlin.reflect.KClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query.query

class ReactiveSearchRepositoryImpl<T : Any>(
    private val r2dbcTemplate: R2dbcEntityTemplate
) : ReactiveSearchRepository<T> {
    /**
     * Fetches all entities that match the given criteria.
     *
     * @param criteria The criteria to match.
     * @param domainType The class of the entity.
     * @return A Flow of entities that match the criteria.
     */
    override suspend fun findAll(criteria: Criteria, domainType: KClass<T>): Flow<T> {
        return r2dbcTemplate.select(domainType.java)
            .matching(query(criteria))
            .all().asFlow()
    }

    /**
     * Fetches all entities that match the given criteria, with support for pagination.
     *
     * @param criteria The criteria to match.
     * @param pageable The pagination information.
     * @param domainType The class of the entity.
     * @return A Page of entities that match the criteria.
     */
    override suspend fun findAll(
        criteria: Criteria,
        pageable: Pageable,
        domainType: KClass<T>
    ): Page<T> = coroutineScope {
        val listDeferred = async { fetchEntities(criteria, pageable, domainType) }
        val countDeferred = async { countEntities(criteria, domainType) }

        val list = listDeferred.await()
        val count = countDeferred.await()
        println("\uD83D\uDFE2 list: $list")
        println("\uD83D\uDFE2 count: $count")
        PageImpl(list, pageable, count)
    }

    private suspend fun fetchEntities(
        criteria: Criteria,
        pageable: Pageable,
        domainType: KClass<T>
    ): List<T> = withContext(Dispatchers.IO) {
        r2dbcTemplate.select(domainType.java)
            .matching(query(criteria).with(pageable))
            .all()
            .collectList()
            .awaitSingle()
    }

    private suspend fun countEntities(
        criteria: Criteria,
        domainType: KClass<T>
    ): Long = withContext(Dispatchers.IO) {
        r2dbcTemplate.select(domainType.java)
            .matching(query(criteria))
            .count()
            .awaitSingle()
    }

    /**
     * Fetches all entities that match the given criteria, with support for pagination and a cursor.
     *
     * @param criteria The criteria to match.
     * @param size The number of entities to fetch. Default is 10.
     * @param domainType The class of the entity.
     * @param cursor The cursor to use for pagination.
     * @return A CursorPageResponse of entities that match the criteria.
     */
    override suspend fun findAllByCursor(
        criteria: Criteria,
        size: Int,
        domainType: KClass<T>,
        sort: Sort,
        cursor: Cursor
    ): CursorPageResponse<T> {
        val pageSize = size + 1
        val cursorCriteriaParsed = R2DBCCriteriaParser(domainType).parse(cursor.getCriteria())
        val criteriaSpringSort = cursor.getSort().toSpringSort()
        // Combine the criteria separately
        val combinedCriteria = Criteria.empty()
            .and(criteria)
            .and(cursorCriteriaParsed)
        val query = query(combinedCriteria)
            .with(PageRequest.of(0, pageSize, sort.and(criteriaSpringSort)))
        val list: List<T> =
            r2dbcTemplate.select(domainType.java).matching(query).all().collectList().awaitFirstOrNull()
                ?: emptyList()

//        return list.flatMap { entities ->
//            val content = entities.take(size)
//            if (content.isEmpty()) {
//                Mono.just(CursorPageResponse(emptyList(), null))
//            } else {
//                val hasNextPage = entities.size > size
//                val nextCursor = if (hasNextPage) cursor.serialize(content.last()) else null
//                Mono.just(CursorPageResponse(content, nextCursor))
//            }
//        }
        val content = list.take(size)
        val hasNextPage = list.size > size
        val nextCursor = if (hasNextPage) cursor.serialize(content.last()) else null
        return CursorPageResponse(content, nextCursor)
    }
}
