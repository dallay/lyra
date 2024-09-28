package com.lyra.app.newsletter.tag.infrastructure.persistence

import com.lyra.app.newsletter.tag.domain.SubscriberTag
import com.lyra.app.newsletter.tag.domain.SubscriberTagRepository
import com.lyra.app.newsletter.tag.infrastructure.persistence.entity.SubscriberTagEntityId
import com.lyra.app.newsletter.tag.infrastructure.persistence.mapper.SubscriberTagMapper.toEntity
import com.lyra.app.newsletter.tag.infrastructure.persistence.repository.SubscriberTagReactiveR2dbcRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

/**
 *
 * @created 20/9/24
 */
@Repository
class SubscriberTagR2dbcRepository(
    private val subscriberTagR2dbcRepository: SubscriberTagReactiveR2dbcRepository
) : SubscriberTagRepository {

    /**
     * Creates a new tag in the repository.
     *
     * @param tag The Tag entity to be created.
     */
    override suspend fun create(tag: SubscriberTag) {
        log.debug("Creating tag with id {}", tag.id.value)
        subscriberTagR2dbcRepository.upsert(tag.toEntity())
    }

    override suspend fun delete(tag: SubscriberTag) {
        val subscriberTagId = tag.id.value
        log.debug("Deleting tag with id {}", subscriberTagId)
        subscriberTagR2dbcRepository.deleteSubscriberTagById(
            SubscriberTagEntityId(
                subscriberTagId.first,
                subscriberTagId.second,
            ),
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(SubscriberTagR2dbcRepository::class.java)
    }
}
