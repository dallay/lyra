package com.lyra.app.newsletter.application.search.all

import com.lyra.app.newsletter.application.SubscribersResponse
import com.lyra.common.domain.bus.query.Query

/**
 *
 * @created 9/1/24
 */
class SearchAllSubscribersQuery : Query<SubscribersResponse> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SearchAllSubscribersQuery) return false
        return true
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
