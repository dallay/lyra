package com.lyra.app.newsletter.application.search.all

import com.lyra.app.newsletter.application.SubscriberResponse
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.query.QueryHandler
import com.lyra.common.domain.presentation.pagination.OffsetPage
import org.slf4j.LoggerFactory

/**
 *
 * @created 9/1/24
 */
@Service
class SearchAllSubscribersQueryHandler(
    private val searcher: SearchAllSubscriberSearcher,
) : QueryHandler<SearchAllSubscribersQuery, OffsetPage<SubscriberResponse>> {
    override suspend fun handle(query: SearchAllSubscribersQuery): OffsetPage<SubscriberResponse> {
        log.info("Searching all subscribers")
        return searcher.search(query.criteria, query.size, query.page, query.sort)
    }
    companion object {
        private val log = LoggerFactory.getLogger(SearchAllSubscribersQueryHandler::class.java)
    }
}
