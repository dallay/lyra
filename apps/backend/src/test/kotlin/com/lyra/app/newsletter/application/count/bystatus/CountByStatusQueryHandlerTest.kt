package com.lyra.app.newsletter.application.count.bystatus

import com.lyra.UnitTest
import com.lyra.app.newsletter.domain.SubscriberStatsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class CountByStatusQueryHandlerTest {
    private val repository = mockk<SubscriberStatsRepository>()
    private val counter = SubscriberCountByStatus(repository)
    private val handler = CountByStatusQueryHandler(counter)
    private val statuses = listOf("ENABLED" to 10L, "DISABLED" to 5L, "BLOCKLISTED" to 98L).asFlow()
    @BeforeEach
    fun setUp() {
        coEvery { repository.countByStatus() } returns statuses
    }

    @Test
    fun `should count subscribers by status`(): Unit = runBlocking {
        // Given
        val query = CountByStatusQuery()

        // When
        val result = handler.handle(query)

        // Then
        coVerify(exactly = 1) { repository.countByStatus() }
        assertEquals(3, result.data.size)
        assertEquals(10L, result.data.first { it.status == "ENABLED" }.count)
        assertEquals(5L, result.data.first { it.status == "DISABLED" }.count)
        assertEquals(98L, result.data.first { it.status == "BLOCKLISTED" }.count)
    }
}
