package com.lyra.app.newsletter.tag.domain

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
    }
}
