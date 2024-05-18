package com.lyra.app.newsletter.domain

import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.pagination.Cursor
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.common.domain.presentation.pagination.OffsetPageResponse
import com.lyra.common.domain.presentation.sort.Sort
import kotlinx.coroutines.flow.Flow

/**
 * This is an interface for a repository that handles subscriber data.
 * It has five functions, create, searchAllByOffset, searchAllByCursor,
 * and searchActive, which are all suspending functions.
 */
interface SubscriberRepository {
    /**
     * This function is used to create a new subscriber.
     * It is a suspending function, meaning it can be paused and resumed at a later time.
     * This makes it suitable for use in a coroutine context, where it can be used for non-blocking IO operations.
     *
     * @param subscriber The subscriber to create.
     */
    suspend fun create(subscriber: Subscriber)

    /**
     * This function is used to search all subscribers by offset.
     * It is a suspending function, meaning it can be paused and resumed at a later time.
     * This makes it suitable for use in a coroutine context, where it can be used for non-blocking IO operations.
     *
     * @param criteria The criteria to use for the search.
     * @param size The size of the page to return.
     * @param page The page number to return.
     * @param sort The sort order to use for the results.
     * @return An OffsetPageResponse containing the search results.
     */
    suspend fun searchAllByOffset(
        criteria: Criteria? = null,
        size: Int? = null,
        page: Int? = null,
        sort: Sort? = null
    ): OffsetPageResponse<Subscriber>

    /**
     * This function is used to search all subscribers by cursor.
     * It is a suspending function, meaning it can be paused and resumed at a later time.
     * This makes it suitable for use in a coroutine context, where it can be used for non-blocking IO operations.
     *
     * @param criteria The criteria to use for the search.
     * @param size The size of the page to return.
     * @param sort The sort order to use for the results.
     * @param cursor The cursor to use for the search.
     * @return A CursorPageResponse containing the search results.
     */
    suspend fun searchAllByCursor(
        criteria: Criteria? = null,
        size: Int? = null,
        sort: Sort? = null,
        cursor: Cursor? = null,
    ): CursorPageResponse<Subscriber>

    /**
     * This function is used to search all active subscribers.
     * It is a suspending function, meaning it can be paused and resumed at a later time.
     * This makes it suitable for use in a coroutine context, where it can be used for non-blocking IO operations.
     *
     * @return A Flow of active subscribers.
     */
    suspend fun searchActive(): Flow<Subscriber>
}
