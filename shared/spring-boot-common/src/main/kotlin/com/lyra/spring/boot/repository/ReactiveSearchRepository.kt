package com.lyra.spring.boot.repository

import com.lyra.common.domain.presentation.pagination.Cursor
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.relational.core.query.Criteria

/**
 * ReactiveSearchRepository is an interface for performing reactive database operations.
 * It provides methods for fetching all entities that match a given criteria, with support for pagination.
 *
 * @param T The type of the entity.
 * @param CURSOR The type of the cursor.
 */
interface ReactiveSearchRepository<T : Any> {

    /**
     * Fetches all entities that match the given criteria.
     *
     * @param criteria The criteria to match.
     * @param domainType The class of the entity.
     * @return A Flux of entities that match the criteria.
     */
    suspend fun findAll(criteria: Criteria, domainType: KClass<T>): Flow<T>

    /**
     * Fetches all entities that match the given criteria, with support for pagination.
     *
     * @param criteria The criteria to match.
     * @param pageable The pagination information.
     * @param domainType The class of the entity.
     * @return A Mono of a Page of entities that match the criteria.
     */
    suspend fun findAll(criteria: Criteria, pageable: Pageable, domainType: KClass<T>): Page<T>

    /**
     * Fetches all entities that match the given criteria, with support for pagination and a cursor.
     *
     * @param criteria The criteria to match.
     * @param size The number of entities to fetch. Default is 10.
     * @param domainType The class of the entity.
     * @param cursor The cursor to use for pagination. Default is null.
     * @return A Mono of a [PageResponse] of entities that match the criteria.
     */
    suspend fun findAllByCursor(
        criteria: Criteria,
        size: Int = 10,
        domainType: KClass<T>,
        sort: Sort,
        cursor: Cursor,
    ): CursorPageResponse<T>
}
