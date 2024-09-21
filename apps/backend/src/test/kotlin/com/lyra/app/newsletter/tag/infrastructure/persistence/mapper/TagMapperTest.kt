package com.lyra.app.newsletter.tag.infrastructure.persistence.mapper

import com.lyra.UnitTest
import com.lyra.app.newsletter.tag.domain.Tag
import com.lyra.app.newsletter.tag.domain.TagColor
import com.lyra.app.newsletter.tag.domain.TagId
import com.lyra.app.newsletter.tag.infrastructure.persistence.entity.TagEntity
import com.lyra.app.newsletter.tag.infrastructure.persistence.mapper.TagMapper.toDomain
import com.lyra.app.newsletter.tag.infrastructure.persistence.mapper.TagMapper.toEntity
import com.lyra.app.organization.domain.OrganizationId
import java.time.LocalDateTime
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@UnitTest
internal class TagMapperTest {

    @Test
    fun convertTagToEntity() {
        val tag = Tag(
            id = TagId(UUID.randomUUID()),
            name = "Test Tag",
            color = TagColor.RED,
            organizationId = OrganizationId(UUID.randomUUID()),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )

        val tagEntity = tag.toEntity()

        assertEquals(tag.id.value, tagEntity.id)
        assertEquals(tag.name, tagEntity.name)
        assertEquals(tag.color.value, tagEntity.color)
        assertEquals(tag.organizationId.value, tagEntity.organizationId)
        assertEquals(tag.createdAt, tagEntity.createdAt)
        assertEquals(tag.updatedAt, tagEntity.updatedAt)
    }

    @Test
    fun convertTagEntityToDomain() {
        val tagEntity = TagEntity.create(
            id = UUID.randomUUID(),
            name = "Test Tag",
            color = "red",
            organizationId = UUID.randomUUID(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )

        val tag = tagEntity.toDomain()

        assertEquals(tagEntity.id, tag.id.value)
        assertEquals(tagEntity.name, tag.name)
        assertEquals(tagEntity.color, tag.color.value)
        assertEquals(tagEntity.organizationId, tag.organizationId.value)
        assertEquals(tagEntity.createdAt, tag.createdAt)
        assertEquals(tagEntity.updatedAt, tag.updatedAt)
    }
}
