package com.lyra.app.newsletter.infrastructure.persistence.repository

import com.lyra.app.newsletter.domain.SubscriberStatus
import com.lyra.app.newsletter.infrastructure.persistence.entity.CountByStatusEntity
import com.lyra.app.newsletter.infrastructure.persistence.entity.SubscriberEntity
import com.lyra.spring.boot.repository.ReactiveSearchRepository
import java.util.*
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscriberReactiveR2dbcRepository :
    CoroutineCrudRepository<SubscriberEntity, UUID>,
    ReactiveSearchRepository<SubscriberEntity> {
    fun findAllByStatus(status: SubscriberStatus): Flow<SubscriberEntity>

    @Query(
        """
        SELECT s.status, COUNT(s.id)
        FROM subscribers s
        GROUP BY s.status
    """,
    )
    fun countByStatus(): Flow<CountByStatusEntity>
}
