package com.lyra.app.organization.infrastructure.persistence

import com.lyra.app.organization.domain.Organization
import com.lyra.app.organization.domain.OrganizationFinderRepository
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.app.organization.domain.OrganizationRemoverRepository
import com.lyra.app.organization.domain.OrganizationRepository
import com.lyra.app.organization.domain.exception.OrganizationException
import com.lyra.app.organization.infrastructure.persistence.mapper.OrganizationMapper.toDomain
import com.lyra.app.organization.infrastructure.persistence.mapper.OrganizationMapper.toEntity
import com.lyra.app.organization.infrastructure.persistence.repository.OrganizationR2dbcRepository
import com.lyra.app.users.domain.UserId
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Repository

@Repository
class OrganizationStoreR2DbcRepository(
    private val organizationRepository: OrganizationR2dbcRepository
) : OrganizationRepository,
    OrganizationFinderRepository,
    OrganizationRemoverRepository {

    /**
     * Create a new organization.
     *
     * @param organization The organization to be created.
     */
    override suspend fun create(organization: Organization) {
        log.debug("Creating organization with id: {}", organization.id)
        try {
            organizationRepository.save(organization.toEntity())
        } catch (e: DuplicateKeyException) {
            log.error("Organization already exists in the database: ${organization.id.value}")
            throw OrganizationException("Error creating organization", e)
        }
    }

    /**
     * Updates an organization.
     *
     * @param organization The organization to be updated.
     */
    override suspend fun update(organization: Organization) {
        log.debug("Updating organization with id: {}", organization.id)
        try {
            organizationRepository.save(organization.toEntity())
        } catch (e: DuplicateKeyException) {
            log.error("Organization already exists in the database: {}", organization.id.value)
            throw OrganizationException("Error updating organization", e)
        } catch (e: org.springframework.dao.TransientDataAccessResourceException) {
            log.error("Error updating form with id: ${organization.id.value}")
            throw OrganizationException("Error updating form because it does not exist", e)
        }
    }

    /**
     * Find an organization by its unique identifier.
     *
     * @param id The unique identifier of the organization.
     * @return The organization with the given unique identifier.
     */
    override suspend fun findById(id: OrganizationId): Organization? {
        log.debug("Finding organization with id: {}", id)
        return organizationRepository.findById(id.value)?.toDomain()
    }

    /**
     * Find all organizations that belong to a user.
     * Should return an empty list if the user does not belong to any organization.
     * @param userId The unique identifier of the user.
     * @return The list of organizations that belong to the user.
     */
    override suspend fun findAll(userId: UserId): List<Organization> {
        log.debug("Finding all organizations for user with id: {}", userId)
        return organizationRepository.findOrganizationsByUserId(userId.value).map { it.toDomain() }
    }

    /**
     * Deletes an organization.
     *
     * @param id The organization id.
     */
    override suspend fun delete(id: OrganizationId) {
        log.debug("Deleting organization with id: {}", id)
        organizationRepository.deleteById(id.value)
    }

    companion object {
        private val log = LoggerFactory.getLogger(OrganizationStoreR2DbcRepository::class.java)
    }
}
