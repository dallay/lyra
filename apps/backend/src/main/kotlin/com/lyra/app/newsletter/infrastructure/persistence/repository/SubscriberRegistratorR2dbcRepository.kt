package com.lyra.app.newsletter.infrastructure.persistence.repository

import com.lyra.app.newsletter.domain.SubscriberStatus
import com.lyra.app.newsletter.infrastructure.persistence.entity.SubscriberEntity
import com.lyra.spring.boot.repository.ReactiveSearchRepository
import java.util.*
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface SubscriberRegistratorR2dbcRepository :
    R2dbcRepository<SubscriberEntity, UUID>,
    ReactiveSearchRepository<SubscriberEntity> {
    fun findAllByStatus(status: SubscriberStatus): Flux<SubscriberEntity>
}
