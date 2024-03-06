package com.lyra.app.newsletter.application.search.all

import com.lyra.app.newsletter.application.SubscriberResponse
import com.lyra.common.domain.bus.query.Query
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.common.domain.presentation.sort.Sort

/**
 *
 * @created 9/1/24
 */
class SearchAllSubscribersQuery(
    val criteria: Criteria? = null,
    val size: Int? = null,
    val cursor: String? = null,
    val sort: Sort? = null
) :
    Query<CursorPageResponse<SubscriberResponse>> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SearchAllSubscribersQuery) return false
        return true
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
