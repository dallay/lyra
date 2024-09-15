package com.lyra.app.newsletter.application.search.active

import com.lyra.app.newsletter.application.SubscriberResponse
import com.lyra.app.newsletter.application.SubscribersResponse
import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.app.newsletter.domain.SubscriberRepository
import com.lyra.common.domain.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.slf4j.LoggerFactory

/**
 *
 * @created 10/1/24
 */
@Service
class ActiveSubscriberSearcher(private val repository: SubscriberRepository) {

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
