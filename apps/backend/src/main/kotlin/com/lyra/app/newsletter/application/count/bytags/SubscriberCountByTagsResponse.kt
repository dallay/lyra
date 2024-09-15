package com.lyra.app.newsletter.application.count.bytags

import com.lyra.common.domain.bus.query.Response

/**
 * Data class representing the response for counting subscribers by their tags.
 *
 * This class implements the [Response] interface and contains the tags of the subscribers
 * and the count of subscribers with that tags.
 *
 * @property data The list of subscriber counts by tags.
 */
data class SubscriberCountByTagsResponse(
    val data: List<SubscriberCountByTagsData>
) : Response

/**
 * Data class representing the data for counting subscribers by their tags.
 *
 * This class contains the tags of the subscribers and the count of subscribers with that tags.
 *
 * @property tag The tags of the subscribers.
 * @property count The count of subscribers with the specified tags.
 *
 */
data class SubscriberCountByTagsData(
    val tag: String,
    val count: Long
)
