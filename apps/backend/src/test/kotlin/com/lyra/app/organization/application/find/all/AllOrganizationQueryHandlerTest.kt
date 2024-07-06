package com.lyra.app.organization.application.find.all

import com.lyra.UnitTest
import com.lyra.app.organization.domain.OrganizationFinderRepository
import com.lyra.app.organization.infrastructure.OrganizationStub
import com.lyra.app.users.domain.UserId
import io.mockk.coEvery
import io.mockk.mockkClass
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class AllOrganizationQueryHandlerTest {
    private val repository = mockkClass(OrganizationFinderRepository::class)
    private val finder = AllOrganizationFinder(repository)
    private val handler = AllOrganizationsQueryHandler(finder)
    private val organizations = OrganizationStub.dummyRandomOrganizations(6)
    private val userId: UserId = UserId(UUID.randomUUID().toString())

    @BeforeEach
    fun setUp() {
        coEvery { repository.findAll(userId) } returns organizations
    }

    @Test
    fun `should find all organization`() = runBlocking {
        val query = AllOrganizationQuery(userId.value.toString())
        val response = handler.handle(query)
        assertEquals(organizations.size, response.data.size)
    }
}
