package com.lyra.app.newsletter.application.search

import com.lyra.app.newsletter.application.SubscriberResponse
import com.lyra.app.newsletter.application.SubscribersResponse
import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.app.newsletter.domain.SubscriberRepository
import com.lyra.common.domain.Service
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.slf4j.LoggerFactory

@Service
class AllSubscriberSearcher(private val repository: SubscriberRepository) {
    suspend fun search(): SubscribersResponse {
        log.info("Searching all subscribers")
        val subscribers: Flow<Subscriber> = repository.searchAll()
        return SubscribersResponse(
            subscribers.map {
                SubscriberResponse(
                    it.id.toString(),
                    it.email.value,
                    it.name.fullName(),
                    it.status.name,
                )
            }.toList(),
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(AllSubscriberSearcher::class.java)
    }
}
