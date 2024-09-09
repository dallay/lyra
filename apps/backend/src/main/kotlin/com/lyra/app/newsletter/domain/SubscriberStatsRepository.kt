package com.lyra.app.newsletter.domain

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
     * @return Flow<Pair<String, Long>> A flow emitting pairs of status and count.
     */
    suspend fun countByStatus(): Flow<Pair<String, Long>>
}
