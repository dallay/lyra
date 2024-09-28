package com.lyra.app.newsletter.subscriber.application.count.bytags

import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.query.QueryHandler
import org.slf4j.LoggerFactory

/**
 * Query handler for counting subscribers by their tag.
 *
 * This class handles the [CountByTagsQuery] by using the [SubscriberCountByTags] service
 * to retrieve the count of subscribers by their tag.
 *
 * @property counter The service used to count subscribers by their tag.
 * @created 8/9/24
 */
@Service
class CountByTagsQueryHandler(private val counter: SubscriberCountByTags) :
    QueryHandler<CountByTagsQuery, SubscriberCountByTagsResponse> {

    /**
     * Handles the [CountByTagsQuery].
     *
     * This method processes the query to count subscribers by their tag and returns
     * the result as a [SubscriberCountByTagsResponse].
     *
     * @param query The query to handle.
     * @return [SubscriberCountByTagsResponse] The response containing the count of subscribers by tag.
     */
    override suspend fun handle(query: CountByTagsQuery): SubscriberCountByTagsResponse {
        log.debug("Counting subscribers by tag for organization {}", query.organizationId)
        return SubscriberCountByTagsResponse(counter.count(query.organizationId))
    }

    companion object {
        private val log = LoggerFactory.getLogger(CountByTagsQueryHandler::class.java)
    }
}
