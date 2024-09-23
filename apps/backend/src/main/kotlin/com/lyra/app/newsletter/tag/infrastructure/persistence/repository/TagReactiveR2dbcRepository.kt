package com.lyra.app.newsletter.tag.infrastructure.persistence.repository

import com.lyra.app.newsletter.tag.infrastructure.persistence.entity.TagEntity
import com.lyra.app.newsletter.tag.infrastructure.persistence.entity.TagWithSubscribersEntity
import com.lyra.spring.boot.repository.ReactiveSearchRepository
import java.util.UUID
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

/**
 * Repository interface for performing CRUD operations and search on TagEntity objects.
 *
 * @created 15/9/24
 */
@Repository
interface TagReactiveR2dbcRepository :
    CoroutineCrudRepository<TagEntity, UUID>,
    ReactiveSearchRepository<TagEntity> {
    /**
     * Finds all tags by organization ID.
     *
     * @param organizationId The organization ID.
     * @return A list of TagEntity objects.
     */
    @Query(
        """
            SELECT t.id, t.name, t.color, t.organization_id,
                   COALESCE(array_agg(s.email) FILTER (WHERE s.email IS NOT NULL), '{}') AS subscribers,
                   t.created_at, t.updated_at
            FROM tags t
            LEFT JOIN subscriber_tags st ON t.id = st.tag_id
            LEFT JOIN subscribers s ON st.subscriber_id = s.id
            WHERE t.organization_id = :organizationId
            GROUP BY t.id, t.name, t.color, t.organization_id, t.created_at, t.updated_at
            ORDER BY t.created_at DESC
        """,
    )
    suspend fun findAllTagsByOrganizationId(organizationId: UUID): List<TagWithSubscribersEntity>

    @Query(
        """
            SELECT t.id, t.name, t.color, t.organization_id,
                   COALESCE(array_agg(s.email) FILTER (WHERE s.email IS NOT NULL), '{}') AS subscribers,
                   t.created_at, t.updated_at
            FROM tags t
            LEFT JOIN subscriber_tags st ON t.id = st.tag_id
            LEFT JOIN subscribers s ON st.subscriber_id = s.id
            WHERE t.organization_id = :organizationId AND t.id = :tagId
            GROUP BY t.id, t.name, t.color, t.organization_id, t.created_at, t.updated_at
            ORDER BY t.created_at DESC
        """,
    )
    suspend fun findByIdWithSubscribers(organizationId: UUID, tagId: UUID): TagWithSubscribersEntity?
}
