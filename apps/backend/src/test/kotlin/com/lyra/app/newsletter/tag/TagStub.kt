package com.lyra.app.newsletter.tag

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.lyra.app.newsletter.tag.domain.SubscriberTag
import com.lyra.app.newsletter.tag.domain.SubscriberTagId
import com.lyra.app.newsletter.tag.domain.Tag
import com.lyra.app.newsletter.tag.domain.TagColor
import com.lyra.app.newsletter.tag.domain.TagId
import com.lyra.app.newsletter.tag.infrastructure.http.request.CreateTagRequest
import com.lyra.app.newsletter.tag.infrastructure.http.request.UpdateTagRequest
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.vo.email.Email
import java.util.UUID
import kotlin.String
import kotlin.collections.Set
import net.datafaker.Faker

object TagStub {
    private val faker = Faker()
    private val objectMapper = jacksonObjectMapper()
    private val allColorSupported = listOf("default", "purple", "pink", "red", "blue", "yellow")
    private val subscriberEmails = (0 until 1000).map { faker.internet().emailAddress() }.toSet()
    fun create(
        id: String = UUID.randomUUID().toString(),
        name: String = faker.lorem().word(),
        color: String = allColorSupported.random(),
        subscribers: Set<String> = subscriberEmails.take(10).toSet(),
        organizationId: String = UUID.randomUUID().toString()
    ) = Tag(
        id = TagId(id),
        name = name,
        color = TagColor.fromString(color),
        organizationId = OrganizationId(organizationId),
        subscribers = subscribers.map { Email(it) }.toMutableSet(),
    )

    fun createSubscriberTag(
        subscriberId: UUID = UUID.randomUUID(),
        tagId: UUID = UUID.randomUUID()
    ): SubscriberTag = SubscriberTag(
        id = SubscriberTagId(subscriberId, tagId),
    )

    fun generateTagRequest(
        name: String = faker.lorem().word(),
        color: String = allColorSupported.random(),
        subscriberEmails: Set<String> = (0 until 10).map { faker.internet().emailAddress() }
            .toSet(),
        isUpdate: Boolean = false
    ): String {
        val request = if (isUpdate) {
            UpdateTagRequest(
                name = name,
                color = color,
                subscribers = subscriberEmails,
            )
        } else {
            CreateTagRequest(
                name = name,
                color = color,
                subscribers = subscriberEmails,
            )
        }
        return objectMapper.writeValueAsString(request)
    }

    fun randomTagsList(size: Int = 10): List<Tag> = (0 until size).map { create() }
}
