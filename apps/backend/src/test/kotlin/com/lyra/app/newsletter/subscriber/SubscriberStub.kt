package com.lyra.app.newsletter.subscriber

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.lyra.app.GeneralStub.getTimestampCursorPage
import com.lyra.app.newsletter.subscriber.application.SubscriberResponse
import com.lyra.app.newsletter.subscriber.domain.Attributes
import com.lyra.app.newsletter.subscriber.domain.FirstName
import com.lyra.app.newsletter.subscriber.domain.LastName
import com.lyra.app.newsletter.subscriber.domain.Name
import com.lyra.app.newsletter.subscriber.domain.Subscriber
import com.lyra.app.newsletter.subscriber.domain.SubscriberId
import com.lyra.app.newsletter.subscriber.domain.SubscriberStatus
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.common.domain.presentation.pagination.TimestampCursor
import com.lyra.common.domain.vo.email.Email
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import net.datafaker.Faker

object SubscriberStub {
    private val faker = Faker()
    fun create(
        id: String = UUID.randomUUID().toString(),
        email: String = faker.internet().emailAddress(),
        firstname: String = faker.name().firstName(),
        lastname: String = faker.name().lastName(),
        status: SubscriberStatus = SubscriberStatus.ENABLED,
        organizationId: String = UUID.randomUUID().toString(),
        attributes: Attributes = Attributes(tags = listOf("tag1", "tag2"))
    ): Subscriber = Subscriber(
        id = SubscriberId(id),
        email = Email(email),
        name = Name(FirstName(firstname), LastName(lastname)),
        status = status,
        organizationId = OrganizationId(organizationId),
        attributes = attributes,
    )
    fun dummyRandomSubscribersList(size: Int = 10): List<Subscriber> {
        return (1..size).map {
            create()
        }
    }

    fun dummyRandomSubscribersPageResponse(size: Int = 10): CursorPageResponse<Subscriber> {
        val data = dummyRandomSubscribersList(size)
        val (previousCursor, nextCursor) = getStartAndEndTimestampCursorPage(data.map { SubscriberResponse.from(it) })
        return CursorPageResponse(
            data = data,
            prevPageCursor = previousCursor,
            nextPageCursor = nextCursor,
        )
    }

    fun dummyRandomSubscriberPageResponse(size: Int = 10): CursorPageResponse<SubscriberResponse> {
        val data = dummyRandomSubscribersList(size).map { SubscriberResponse.from(it) }
        val (previousCursor, nextCursor) = getStartAndEndTimestampCursorPage(data)
        return CursorPageResponse(
            data = data,
            prevPageCursor = previousCursor,
            nextPageCursor = nextCursor,
        )
    }

    private fun getStartAndEndTimestampCursorPage(data: List<SubscriberResponse>): Pair<String, String> {
        val startCreatedAt = data.first().createdAt
        val startCursor = startCreatedAt?.let { getTimestampCursorPage(it) }
            ?: TimestampCursor.DEFAULT_CURSOR.serialize()
        val lastCreatedAt = data.last().createdAt
        val endCursor = lastCreatedAt?.let { getTimestampCursorPage(it) }
            ?: TimestampCursor.DEFAULT_CURSOR.serialize()
        return Pair(startCursor, endCursor)
    }

    @Suppress("MultilineRawStringIndentation")
    fun generateRequest(
        email: String = faker.internet().emailAddress(),
        firstname: String = faker.name().firstName(),
        lastname: String = faker.name().lastName(),
    ): String = """
      {
           "email": "$email",
           "firstname": "$firstname",
           "lastname": "$lastname",
           "attributes": {
             "tags": ["tag1", "tag2"]
           }
       }
    """.trimIndent()

    private fun jsonToSubscriberResponseList(json: String): List<SubscriberResponse> {
        val mapper = jacksonObjectMapper()
        return mapper.readValue(json)
    }

    fun subscriberResponsesByBatch(batch: Int = 1): List<SubscriberResponse> {
        val url = javaClass.classLoader.getResource("db/subscriber/subscriber-pagination.json")
        val path = Paths.get(url?.toURI() ?: throw FileNotFoundException("File not found"))
        val subscribers: List<SubscriberResponse> = jsonToSubscriberResponseList(Files.readString(path))

        val batches: Map<Int, List<SubscriberResponse>> =
            subscribers.chunked(5).mapIndexed { index, list ->
                index + 1 to list
            }.toMap()

        return batches[batch] ?: emptyList()
    }
}
