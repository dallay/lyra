package com.lyra.app.newsletter.subscriber.application.search.email

import com.lyra.app.newsletter.subscriber.application.SubscriberResponse
import com.lyra.app.newsletter.subscriber.application.SubscribersResponse
import com.lyra.app.newsletter.subscriber.domain.SubscriberSearchRepository
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.Service
import org.slf4j.LoggerFactory

/**
 * Service for retrieving all subscribers by their email addresses.
 *
 * @property repository The repository for searching subscribers.
 * @constructor Creates an instance of GetAllSubscribersByEmailService with the given repository.
 * @created 10/1/24
 */
@Service
class GetAllSubscribersByEmailService(private val repository: SubscriberSearchRepository) {

    /**
     * Searches for all subscribers by their email addresses.
     *
     * @param emails The set of email addresses to search for.
     * @param organizationId The identifier of the organization the subscribers belong to.
     * @return A response containing the list of [SubscriberResponse] objects representing the subscribers found.
     */
    suspend fun searchAllByEmails(organizationId: String, emails: Set<String>): SubscribersResponse {
        log.debug("Searching all subscribers by emails: {} for organization: {}", emails, organizationId)
        if (emails.isEmpty()) {
            return SubscribersResponse(emptyList())
        }
        val orgId = OrganizationId(organizationId)
        val response: List<SubscriberResponse> =
            repository.searchAllByEmails(orgId, emails).map { SubscriberResponse.from(it) }
        return SubscribersResponse(response)
    }

    companion object {
        private val log = LoggerFactory.getLogger(GetAllSubscribersByEmailService::class.java)
    }
}
