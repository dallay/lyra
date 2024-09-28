package com.lyra.app.newsletter.tag.infrastructure.persistence.mapper

import com.lyra.app.newsletter.tag.domain.SubscriberTag
import com.lyra.app.newsletter.tag.domain.SubscriberTagId
import com.lyra.app.newsletter.tag.infrastructure.persistence.entity.SubscriberTagEntity

/**
 * Mapper object for converting between SubscriberTag domain objects and SubscriberTagEntity persistence objects.
 */
object SubscriberTagMapper {

    /**
     * Extension function to convert a SubscriberTag domain object to a SubscriberTagEntity persistence object.
     *
     * @receiver SubscriberTag The domain object to be converted.
     * @return SubscriberTagEntity The resulting persistence object.
     */
    fun SubscriberTag.toEntity() = SubscriberTagEntity(
        subscriberId = id.value.first,
        tagId = id.value.second,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    /**
     * Extension function to convert a SubscriberTagEntity persistence object to a SubscriberTag domain object.
     *
     * @receiver SubscriberTagEntity The persistence object to be converted.
     * @return SubscriberTag The resulting domain object.
     */
    fun SubscriberTagEntity.toDomain() = SubscriberTag(
        id = SubscriberTagId(subscriberId, tagId),
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
