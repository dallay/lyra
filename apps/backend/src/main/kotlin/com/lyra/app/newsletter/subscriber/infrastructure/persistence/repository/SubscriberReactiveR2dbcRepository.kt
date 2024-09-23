package com.lyra.app.newsletter.subscriber.infrastructure.persistence.repository

import com.lyra.app.newsletter.subscriber.domain.SubscriberStatus
import com.lyra.app.newsletter.subscriber.infrastructure.persistence.entity.CountByStatusEntity
import com.lyra.app.newsletter.subscriber.infrastructure.persistence.entity.CountByTagsEntity
import com.lyra.app.newsletter.subscriber.infrastructure.persistence.entity.SubscriberEntity
import com.lyra.spring.boot.repository.ReactiveSearchRepository
import java.util.UUID
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
interface SubscriberReactiveR2dbcRepository :
    CoroutineCrudRepository<SubscriberEntity, UUID>,
    ReactiveSearchRepository<SubscriberEntity> {
    suspend fun findAllByStatus(status: SubscriberStatus): List<SubscriberEntity>

    @Query(
        """
        SELECT s.status, COUNT(s.id)
        FROM subscribers s
        WHERE  organization_id = :organizationId
        GROUP BY s.status
    """,
    )
    suspend fun countByStatus(organizationId: UUID): List<CountByStatusEntity>
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
    suspend fun countByTag(organizationId: UUID): List<CountByTagsEntity>

    @Query(
        """
        SELECT *
        FROM subscribers
        WHERE  organization_id = :organizationId
        AND email IN (:emails)
        """,
    )
    suspend fun findAllByEmails(organizationId: UUID, emails: Set<String>): List<SubscriberEntity>
}
