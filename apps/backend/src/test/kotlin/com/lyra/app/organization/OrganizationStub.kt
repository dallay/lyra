package com.lyra.app.organization

import com.lyra.app.organization.domain.Organization
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.app.organization.infrastructure.http.request.CreateOrganizationRequest
import com.lyra.app.organization.infrastructure.http.request.UpdateOrganizationRequest
import com.lyra.app.users.domain.UserId
import java.util.*
import net.datafaker.Faker

object OrganizationStub {
    private val faker = Faker()
    fun create(
        id: UUID = UUID.randomUUID(),
        name: String = generateName(),
        userId: UUID = UUID.randomUUID()
    ): Organization = Organization(
        id = OrganizationId(id),
        name = name,
        userId = UserId(userId),
    )

    fun generateRequest(
        name: String = generateName(),
        userId: String = UUID.randomUUID().toString(),
    ): CreateOrganizationRequest = CreateOrganizationRequest(
        name = name,
        userId = userId,
    )

    fun generateUpdateRequest(
        name: String = generateName(),
    ): UpdateOrganizationRequest = UpdateOrganizationRequest(
        name = name,
    )

    fun dummyRandomOrganizations(
        size: Int,
        id: UUID = UUID.randomUUID(),
        name: String = generateName(),
        userId: UUID = UUID.randomUUID()
    ): List<Organization> {
        val organizations = (0 until size).map {
            create(id, name, userId)
        }
        return organizations
    }

    private fun generateName(): String {
        val randomNum = faker.number().numberBetween(1, 4)
        return "Test: ${faker.lorem().words(randomNum).joinToString(" ")}"
    }
}
