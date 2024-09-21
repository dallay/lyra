package com.lyra.app.newsletter.subscriber.application.search.all

import com.lyra.app.newsletter.subscriber.application.SubscriberResponse
import com.lyra.common.domain.bus.query.Query
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.common.domain.presentation.sort.Sort

/**
 * This class represents a query to search all subscribers.
 *
 * @property criteria The criteria to filter the subscribers. It can be null.
 * @property size The size of the page to return. It can be null.
 * @property cursor The cursor to paginate through the subscribers. It can be null.
 * @property sort The sort order for the subscribers. It can be null.
 */
data class SearchAllSubscribersQuery(
    val criteria: Criteria? = null,
    val size: Int? = null,
    val cursor: String? = null,
    val sort: Sort? = null
) :
    Query<CursorPageResponse<SubscriberResponse>> {

    /**
     * Checks if the given object is equal to this instance.
     *
     * @param other The object to compare with this instance.
     * @return true if the given object is equal to this instance, false otherwise.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SearchAllSubscribersQuery) return false
        return true
    }

    /**
     * Generates a hash code for this instance.
     *
     * @return The hash code of this instance.
     */
    override fun hashCode(): Int = javaClass.hashCode()
}
