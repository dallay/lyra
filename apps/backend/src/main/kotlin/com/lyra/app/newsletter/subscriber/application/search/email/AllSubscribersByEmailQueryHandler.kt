package com.lyra.app.newsletter.subscriber.application.search.email

import com.lyra.app.newsletter.subscriber.application.SubscribersResponse
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.query.QueryHandler
import org.slf4j.LoggerFactory

/**
 * Query handler for retrieving all subscribers by their email addresses.
 *
 * @property searcher The service used to search for subscribers by email.
 * @constructor Creates an instance of AllSubscribersByEmailQueryHandler with the given searcher service.
 * @created 10/1/24
 */
@Service
class AllSubscribersByEmailQueryHandler(
    private val searcher: GetAllSubscribersByEmailService,
) : QueryHandler<AllSubscribersByEmailQuery, SubscribersResponse> {

    /**
     * Handles the query to search for all subscribers by their email addresses.
     *
     * @param query The query containing the list of email addresses to search for.
     * @return A list of SubscriberResponse objects representing the subscribers found.
     */
    override suspend fun handle(query: AllSubscribersByEmailQuery): SubscribersResponse {
        log.debug("Searching all subscribers by emails: {} for organization: {}", query.emails, query.organizationId)
        return searcher.searchAllByEmails(query.organizationId, query.emails)
    }

    companion object {
        private val log = LoggerFactory.getLogger(AllSubscribersByEmailQueryHandler::class.java)
    }
}
