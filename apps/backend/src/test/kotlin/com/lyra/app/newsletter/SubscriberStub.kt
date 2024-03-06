package com.lyra.app.newsletter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.lyra.app.newsletter.application.SubscriberResponse
import com.lyra.app.newsletter.domain.FirstName
import com.lyra.app.newsletter.domain.LastName
import com.lyra.app.newsletter.domain.Name
import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.app.newsletter.domain.SubscriberId
import com.lyra.app.newsletter.domain.SubscriberStatus
import com.lyra.common.domain.email.Email
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.common.domain.presentation.pagination.TimestampCursor
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import net.datafaker.Faker

/**
 *
 * @created 11/1/24
 */
object SubscriberStub {
    private val faker = Faker()
    fun create(
        id: String = UUID.randomUUID().toString(),
        email: String = faker.internet().emailAddress(),
        firstname: String = faker.name().firstName(),
        lastname: String = faker.name().lastName(),
        status: SubscriberStatus = SubscriberStatus.ENABLED,
    ): Subscriber {
        return Subscriber(
            id = SubscriberId(id),
            email = Email(email),
            name = Name(FirstName(firstname), LastName(lastname)),
            status = status,
        )
    }

    fun dummyRandomSubscribersList(size: Int = 10): List<Subscriber> {
        return (1..size).map {
            create()
        }
    }

    fun dummyRandomSubscribersFlow(size: Int = 10): Flow<Subscriber> =
        dummyRandomSubscribersList(size).asFlow()

    fun dummyRandomSubscribersPageResponse(size: Int = 10): CursorPageResponse<Subscriber> {
        val data = dummyRandomSubscribersList(size)
        val cursor = TimestampCursor(data.last().createdAt).serialize()
        return CursorPageResponse(
            data = data,
            nextPageCursor = cursor,
        )
    }

    fun dummyRandomSubscriberResponsePageResponse(size: Int = 10): CursorPageResponse<SubscriberResponse> {
        val data = dummyRandomSubscribersList(size).map { SubscriberResponse.from(it) }
        val (_, cursor) = getStartAndEndTimestampCursorPage(data)
        return CursorPageResponse(
            data = data,
            nextPageCursor = cursor,
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

    fun getTimestampCursorPage(date: LocalDateTime = LocalDateTime.now()): String =
        TimestampCursor(date).serialize()

    fun getTimestampCursorPage(stringDate: String): String =
        getTimestampCursorPage(LocalDateTime.parse(stringDate))

    @Suppress("MultilineRawStringIndentation")
    fun generateRequest(
        email: String = faker.internet().emailAddress(),
        firstname: String = faker.name().firstName(),
        lastname: String = faker.name().lastName()
    ): String = """
      {
           "email": "$email",
           "firstname": "$firstname",
           "lastname": "$lastname"
       }
    """.trimIndent()

    private fun jsonToSubscriberResponseList(json: String): List<SubscriberResponse> {
        val mapper = jacksonObjectMapper()
        return mapper.readValue(json)
    }

    fun subscriberResponsesByBatch(batch: Int = 1): List<SubscriberResponse> {
        val url = javaClass.classLoader.getResource("db/subscriber-pagination.json")
        val path = Paths.get(url?.toURI() ?: throw FileNotFoundException("File not found"))
        val subscribers: List<SubscriberResponse> = jsonToSubscriberResponseList(Files.readString(path))

        val batches: Map<Int, List<SubscriberResponse>> =
            subscribers.chunked(5).mapIndexed { index, list ->
                index + 1 to list
            }.toMap()

        return batches[batch] ?: emptyList()
    }
}
