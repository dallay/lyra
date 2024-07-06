package com.lyra.app.organization.application.find

import com.lyra.UnitTest
import com.lyra.app.organization.application.OrganizationResponse
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.app.organization.domain.exception.OrganizationNotFoundException
import com.lyra.app.organization.infrastructure.OrganizationStub
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class FindOrganizationQueryHandlerTest {

    private lateinit var organizationFinder: OrganizationFinder
    private lateinit var findOrganizationQueryHandler: FindOrganizationQueryHandler

    @BeforeEach
    fun setup() {
        organizationFinder = mockk()
        findOrganizationQueryHandler = FindOrganizationQueryHandler(organizationFinder)
    }

    @Test
    fun `should return organization response when organization is found`() = runBlocking {
        // Given
        val id = UUID.randomUUID().toString()
        val organizationId = OrganizationId(id)
        val organization = OrganizationStub.create()
        val organizationResponse = OrganizationResponse.from(organization)
        coEvery { organizationFinder.find(organizationId) } returns organization

        // When
        val result = findOrganizationQueryHandler.handle(FindOrganizationQuery(id))

        // Then
        assertEquals(organizationResponse, result)
    }

    @Test
    fun `should throw exception when organization is not found`(): Unit = runBlocking {
        // Given
        val id = UUID.randomUUID().toString()
        val organizationId = OrganizationId(id)
        coEvery { organizationFinder.find(organizationId) } returns null

        // Then
        assertThrows(OrganizationNotFoundException::class.java) {
            // When
            runBlocking {
                findOrganizationQueryHandler.handle(FindOrganizationQuery(id))
            }
        }
    }
}
