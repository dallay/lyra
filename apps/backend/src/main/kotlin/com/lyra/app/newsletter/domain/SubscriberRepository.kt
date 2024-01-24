package com.lyra.app.newsletter.domain

import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.pagination.OffsetPage
import com.lyra.common.domain.presentation.sort.Sort
import kotlinx.coroutines.flow.Flow

/**
 *
 * @created 6/1/24
 */
interface SubscriberRepository {
    suspend fun create(subscriber: Subscriber)
    suspend fun searchAll(
        criteria: Criteria? = null,
        limit: Int? = null,
        offset: Long? = null,
        sort: Sort? = null
    ): OffsetPage<Subscriber>
    suspend fun searchActive(): Flow<Subscriber>
}
