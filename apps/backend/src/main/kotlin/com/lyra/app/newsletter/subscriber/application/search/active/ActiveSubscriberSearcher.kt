package com.lyra.app.newsletter.subscriber.application.search.active

import com.lyra.app.newsletter.subscriber.application.SubscriberResponse
import com.lyra.app.newsletter.subscriber.application.SubscribersResponse
import com.lyra.app.newsletter.subscriber.domain.Subscriber
import com.lyra.app.newsletter.subscriber.domain.SubscriberSearchRepository
import com.lyra.common.domain.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.slf4j.LoggerFactory

/**
 * Service for searching active subscribers.
 *
 * @property repository The repository for searching subscribers.
 * @created 10/1/24
 */
@Service
class ActiveSubscriberSearcher(private val repository: SubscriberSearchRepository) {

    /**
     * Searches for active subscribers and returns a response containing the list of active subscribers.
     *
     * @return SubscribersResponse containing the list of active subscribers.
     */
    suspend fun search(): SubscribersResponse {
        log.info("Searching active subscribers")
        val subscribers: Flow<Subscriber> = repository.searchActive()
        return SubscribersResponse(
            subscribers.map {
                SubscriberResponse(
                    it.id.toString(),
                    it.email.value,
                    it.name.fullName(),
                    it.status.name,
                    it.attributes,
                    it.organizationId.toString(),
                    it.createdAt.toString(),
                    it.updatedAt?.toString(),
                )
            }.toList(),
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(ActiveSubscriberSearcher::class.java)
    }
}
