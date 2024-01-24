package com.lyra.app.newsletter.application.search.all

import com.lyra.app.newsletter.SubscriberStub
import com.lyra.app.newsletter.domain.SubscriberRepository
import com.lyra.common.domain.criteria.Criteria
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
    private val searcher = SearchAllSubscriberSearcher(repository)
    private val searchAllSubscribersQueryHandler = SearchAllSubscribersQueryHandler(searcher)

    @BeforeEach
    fun setUp() {
        coEvery { repository.searchAll(any(Criteria::class)) } returns SubscriberStub.dummyRandomSubscribersOffsetPage(
            NUM_SUBSCRIBER,
        )
    }

    @Test
    fun `should search all subscribers`() = runBlocking {
        val query = SearchAllSubscribersQuery(criteria = Criteria.Empty)
        val response = searchAllSubscribersQueryHandler.handle(query)
        val data = response.data
        val total = response.total
        val page = response.page
        val perPage = response.perPage
        assertTrue(data.isNotEmpty())
        assertEquals(100, data.size)
        assertEquals(100, total)
        assertEquals(1, page)
        assertEquals(NUM_SUBSCRIBER, perPage)
        coVerify(exactly = 1) { repository.searchAll(any(Criteria::class)) }
    }
}
