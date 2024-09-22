package com.lyra.app.newsletter.tag.domain

import com.lyra.app.organization.domain.OrganizationId

/**
 * Repository interface for searching tags.
 */
fun interface TagSearchRepository {

    /**
     * Finds all tags by the given organization ID.
     *
     * @param organizationId The ID of the organization to find tags for.
     * @return A list of tags associated with the specified organization ID.
     */
    suspend fun findAllTagsByOrganizationId(organizationId: OrganizationId): List<Tag>
}
