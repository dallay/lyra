package com.lyra.app.newsletter.tag.infrastructure.persistence.mapper

import com.lyra.UnitTest
import com.lyra.app.newsletter.tag.domain.SubscriberTag
import com.lyra.app.newsletter.tag.domain.SubscriberTagId
import com.lyra.app.newsletter.tag.infrastructure.persistence.entity.SubscriberTagEntity
import com.lyra.app.newsletter.tag.infrastructure.persistence.entity.SubscriberTagEntityId
import com.lyra.app.newsletter.tag.infrastructure.persistence.mapper.SubscriberTagMapper.toDomain
import com.lyra.app.newsletter.tag.infrastructure.persistence.mapper.SubscriberTagMapper.toEntity
import java.time.LocalDateTime
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@UnitTest
internal class SubscriberTagMapperTest {

    @Test
    fun convertSubscriberTagToEntity() {
        val subscriberTag = SubscriberTag(
            id = SubscriberTagId(UUID.randomUUID(), UUID.randomUUID()),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )

        val subscriberTagEntity = subscriberTag.toEntity()

        assertEquals(subscriberTag.id.value.first, subscriberTagEntity.subscriberId)
        assertEquals(subscriberTag.id.value.second, subscriberTagEntity.tagId)
        assertEquals(subscriberTag.createdAt, subscriberTagEntity.createdAt)
        assertEquals(subscriberTag.updatedAt, subscriberTagEntity.updatedAt)
    }

    @Test
    fun convertSubscriberTagEntityToDomain() {
        val subscriberTagEntityId = SubscriberTagEntityId(
            subscriberId = UUID.randomUUID(),
            tagId = UUID.randomUUID(),
        )
        val subscriberTagEntity = SubscriberTagEntity(
            subscriberId = subscriberTagEntityId.subscriberId,
            tagId = subscriberTagEntityId.tagId,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )

        val subscriberTag = subscriberTagEntity.toDomain()

        assertEquals(subscriberTagEntity.subscriberId, subscriberTag.id.value.first)
        assertEquals(subscriberTagEntity.tagId, subscriberTag.id.value.second)
        assertEquals(subscriberTagEntityId.subscriberId, subscriberTag.id.value.first)
        assertEquals(subscriberTagEntityId.tagId, subscriberTag.id.value.second)
        assertEquals(subscriberTagEntity.createdAt, subscriberTag.createdAt)
        assertEquals(subscriberTagEntity.updatedAt, subscriberTag.updatedAt)
    }
}
