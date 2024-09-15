package com.lyra.app.newsletter.subscriber.application.count.bytags

import com.lyra.app.newsletter.subscriber.domain.SubscriberStatsRepository
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.Service
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.slf4j.LoggerFactory

/**
 * Service class for counting subscribers by their tag.
 *
 * This service interacts with the [SubscriberStatsRepository] to retrieve
 * and process subscriber statistics.
 *
 * @property repository The repository used to access subscriber statistics.
 */
@Service
class SubscriberCountByTags(private val repository: SubscriberStatsRepository) {

    /**
     * Count subscribers by their tag.
     *
     * This method retrieves subscriber counts by tag from the repository,
     * maps the results to [SubscriberCountByTagsResponse] objects, and returns
     * them as a list.
     *
     * @param organizationId The ID of the organization to count subscribers for.
     * @return A list of [SubscriberCountByTagsData] objects containing the tags and counts.
     */
    suspend fun count(organizationId: String): List<SubscriberCountByTagsData> {
        log.debug("Counting subscribers by tag for organization {}", organizationId)
        return repository.countByTag(OrganizationId(organizationId)).map { (tag, count) ->
            SubscriberCountByTagsData(tag, count)
        }.toList()
    }

    companion object {
        private val log = LoggerFactory.getLogger(SubscriberCountByTags::class.java)
    }
}
