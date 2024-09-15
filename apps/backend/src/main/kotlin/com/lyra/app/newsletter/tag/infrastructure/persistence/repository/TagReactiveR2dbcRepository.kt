package com.lyra.app.newsletter.tag.infrastructure.persistence.repository

import com.lyra.app.newsletter.tag.infrastructure.persistence.entity.TagEntity
import com.lyra.spring.boot.repository.ReactiveSearchRepository
import java.util.UUID
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
    ReactiveSearchRepository<TagEntity>
