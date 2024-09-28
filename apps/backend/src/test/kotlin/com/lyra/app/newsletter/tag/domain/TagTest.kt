package com.lyra.app.newsletter.tag.domain

import com.lyra.UnitTest
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.vo.email.Email
import java.util.UUID
import net.datafaker.Faker
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@UnitTest
internal class TagTest {
    private val faker = Faker()

    @Test
    fun `number of subscribers returns correct count`() {
        val tag = Tag(
            id = TagId(UUID.randomUUID()),
            name = "Test Tag",
            color = TagColor.RED,
            organizationId = OrganizationId(UUID.randomUUID()),
            subscribers = mutableSetOf(Email("test1@example.com"), Email("test2@example.com")),
        )
        assertEquals(2, tag.numberOfSubscribers())
    }

    @Test
    fun `number of subscribers returns zero when no subscribers`() {
        val tag = Tag(
            id = TagId(UUID.randomUUID()),
            name = "Test Tag",
            color = TagColor.RED,
            organizationId = OrganizationId(UUID.randomUUID()),
            subscribers = mutableSetOf(),
        )
        assertEquals(0, tag.numberOfSubscribers())
    }

    @Test
    fun `number of subscribers returns zero when subscribers is null`() {
        val tag = Tag(
            id = TagId(UUID.randomUUID()),
            name = "Test Tag",
            color = TagColor.RED,
            organizationId = OrganizationId(UUID.randomUUID()),
            subscribers = null,
        )
        assertEquals(0, tag.numberOfSubscribers())
    }

    @Test
    fun `update tag with new name and color`() {
        val tagId = UUID.randomUUID()
        val organizationId = UUID.randomUUID()
        val tag = Tag(
            id = TagId(tagId),
            name = "Test Tag",
            color = TagColor.RED,
            organizationId = OrganizationId(organizationId),
        )
        val updatedTag = tag.update("Updated Tag", TagColor.BLUE)
        assertEquals(tagId, updatedTag.id.value)
        assertEquals("Updated Tag", updatedTag.name)
        assertEquals(TagColor.BLUE, updatedTag.color)
        assertEquals(0, updatedTag.numberOfSubscribers())
        assertEquals(organizationId, updatedTag.organizationId.value)
    }

    @Test
    fun `create tag`() {
        val tagId = UUID.randomUUID()
        val organizationId = UUID.randomUUID()
        val tag = Tag.create(
            id = tagId,
            name = "Test Tag",
            color = TagColor.RED,
            organizationId = organizationId,
        )
        assertEquals(tagId, tag.id.value)
        assertEquals("Test Tag", tag.name)
        assertEquals(TagColor.RED, tag.color)
        assertEquals(0, tag.numberOfSubscribers())
        assertEquals(organizationId, tag.organizationId.value)
    }

    @Test
    fun `create tag and add subscribers`() {
        val tagId = UUID.randomUUID()
        val organizationId = UUID.randomUUID()
        val tag = Tag.create(
            id = tagId,
            name = "Test Tag",
            color = TagColor.RED,
            organizationId = organizationId,
        )
        val emails = (1..5).map { Email(faker.internet().emailAddress()) }.toSet()
        tag.addSubscriberEmails(emails)
        assertEquals(5, tag.numberOfSubscribers())
    }
}
