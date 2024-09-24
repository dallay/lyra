package com.lyra.app.newsletter.tag.application.list

import com.lyra.app.newsletter.tag.domain.Tag
import com.lyra.app.newsletter.tag.domain.TagSearchRepository
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.Service
import org.slf4j.LoggerFactory

/**
 * Service class for searching all tags for a specific organization.
 *
 * @property repository The repository to search tags.
 */
@Service
class AllTagSearcher(private val repository: TagSearchRepository) {

    /**
     * Searches all tags for a given organization.
     *
     * @param organizationId The ID of the organization to search tags for.
     * @return A list of tags for the specified organization.
     */
    suspend fun search(organizationId: String): List<Tag> {
        log.debug("Searching all tags for organization $organizationId")
        return repository.findAllTagsByOrganizationId(OrganizationId(organizationId))
    }

    companion object {
        private val log = LoggerFactory.getLogger(AllTagSearcher::class.java)
    }
}
