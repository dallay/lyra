package com.lyra.app.newsletter.subscriber.infrastructure.persistence.repository

import com.lyra.app.newsletter.subscriber.domain.SubscriberStatus
import com.lyra.app.newsletter.subscriber.infrastructure.persistence.entity.CountByStatusEntity
import com.lyra.app.newsletter.subscriber.infrastructure.persistence.entity.CountByTagsEntity
import com.lyra.app.newsletter.subscriber.infrastructure.persistence.entity.SubscriberEntity
import com.lyra.spring.boot.repository.ReactiveSearchRepository
import java.util.*
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
interface SubscriberReactiveR2dbcRepository :
    CoroutineCrudRepository<SubscriberEntity, UUID>,
    ReactiveSearchRepository<SubscriberEntity> {
    fun findAllByStatus(status: SubscriberStatus): Flow<SubscriberEntity>

    @Query(
        """
        SELECT s.status, COUNT(s.id)
        FROM subscribers s
        WHERE  organization_id = :organizationId
        GROUP BY s.status
    """,
    )
    fun countByStatus(organizationId: UUID): Flow<CountByStatusEntity>
    @Query(
        """
            SELECT tag, COUNT(*)
            FROM (
                SELECT json_array_elements_text(attributes->'tags') AS tag
                FROM subscribers
                 WHERE  organization_id = :organizationId
            ) AS tags
            GROUP BY tag;
        """,
    )
    fun countByTag(organizationId: UUID): Flow<CountByTagsEntity>

    @Query(
        """
        SELECT *
        FROM subscribers
        WHERE  organization_id = :organizationId
        AND email IN (:emails)
        """,
    )
    fun findAllByEmails(organizationId: UUID, emails: List<String>): Flow<SubscriberEntity>
}
