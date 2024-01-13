package com.lyra.app.newsletter.infrastructure.persistence.repository

import com.lyra.app.newsletter.domain.SubscriberStatus
import com.lyra.app.newsletter.infrastructure.persistence.entity.SubscriberEntity
import java.util.UUID
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface SubscriberRegistratorR2dbcRepository : ReactiveCrudRepository<SubscriberEntity, UUID> {
    fun findAllByStatus(status: SubscriberStatus): Flux<SubscriberEntity>
}
