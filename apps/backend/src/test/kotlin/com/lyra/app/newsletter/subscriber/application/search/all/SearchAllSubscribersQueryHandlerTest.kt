package com.lyra.app.newsletter.subscriber.application.search.all

import com.lyra.UnitTest
import com.lyra.app.GeneralStub.getTimestampCursorPage
import com.lyra.app.newsletter.subscriber.SubscriberStub
import com.lyra.app.newsletter.subscriber.domain.SubscriberRepository
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.sort.Sort
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import java.time.LocalDateTime
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private const val NUM_SUBSCRIBER = 100

@UnitTest
internal class SearchAllSubscribersQueryHandlerTest {
    private lateinit var repository: SubscriberRepository
    private lateinit var searcher: SearchAllSubscriberSearcher
    private lateinit var searchAllSubscribersQueryHandler: SearchAllSubscribersQueryHandler

    @BeforeEach
    fun setUp() {
        repository = mockkClass(SubscriberRepository::class)
        searcher = SearchAllSubscriberSearcher(repository)
        searchAllSubscribersQueryHandler = SearchAllSubscribersQueryHandler(searcher)

        val cursorPageResponse = SubscriberStub.dummyRandomSubscribersPageResponse(NUM_SUBSCRIBER)

        coEvery {
            repository.searchAllByCursor(any(Criteria::class), any(Int::class), any(Sort::class))
        } returns cursorPageResponse
    }

    @Test
    fun `should search all subscribers`() = runBlocking {
        // Given
        val query = SearchAllSubscribersQuery(criteria = Criteria.Empty)

        // When
        val response = searchAllSubscribersQueryHandler.handle(query)

        // Then
        val data = response.data
        val nextCursor = response.nextPageCursor
        assertTrue(data.isNotEmpty())
        assertEquals(NUM_SUBSCRIBER, data.size)
        val endDate = data.last().createdAt ?: LocalDateTime.now().toString()
        val cursor = getTimestampCursorPage(endDate)
        assertEquals(cursor, nextCursor)
        coVerify(exactly = 1) { repository.searchAllByCursor(any(Criteria::class), any(Int::class), any(Sort::class)) }
    }
}
