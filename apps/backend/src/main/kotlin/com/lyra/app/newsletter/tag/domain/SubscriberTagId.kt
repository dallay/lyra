package com.lyra.app.newsletter.tag.domain

import com.lyra.app.newsletter.subscriber.domain.SubscriberId
import com.lyra.common.domain.BaseId
import java.util.UUID

/**
 * Data class representing the unique identifier for a SubscriberTag.
 *
 * @property subscriberId The UUID of the subscriber.
 * @property tagId The UUID of the tag.
 * @constructor Creates a SubscriberTagId from subscriberId and tagId.
 * @created 18/9/24
 */
data class SubscriberTagId(
    private val subscriberId: UUID,
    private val tagId: UUID
) : BaseId<Pair<UUID, UUID>>(subscriberId to tagId) {
    /**
     * Secondary constructor to create a SubscriberTagId from SubscriberId and TagId.
     *
     * @param subscriberId The SubscriberId object.
     * @param tagId The TagId object.
     */
    constructor(subscriberId: SubscriberId, tagId: TagId) : this(subscriberId.value, tagId.value)

    /**
     * Secondary constructor to create a SubscriberTagId from string representations of UUIDs.
     *
     * @param subscriberId The string representation of the subscriber UUID.
     * @param tagId The string representation of the tag UUID.
     */
    constructor(subscriberId: String, tagId: String) : this(
        UUID.fromString(subscriberId),
        UUID.fromString(tagId),
    )

    /**
     * Returns a string representation of the SubscriberTagId.
     *
     * @return A string in the format "subscriberId-tagId".
     */
    override fun toString(): String = "$subscriberId-$tagId"

    companion object {
        /**
         * Factory method to create a SubscriberTagId from UUIDs.
         *
         * @param subscriberId The UUID of the subscriber.
         * @param tagId The UUID of the tag.
         * @return A new instance of SubscriberTagId.
         */
        fun create(subscriberId: UUID, tagId: UUID) = SubscriberTagId(subscriberId, tagId)

        /**
         * Factory method to create a SubscriberTagId from string representations of UUIDs.
         *
         * @param subscriberId The string representation of the subscriber UUID.
         * @param tagId The string representation of the tag UUID.
         * @return A new instance of SubscriberTagId.
         */
        fun create(subscriberId: String, tagId: String) = SubscriberTagId(
            UUID.fromString(subscriberId),
            UUID.fromString(tagId),
        )
    }
}
