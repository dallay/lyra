package com.lyra.app.newsletter.application.search.all

import com.lyra.UnitTest
import com.lyra.app.GeneralStub.getTimestampCursorPage
import com.lyra.app.newsletter.SubscriberStub
import com.lyra.app.newsletter.domain.SubscriberRepository
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
    private val repository = mockkClass(SubscriberRepository::class)
    private val searcher = SearchAllSubscriberSearcher(repository)
    private val searchAllSubscribersQueryHandler = SearchAllSubscribersQueryHandler(searcher)

    @BeforeEach
    fun setUp() {
        val cursorPageResponse = SubscriberStub
            .dummyRandomSubscribersPageResponse(
                NUM_SUBSCRIBER,
            )
        coEvery {
            repository.searchAllByCursor(
                any(Criteria::class),
                any(Int::class),
                any(Sort::class),
            )
        } returns cursorPageResponse
    }

    @Test
    fun `should search all subscribers`() = runBlocking {
        val query = SearchAllSubscribersQuery(criteria = Criteria.Empty)
        val response = searchAllSubscribersQueryHandler.handle(query)
        val data = response.data
        val nextCursor = response.nextPageCursor
        assertTrue(data.isNotEmpty())
        assertEquals(100, data.size)
        val endDate = data.last().createdAt ?: LocalDateTime.now().toString()
        val cursor = getTimestampCursorPage(endDate)
        assertEquals(cursor, nextCursor)
        coVerify(exactly = 1) { repository.searchAllByCursor(any(Criteria::class), any(Int::class), any(Sort::class)) }
    }
}
