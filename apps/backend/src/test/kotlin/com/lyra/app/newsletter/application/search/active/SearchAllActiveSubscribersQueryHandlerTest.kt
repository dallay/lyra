package com.lyra.app.newsletter.application.search.active

import com.lyra.app.newsletter.SubscriberStub
import com.lyra.app.newsletter.domain.SubscriberRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SearchAllActiveSubscribersQueryHandlerTest {
    private val repository: SubscriberRepository = mockk()

    private var searcher: ActiveSubscriberSearcher = ActiveSubscriberSearcher(repository)

    private var searchAllActiveSubscribersQueryHandler: SearchAllActiveSubscribersQueryHandler =
        SearchAllActiveSubscribersQueryHandler(searcher)

    @BeforeEach
    fun setUp() {
        coEvery { repository.searchActive() } returns SubscriberStub.dummyRandomSubscribersFlow()
    }

    @Test
    fun `should search all active subscribers`() = runBlocking {
        // Given
        val command = SearchAllActiveSubscribersQuery()
        // When
        val response = searchAllActiveSubscribersQueryHandler.handle(command)
        // Then
        assertTrue(response.subscribers.isNotEmpty())
        assertEquals(10, response.subscribers.size)
        coVerify(exactly = 1) { repository.searchActive() }
    }
}
