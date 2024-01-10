package com.lyra.app.newsletter.application.search

import com.lyra.app.newsletter.application.SubscribersResponse
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.query.QueryHandler
import org.slf4j.LoggerFactory

/**
 *
 * @created 9/1/24
 */
@Service
class SearchAllSubscribersQueryHandler(
    private val searcher: AllSubscriberSearcher,
) : QueryHandler<SearchAllSubscribersQuery, SubscribersResponse> {
    override suspend fun handle(query: SearchAllSubscribersQuery): SubscribersResponse {
        log.info("Searching all subscribers")
        return searcher.search()
    }
    companion object {
        private val log = LoggerFactory.getLogger(SearchAllSubscribersQueryHandler::class.java)
    }
}
