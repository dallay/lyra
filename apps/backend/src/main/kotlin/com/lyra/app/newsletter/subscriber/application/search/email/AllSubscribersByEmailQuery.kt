package com.lyra.app.newsletter.subscriber.application.search.email

import com.lyra.app.newsletter.subscriber.application.SubscribersResponse
import com.lyra.common.domain.bus.query.Query

/**
 * Query for retrieving all subscribers by their email addresses.
 *
 * @property emails The list of email addresses to search for.
 * @property organizationId The identifier of the organization the subscribers belong to.
 * @constructor Creates an instance of AllSubscribersByEmailQuery with the given list of email addresses.
 * @created 10/1/24
 */
data class AllSubscribersByEmailQuery(
    val organizationId: String,
    val emails: List<String>,
) : Query<SubscribersResponse>
