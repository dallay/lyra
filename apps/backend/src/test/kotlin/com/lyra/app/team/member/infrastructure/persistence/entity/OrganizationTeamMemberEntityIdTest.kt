package com.lyra.app.team.member.infrastructure.persistence.entity

import com.lyra.UnitTest
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@UnitTest
internal class OrganizationTeamMemberEntityIdTest {

    @Test
    fun equals_sameObject_returnsTrue() {
        val id = OrganizationTeamMemberEntityId(
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
        )
        assertTrue(id == id)
    }

    @Test
    fun equals_differentObjectSameValues_returnsTrue() {
        val organizationId = UUID.randomUUID()
        val organizationOwnerId = UUID.randomUUID()
        val teamId = UUID.randomUUID()
        val userId = UUID.randomUUID()
        val id1 =
            OrganizationTeamMemberEntityId(organizationId, organizationOwnerId, teamId, userId)
        val id2 =
            OrganizationTeamMemberEntityId(organizationId, organizationOwnerId, teamId, userId)
        assertTrue(id1 == id2)
    }

    @Test
    fun equals_differentObjectDifferentValues_returnsFalse() {
        val id1 = OrganizationTeamMemberEntityId(
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
        )
        val id2 = OrganizationTeamMemberEntityId(
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
        )
        assertFalse(id1 == id2)
    }

    @Test
    fun hashCode_sameValues_returnsSameHashCode() {
        val organizationId = UUID.randomUUID()
        val organizationOwnerId = UUID.randomUUID()
        val teamId = UUID.randomUUID()
        val userId = UUID.randomUUID()
        val id1 =
            OrganizationTeamMemberEntityId(organizationId, organizationOwnerId, teamId, userId)
        val id2 =
            OrganizationTeamMemberEntityId(organizationId, organizationOwnerId, teamId, userId)
        assertEquals(id1.hashCode(), id2.hashCode())
    }

    @Test
    fun hashCode_differentValues_returnsDifferentHashCode() {
        val id1 = OrganizationTeamMemberEntityId(
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
        )
        val id2 = OrganizationTeamMemberEntityId(
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
        )
        assertNotEquals(id1.hashCode(), id2.hashCode())
    }
}
