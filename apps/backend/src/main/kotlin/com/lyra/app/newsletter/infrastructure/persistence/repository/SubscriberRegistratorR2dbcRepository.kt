package com.lyra.app.newsletter.infrastructure.persistence.repository

import com.lyra.app.newsletter.infrastructure.persistence.entity.SubscriberEntity
import java.util.UUID
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscriberRegistratorR2dbcRepository : ReactiveCrudRepository<SubscriberEntity, UUID>
