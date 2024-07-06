package com.lyra.app.organization.application.find.all

import com.lyra.UnitTest
import com.lyra.app.organization.domain.Organization
import com.lyra.app.organization.domain.OrganizationFinderRepository
import com.lyra.app.organization.infrastructure.OrganizationStub
import com.lyra.app.users.domain.UserId
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class AllOrganizationQueryHandlerTest {
    private lateinit var repository: OrganizationFinderRepository
    private lateinit var finder: AllOrganizationFinder
    private lateinit var handler: AllOrganizationsQueryHandler
    private lateinit var organizations: List<Organization>
    private lateinit var userId: UserId

    @BeforeEach
    fun setUp() {
        repository = mockk()
        finder = AllOrganizationFinder(repository)
        handler = AllOrganizationsQueryHandler(finder)
        organizations = OrganizationStub.dummyRandomOrganizations(6)
        userId = UserId(UUID.randomUUID().toString())

        coEvery { repository.findAll(userId) } returns organizations
    }

    @Test
    fun `should find all organizations`() = runBlocking {
        // Given
        val query = AllOrganizationQuery(userId.value.toString())

        // When
        val response = handler.handle(query)

        // Then
        assertEquals(organizations.size, response.data.size)
    }
}
