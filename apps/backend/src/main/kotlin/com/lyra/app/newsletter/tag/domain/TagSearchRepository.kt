package com.lyra.app.newsletter.tag.domain

import com.lyra.app.organization.domain.OrganizationId

/**
 * Repository interface for searching tags.
 */
interface TagSearchRepository {

    /**
     * Finds all tags by the given organization ID.
     *
     * @param organizationId The ID of the organization to find tags for.
     * @return A list of tags associated with the specified organization ID.
     */
    suspend fun findAllTagsByOrganizationId(organizationId: OrganizationId): List<Tag>
    /**
     * Finds a tag by its unique identifier.
     *
     * @param organizationId The ID of the organization to find the tag for.
     * @param tagId The unique identifier of the tag.
     * @return The tag with the specified ID, or null if no tag is found.
     */
    suspend fun findById(organizationId: OrganizationId, tagId: TagId): Tag?
}
