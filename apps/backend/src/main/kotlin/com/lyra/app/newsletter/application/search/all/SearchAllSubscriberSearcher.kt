package com.lyra.app.newsletter.application.search.all

import com.lyra.app.newsletter.application.SubscriberResponse
import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.app.newsletter.domain.SubscriberRepository
import com.lyra.common.domain.Service
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.pagination.OffsetPage
import com.lyra.common.domain.presentation.sort.Sort
import org.slf4j.LoggerFactory

@Service
class SearchAllSubscriberSearcher(private val repository: SubscriberRepository) {
    suspend fun search(criteria: Criteria?, limit: Int?, offset: Long?, sort: Sort?): OffsetPage<SubscriberResponse> {
        log.info("Searching all subscribers")
        val pages: OffsetPage<Subscriber> = repository.searchAll(criteria, limit, offset, sort)
        val subscribers: List<SubscriberResponse> = pages.data.map { SubscriberResponse.from(it) }.toList()
        return OffsetPage(
            data = subscribers,
            total = pages.total,
            page = pages.page,
            perPage = pages.perPage,
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(SearchAllSubscriberSearcher::class.java)
    }
}
