package com.lyra.app.newsletter.tag.infrastructure.persistence.mapper

import com.lyra.app.newsletter.tag.domain.Tag
import com.lyra.app.newsletter.tag.domain.TagColor
import com.lyra.app.newsletter.tag.domain.TagId
import com.lyra.app.newsletter.tag.infrastructure.persistence.entity.TagEntity
import com.lyra.app.organization.domain.OrganizationId

/**
 * Mapper object for converting between Tag domain objects and TagEntity persistence objects.
 */
object TagMapper {

    /**
     * Extension function to convert a Tag domain object to a TagEntity persistence object.
     *
     * @return The TagEntity representation of the Tag.
     */
    fun Tag.toEntity() = TagEntity.create(
        id = id.value,
        name = name,
        color = color.value,
        organizationId = organizationId.value,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    /**
     * Extension function to convert a TagEntity persistence object to a Tag domain object.
     *
     * @return The Tag domain representation of the TagEntity.
     */
    fun TagEntity.toDomain() = Tag(
        id = TagId(id),
        name = name,
        color = TagColor.fromString(color),
        organizationId = OrganizationId(organizationId),
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
