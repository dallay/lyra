package com.lyra.app.newsletter.tag.domain

import com.lyra.app.newsletter.tag.domain.event.TagCreatedEvent
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.BaseEntity
import java.time.LocalDateTime
import java.util.UUID

/**
 * Data class representing a Tag entity.
 *
 * @property id The unique identifier of the tag.
 * @property name The name of the tag.
 * @property color The color of the tag.
 * @property organizationId The identifier of the organization the tag belongs to.
 * @property createdAt The timestamp when the tag was created.
 * @property updatedAt The timestamp when the tag was last updated.
 */
data class Tag(
    override val id: TagId,
    val name: String,
    val color: TagColor,
    val organizationId: OrganizationId,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime? = null,
) : BaseEntity<TagId>() {

    /**
     * Updates the tag with a new name and color.
     *
     * @param name The new name of the tag.
     * @param color The new color of the tag.
     * @return A new Tag instance with updated values.
     */
    fun update(name: String, color: TagColor): Tag =
        this.copy(name = name, color = color, updatedAt = LocalDateTime.now())

    companion object {
        /**
         * Creates a new Tag instance.
         *
         * @param id The unique identifier of the tag.
         * @param name The name of the tag.
         * @param color The color of the tag.
         * @param organizationId The identifier of the organization the tag belongs to.
         * @param createdAt The timestamp when the tag was created.
         * @param updatedAt The timestamp when the tag was last updated.
         * @return A new Tag instance.
         */
        fun create(
            id: UUID,
            name: String,
            color: TagColor,
            organizationId: UUID,
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime? = null
        ): Tag {
            val tag =
                Tag(
                    TagId(id),
                    name,
                    color,
                    OrganizationId(organizationId),
                    createdAt,
                    updatedAt,
                )
            tag.record(
                TagCreatedEvent(
                    tag.id.value.toString(),
                    tag.name,
                    tag.color.value,
                    tag.organizationId.value.toString(),
                ),
            )
            return tag
        }
    }
}
