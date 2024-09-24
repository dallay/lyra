package com.lyra.app.newsletter.subscriber.application.search.active

import com.lyra.app.newsletter.subscriber.application.SubscribersResponse
import com.lyra.common.domain.bus.query.Query

/**
 * This class represents a query for searching all active subscribers.
 * It implements the Query interface with a SubscribersResponse.
 * The equals and hashCode methods are overridden for proper comparison of query objects.
 */
class SearchAllActiveSubscribersQuery : Query<SubscribersResponse> {
    /**
     * Checks if this query is equal to another object.
     * It is equal if the other object is a SearchAllActiveSubscribersQuery.
     *
     * @param other The object to compare with.
     * @return True if equal, false otherwise.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SearchAllActiveSubscribersQuery) return false
        return true
    }

    /**
     * Returns a hash code value for the object.
     * This method is supported for the benefit of hash tables such as those provided by HashMap.
     *
     * @return The hash code value.
     */
    override fun hashCode(): Int = javaClass.hashCode()
}
