package com.lyra.app.newsletter.application.search.active

import com.lyra.app.newsletter.application.SubscribersResponse
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.query.QueryHandler
import org.slf4j.LoggerFactory

/**
 *
 * @created 9/1/24
 */
@Service
class SearchAllActiveSubscribersQueryHandler(
    private val searcher: ActiveSubscriberSearcher,
) : QueryHandler<SearchAllActiveSubscribersQuery, SubscribersResponse> {
    override suspend fun handle(query: SearchAllActiveSubscribersQuery): SubscribersResponse {
        log.info("Searching all active subscribers")
        return searcher.search()
    }
    companion object {
        private val log = LoggerFactory.getLogger(SearchAllActiveSubscribersQueryHandler::class.java)
    }
}
