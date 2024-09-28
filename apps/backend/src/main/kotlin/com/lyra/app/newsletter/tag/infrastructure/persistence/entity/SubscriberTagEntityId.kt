package com.lyra.app.newsletter.tag.infrastructure.persistence.entity

import com.lyra.common.domain.Generated
import java.io.Serializable
import java.util.UUID

/**
 *
 * @created 20/9/24
 */
@Generated
data class SubscriberTagEntityId(
    val subscriberId: UUID,
    val tagId: UUID,
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}
