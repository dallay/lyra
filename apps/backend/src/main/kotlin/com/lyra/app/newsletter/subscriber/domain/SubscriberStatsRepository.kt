package com.lyra.app.newsletter.subscriber.domain

import com.lyra.app.organization.domain.OrganizationId
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for subscriber statistics.
 *
 * This interface defines methods for retrieving statistics about subscribers,
 * such as counting subscribers by their status.
 *
 * @created 8/9/24
 */
interface SubscriberStatsRepository {
    /**
     * Count subscribers by their status.
     *
     * This method returns a flow of pairs, where each pair consists of a status
     * (as a string) and the count of subscribers with that status (as an integer).
     *
     * @param organizationId The ID of the organization to count subscribers for.
     * @return Flow<Pair<String, Long>> A flow emitting pairs of status and count.
     */
    suspend fun countByStatus(organizationId: OrganizationId): Flow<Pair<String, Long>>

    /**
     * Count subscribers by tags.
     *
     * This method returns a flow of pairs, where each pair consists of a tag
     * (as a string) and the count of subscribers with that tag (as an integer).
     *
     * @param organizationId The ID of the organization to count subscribers for.
     * @return Flow<Pair<String, Long>> A flow emitting pairs of tag and count.
     */
    suspend fun countByTag(organizationId: OrganizationId): Flow<Pair<String, Long>>
}
