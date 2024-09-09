package com.lyra.app.newsletter.application.count.bystatus

import com.lyra.common.domain.bus.query.Query

/**
 * Query class for counting subscribers by their status.
 *
 * This class represents a query to retrieve subscriber counts by status.
 * It implements the [Query] interface with a response type of [SubscriberCountByStatusResponse].
 */
class CountByStatusQuery : Query<SubscriberCountByStatusResponse> {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CountByStatusQuery) return false
        return true
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
