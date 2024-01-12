package com.lyra.app.newsletter.application.search.all

import com.lyra.app.newsletter.SubscriberStub
import com.lyra.app.newsletter.domain.SubscriberRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val NUM_SUBSCRIBER = 100

internal class SearchAllSubscribersQueryHandlerTest {
    private val repository = mockkClass(SubscriberRepository::class)
    private val searcher = AllSubscriberSearcher(repository)
    private val searchAllSubscribersQueryHandler = SearchAllSubscribersQueryHandler(searcher)

    @BeforeEach
    fun setUp() {
        coEvery { repository.searchAll() } returns SubscriberStub.dummyRandomSubscribersFlow(
            NUM_SUBSCRIBER,
        )
    }

    @Test
    fun `should search all subscribers`() = runBlocking {
        // Given
        val command = SearchAllSubscribersQuery()
        // When
        val response = searchAllSubscribersQueryHandler.handle(command)
        // Then
        assertTrue(response.subscribers.isNotEmpty())
        assertEquals(NUM_SUBSCRIBER, response.subscribers.size)
        coVerify(exactly = 1) { repository.searchAll() }
    }
}
