package com.lyra.app.newsletter.tag.domain

import com.lyra.app.organization.domain.OrganizationId

/**
 * Repository interface for managing Tag entities.
 */
interface TagRepository {
    /**
     * Creates a new tag in the repository.
     *
     * @param tag The Tag entity to be created.
     */
    suspend fun create(tag: Tag)

    /**
     * Updates a tag in the repository.
     *
     * @param tag The Tag entity to be updated.
     */
    suspend fun update(tag: Tag)

    /**
     * Deletes a tag from the repository.
     *
     * @param organizationId The ID of the organization to which the tag belongs.
     * @param tagId The ID of the Tag entity to be deleted.
     */
    suspend fun delete(organizationId: OrganizationId, tagId: TagId)
}
