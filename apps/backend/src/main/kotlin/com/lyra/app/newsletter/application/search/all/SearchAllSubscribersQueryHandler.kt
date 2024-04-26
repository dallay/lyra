package com.lyra.app.newsletter.application.search.all

import com.lyra.app.newsletter.application.SubscriberResponse
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.query.QueryHandler
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import org.slf4j.LoggerFactory

/**
 * This class represents a query handler to search all subscribers.
 *
 * @property searcher The [SearchAllSubscriberSearcher] to search subscribers.
 */
@Service
class SearchAllSubscribersQueryHandler(
    private val searcher: SearchAllSubscriberSearcher,
) : QueryHandler<SearchAllSubscribersQuery, CursorPageResponse<SubscriberResponse>> {
    /**
     * Handles the given query.
     *
     * @param query The query to handle.
     * @return A CursorPageResponse containing the search results.
     */
    override suspend fun handle(query: SearchAllSubscribersQuery): CursorPageResponse<SubscriberResponse> {
        log.info("Searching all subscribers")
        return searcher.search(query.criteria, query.size, query.cursor, query.sort)
    }
    companion object {
        private val log = LoggerFactory.getLogger(SearchAllSubscribersQueryHandler::class.java)
    }
}
