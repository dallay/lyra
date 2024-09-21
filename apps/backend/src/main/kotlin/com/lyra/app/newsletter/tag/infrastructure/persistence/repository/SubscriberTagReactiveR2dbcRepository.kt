package com.lyra.app.newsletter.tag.infrastructure.persistence.repository

import com.lyra.app.newsletter.tag.infrastructure.persistence.entity.SubscriberTagEntity
import com.lyra.app.newsletter.tag.infrastructure.persistence.entity.SubscriberTagEntityId
import com.lyra.spring.boot.repository.ReactiveSearchRepository
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * Repository interface for performing CRUD operations and search on TagEntity objects.
 *
 * @created 15/9/24
 */
@Repository
@Transactional(readOnly = true)
interface SubscriberTagReactiveR2dbcRepository :
    CoroutineCrudRepository<SubscriberTagEntity, SubscriberTagEntityId>,
    ReactiveSearchRepository<SubscriberTagEntity> {
    /**
     * Upserts a subscriber tag.
     * This is a workaround until the Spring R2DBC team provides native support for composite primary keys.
     * The issues where this topic is being discussed are:
     * [Support for Composite Keys using @Embeded and @Id](https://github.com/spring-projects/spring-data-relational/issues/574)
     * [Add support for composite Id's](https://github.com/spring-projects/spring-data-r2dbc/issues/158)
     *
     */
    @Transactional
    @Modifying
    @Query(
        """
            INSERT INTO subscriber_tags
            (subscriber_id, tag_id, created_at, updated_at)
            VALUES(:#{#entity.subscriberId}, :#{#entity.tagId}, :#{#entity.createdAt}, :#{#entity.updatedAt});
        """,
    )
    suspend fun upsert(entity: SubscriberTagEntity): Int
}
