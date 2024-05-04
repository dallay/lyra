package com.lyra.app.forms.application.search

import com.lyra.app.GeneralStub.getTimestampCursorPage
import com.lyra.app.forms.FormStub
import com.lyra.app.forms.domain.FormFinderRepository
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

private const val NUM_FORMS = 100
class SearchFormsQueryHandlerTest {
    private val repository = mockkClass(FormFinderRepository::class)
    private val searcher = FormsSearcher(repository)
    private val searchFormsQueryHandler = SearchFormsQueryHandler(searcher)

    @BeforeEach
    fun setUp() {
        val cursorPageResponse = FormStub.dummyRandomFormsPageResponse(NUM_FORMS)

        coEvery {
            repository.search(
                any(Criteria::class),
                any(Int::class),
                any(Sort::class),
            )
        } returns cursorPageResponse
    }

    @Test
    fun `should search all forms`() = runBlocking {
        val query = SearchFormsQuery(criteria = Criteria.Empty)
        val response = searchFormsQueryHandler.handle(query)
        val data = response.data
        val nextCursor = response.nextPageCursor
        assertTrue(data.isNotEmpty())
        assertEquals(NUM_FORMS, data.size)
        val endDate = data.last().createdAt ?: LocalDateTime.now().toString()
        val cursor = getTimestampCursorPage(endDate)
        assertEquals(cursor, nextCursor)
        coVerify(exactly = 1) { repository.search(any(Criteria::class), any(Int::class), any(Sort::class)) }
    }
}
