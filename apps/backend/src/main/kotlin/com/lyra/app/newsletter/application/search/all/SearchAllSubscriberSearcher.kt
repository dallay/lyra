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

@Service
class SearchAllSubscriberSearcher(private val repository: SubscriberRepository) {
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
