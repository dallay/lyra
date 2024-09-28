package com.lyra.app.newsletter.subscriber.application.count.bystatus

import com.lyra.common.domain.bus.query.Response

/**
 * Data class representing the response for counting subscribers by their status.
 *
 * This class implements the [Response] interface and contains the status of the subscribers
 * and the count of subscribers with that status.
 *
 * @property data The list of subscriber counts by status.
 */
data class SubscriberCountByStatusResponse(
    val data: List<SubscriberCountByStatusData>
) : Response

/**
 * Data class representing the data for counting subscribers by their status.
 *
 * This class contains the status of the subscribers and the count of subscribers with that status.
 *
 * @property status The status of the subscribers.
 * @property count The count of subscribers with the specified status.
 *
 */
data class SubscriberCountByStatusData(
    val status: String,
    val count: Long
)
