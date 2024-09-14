package com.lyra.app.newsletter.application.count.bystatus

import com.lyra.app.newsletter.domain.SubscriberStatsRepository
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.Service
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.slf4j.LoggerFactory

/**
 * Service class for counting subscribers by their status.
 *
 * This service interacts with the [SubscriberStatsRepository] to retrieve
 * and process subscriber statistics.
 *
 * @property repository The repository used to access subscriber statistics.
 */
@Service
class SubscriberCountByStatus(private val repository: SubscriberStatsRepository) {

    /**
     * Count subscribers by their status.
     *
     * This method retrieves subscriber counts by status from the repository,
     * maps the results to [SubscriberCountByStatusResponse] objects, and returns
     * them as a list.
     *
     * @param organizationId The ID of the organization to count subscribers for.
     * @return A list of subscriber counts by status `List<SubscriberCountByStatusData>`.
     */
    suspend fun count(organizationId: String): List<SubscriberCountByStatusData> {
        log.debug("Counting subscribers by status for organization {}", organizationId)
        return repository.countByStatus(OrganizationId(organizationId)).map { (status, count) ->
            SubscriberCountByStatusData(status, count)
        }.toList()
    }

    companion object {
        private val log = LoggerFactory.getLogger(SubscriberCountByStatus::class.java)
    }
}
