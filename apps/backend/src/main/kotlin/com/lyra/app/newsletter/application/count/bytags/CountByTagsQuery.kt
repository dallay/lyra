package com.lyra.app.newsletter.application.count.bytags

import com.lyra.common.domain.bus.query.Query

/**
 * Query class for counting subscribers by their status.
 *
 * This class represents a query to retrieve subscriber counts by status.
 * It implements the [Query] interface with a response type of [SubscriberCountByTagsResponse].
 */
data class CountByTagsQuery(val organizationId: String) : Query<SubscriberCountByTagsResponse>
