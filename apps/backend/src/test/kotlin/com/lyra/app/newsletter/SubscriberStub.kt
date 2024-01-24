package com.lyra.app.newsletter

import com.lyra.app.newsletter.application.SubscriberResponse
import com.lyra.app.newsletter.domain.FirstName
import com.lyra.app.newsletter.domain.LastName
import com.lyra.app.newsletter.domain.Name
import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.app.newsletter.domain.SubscriberId
import com.lyra.app.newsletter.domain.SubscriberStatus
import com.lyra.common.domain.email.Email
import com.lyra.common.domain.presentation.pagination.OffsetPage
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

    fun dummyRandomSubscribersOffsetPage(size: Int = 10): OffsetPage<Subscriber> =
        OffsetPage(
            data = dummyRandomSubscribersList(size),
            total = size.toLong(),
            perPage = size,
            page = 1,
        )

    fun dummyRandomSubscriberResponseOffsetPage(size: Int = 10): OffsetPage<SubscriberResponse> =
        OffsetPage(
            data = dummyRandomSubscribersList(size).map { SubscriberResponse.from(it) },
            total = size.toLong(),
            perPage = size,
            page = 1,
        )
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
}
