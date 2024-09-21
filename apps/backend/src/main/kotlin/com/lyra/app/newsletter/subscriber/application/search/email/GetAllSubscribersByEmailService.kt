package com.lyra.app.newsletter.subscriber.application.search.email

import com.lyra.app.newsletter.subscriber.application.SubscriberResponse
import com.lyra.app.newsletter.subscriber.application.SubscribersResponse
import com.lyra.app.newsletter.subscriber.domain.SubscriberSearchRepository
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
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
     * @param emails The list of email addresses to search for.
     * @param organizationId The identifier of the organization the subscribers belong to.
     * @return A response containing the list of [SubscriberResponse] objects representing the subscribers found.
     */
    suspend fun searchAllByEmails(organizationId: String, emails: List<String>): SubscribersResponse {
        log.debug("Searching all subscribers by emails: {} for organization: {}", emails, organizationId)
        val organizationId = OrganizationId(organizationId)
        val response: Flow<SubscriberResponse> =
            repository.searchAllByEmails(organizationId, emails).map { SubscriberResponse.from(it) }
        return SubscribersResponse(response.toList())
    }

    companion object {
        private val log = LoggerFactory.getLogger(GetAllSubscribersByEmailService::class.java)
    }
}
