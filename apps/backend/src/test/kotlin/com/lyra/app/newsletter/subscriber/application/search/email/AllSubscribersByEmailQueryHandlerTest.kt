package com.lyra.app.newsletter.subscriber.application.search.email

import com.lyra.UnitTest
import com.lyra.app.newsletter.subscriber.SubscriberStub
import com.lyra.app.newsletter.subscriber.application.SubscriberResponse
import com.lyra.app.newsletter.subscriber.domain.Subscriber
import com.lyra.app.newsletter.subscriber.domain.SubscriberSearchRepository
import com.lyra.app.organization.domain.OrganizationId
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class AllSubscribersByEmailQueryHandlerTest {
    private val repository = mockk<SubscriberSearchRepository>()
    private val allSubscribersByEmails = GetAllSubscribersByEmailService(repository)
    private val handler = AllSubscribersByEmailQueryHandler(allSubscribersByEmails)
    private val orgId = "27172d5a-b88e-451c-9787-312706f4570d"
    private val organizationId = OrganizationId(orgId)
    private val subscribers = SubscriberStub.dummyRandomSubscribersList(30)
    private lateinit var emails: Set<String>

    @BeforeEach
    fun setUp() {
        emails = subscribers.map { it.email.value }.toSet()
        coEvery {
            repository.searchAllByEmails(
                organizationId,
                emails,
            )
        } returns subscribers
    }

    @Test
    fun `should search all subscribers by emails`(): Unit = runBlocking {
        // Given
        val query = AllSubscribersByEmailQuery(orgId, emails)

        // When
        val result = handler.handle(query)

        // Then
        coVerify(exactly = 1) { repository.searchAllByEmails(organizationId, emails) }
        assertEquals(
            subscribers.map {
                SubscriberResponse.from(it)
            },
            result.subscribers,
        )
    }

    @Test
    fun `should return empty list when no subscribers are found`(): Unit = runBlocking {
        // Given
        val query = AllSubscribersByEmailQuery(orgId, emails)
        coEvery {
            repository.searchAllByEmails(
                organizationId,
                emails,
            )
        } returns emptyList<Subscriber>()

        // When
        val result = handler.handle(query)

        // Then
        coVerify(exactly = 1) { repository.searchAllByEmails(organizationId, emails) }
        assertEquals(emptyList<Subscriber>(), result.subscribers)
    }

    @Test
    fun `should return empty list when no emails are provided`(): Unit = runBlocking {
        // Given
        val query = AllSubscribersByEmailQuery(orgId, emptySet())
        coEvery {
            repository.searchAllByEmails(
                organizationId,
                emptySet(),
            )
        } returns emptyList<Subscriber>()

        // When
        val result = handler.handle(query)

        // Then
        coVerify(exactly = 0) { repository.searchAllByEmails(organizationId, emptySet()) }
        assertEquals(emptyList<Subscriber>(), result.subscribers)
    }
}
