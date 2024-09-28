package com.lyra.app.newsletter.subscriber.application.count.bystatus

import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.query.QueryHandler
import org.slf4j.LoggerFactory

/**
 * Query handler for counting subscribers by their status.
 *
 * This class handles the [CountByStatusQuery] by using the [SubscriberCountByStatus] service
 * to retrieve the count of subscribers by their status.
 *
 * @property counter The service used to count subscribers by their status.
 * @created 8/9/24
 */
@Service
class CountByStatusQueryHandler(private val counter: SubscriberCountByStatus) :
    QueryHandler<CountByStatusQuery, SubscriberCountByStatusResponse> {

    /**
     * Handles the [CountByStatusQuery].
     *
     * This method processes the query to count subscribers by their status and returns
     * the result as a [SubscriberCountByStatusResponse].
     *
     * @param query The query to handle.
     * @return [SubscriberCountByStatusResponse] The response containing the count of subscribers by status.
     */
    override suspend fun handle(query: CountByStatusQuery): SubscriberCountByStatusResponse {
        log.debug("Counting subscribers by status for organization {}", query.organizationId)
        return SubscriberCountByStatusResponse(counter.count(query.organizationId))
    }

    companion object {
        private val log = LoggerFactory.getLogger(CountByStatusQueryHandler::class.java)
    }
}
