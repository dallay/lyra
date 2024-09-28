package com.lyra.app.newsletter.subscriber.application.count.bystatus

import com.lyra.common.domain.bus.query.Query

/**
 * Query class for counting subscribers by their status.
 *
 * This class represents a query to retrieve subscriber counts by status.
 * It implements the [Query] interface with a response type of [SubscriberCountByStatusResponse].
 */
data class CountByStatusQuery(val organizationId: String) : Query<SubscriberCountByStatusResponse>
