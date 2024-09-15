package com.lyra.app.newsletter.subscriber.application.search.active

import com.lyra.UnitTest
import com.lyra.app.newsletter.subscriber.SubscriberStub
import com.lyra.app.newsletter.subscriber.domain.SubscriberRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class SearchAllActiveSubscribersQueryHandlerTest {
    private lateinit var repository: SubscriberRepository
    private lateinit var searcher: ActiveSubscriberSearcher
    private lateinit var searchAllActiveSubscribersQueryHandler: SearchAllActiveSubscribersQueryHandler

    @BeforeEach
    fun setUp() {
        repository = mockk()
        searcher = ActiveSubscriberSearcher(repository)
        searchAllActiveSubscribersQueryHandler = SearchAllActiveSubscribersQueryHandler(searcher)

        coEvery { repository.searchActive() } returns SubscriberStub.dummyRandomSubscribersFlow()
    }

    @Test
    fun `should search all active subscribers`() = runBlocking {
        // Given
        val query = SearchAllActiveSubscribersQuery()

        // When
        val response = searchAllActiveSubscribersQueryHandler.handle(query)

        // Then
        assertTrue(response.subscribers.isNotEmpty())
        assertEquals(10, response.subscribers.size)
        coVerify(exactly = 1) { repository.searchActive() }
    }
}
