package com.lyra.app.newsletter.application.search.all

import com.lyra.app.newsletter.application.SubscriberResponse
import com.lyra.app.newsletter.domain.SubscriberRepository
import com.lyra.common.domain.Service
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.common.domain.presentation.pagination.TimestampCursor
import com.lyra.common.domain.presentation.pagination.map
import com.lyra.common.domain.presentation.sort.Sort
import org.slf4j.LoggerFactory

/**
 * This class represents a service to search all subscribers.
 *
 * @property repository The [SubscriberRepository] to fetch subscribers from.
 */
@Service
class SearchAllSubscriberSearcher(private val repository: SubscriberRepository) {

    /**
     * Searches for subscribers based on the given criteria, size, cursor, and sort.
     *
     * @param criteria The criteria to filter the subscribers. It can be null.
     * @param size The size of the page to return. It can be null.
     * @param cursor The cursor to paginate through the subscribers. It can be null.
     * @param sort The sort order for the subscribers. It can be null.
     * @return A page of subscribers that match the given criteria, size, cursor, and sort.
     */
    suspend fun search(
        criteria: Criteria?,
        size: Int?,
        cursor: String?,
        sort: Sort?
    ): CursorPageResponse<SubscriberResponse> {
        log.debug(
            "Searching all subscribers with criteria: {}, size: {}, cursor: {}, sort: {}",
            criteria,
            size,
            cursor,
            sort,
        )
        val timestampCursor = cursor?.let { TimestampCursor.deserialize(it) }
        return repository.searchAllByCursor(criteria, size, sort, timestampCursor).map { pageResponse ->
            pageResponse.map { SubscriberResponse.from(it) }
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(SearchAllSubscriberSearcher::class.java)
    }
}
