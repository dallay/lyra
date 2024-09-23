package com.lyra.app.newsletter.tag.infrastructure.persistence

import com.lyra.app.newsletter.tag.domain.Tag
import com.lyra.app.newsletter.tag.domain.TagId
import com.lyra.app.newsletter.tag.domain.TagRepository
import com.lyra.app.newsletter.tag.domain.TagSearchRepository
import com.lyra.app.newsletter.tag.domain.exceptions.TagException
import com.lyra.app.newsletter.tag.infrastructure.persistence.mapper.TagMapper.toDomain
import com.lyra.app.newsletter.tag.infrastructure.persistence.mapper.TagMapper.toEntity
import com.lyra.app.newsletter.tag.infrastructure.persistence.repository.TagReactiveR2dbcRepository
import com.lyra.app.organization.domain.OrganizationId
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Repository

/**
 * Repository implementation for managing Tag entities using R2DBC.
 *
 * @property tagReactiveR2dbcRepository The reactive repository for TagEntity.
 * @created 15/9/24
 */
@Repository
class TagR2dbcRepository(private val tagReactiveR2dbcRepository: TagReactiveR2dbcRepository) :
    TagRepository, TagSearchRepository {
    /**
     * Creates a new tag in the repository.
     *
     * @param tag The Tag entity to be created.
     * @throws TagException if a tag with the same ID already exists.
     */
    override suspend fun create(tag: Tag) {
        log.debug("Creating tag with id {}", tag.id)
        try {
            tagReactiveR2dbcRepository.save(tag.toEntity())
        } catch (e: DuplicateKeyException) {
            log.error("Tag already exists in the database: ${tag.id.value}")
            throw TagException("Error creating tag", e)
        }
    }

    override suspend fun update(tag: Tag) {
        log.debug("Updating tag with id {}", tag.id)
        try {
            tagReactiveR2dbcRepository.save(tag.toEntity())
        } catch (e: org.springframework.dao.TransientDataAccessResourceException) {
            log.error("Error updating tag with id: ${tag.id.value}")
            throw TagException("Error updating tag", e)
        }
    }

    override suspend fun findAllTagsByOrganizationId(organizationId: OrganizationId): List<Tag> {
        log.debug("Searching all tags for organization {}", organizationId.value)
        return tagReactiveR2dbcRepository.findAllTagsByOrganizationId(organizationId.value)
            .map { it.toDomain() }
    }

    override suspend fun findById(organizationId: OrganizationId, id: TagId): Tag? {
        log.debug("Searching tag with id {} for organization {}", id.value, organizationId.value)
        return tagReactiveR2dbcRepository.findByIdWithSubscribers(
            organizationId.value, id.value,
        )?.toDomain()
    }

    companion object {
        private val log = LoggerFactory.getLogger(TagR2dbcRepository::class.java)
    }
}
