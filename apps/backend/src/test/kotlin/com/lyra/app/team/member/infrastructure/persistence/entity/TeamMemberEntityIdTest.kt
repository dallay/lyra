package com.lyra.app.team.member.infrastructure.persistence.entity

import com.lyra.UnitTest
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@UnitTest
internal class TeamMemberEntityIdTest {

    @Test
    fun equals_sameObject_returnsTrue() {
        val id = TeamMemberEntityId(UUID.randomUUID(), UUID.randomUUID())
        assertTrue(id == id)
    }

    @Test
    fun equals_differentObjectSameValues_returnsTrue() {
        val teamId = UUID.randomUUID()
        val userId = UUID.randomUUID()
        val id1 = TeamMemberEntityId(teamId, userId)
        val id2 = TeamMemberEntityId(teamId, userId)
        assertTrue(id1 == id2)
    }

    @Test
    fun equals_differentObjectDifferentValues_returnsFalse() {
        val id1 = TeamMemberEntityId(UUID.randomUUID(), UUID.randomUUID())
        val id2 = TeamMemberEntityId(UUID.randomUUID(), UUID.randomUUID())
        assertFalse(id1 == id2)
    }

    @Test
    fun hashCode_sameValues_returnsSameHashCode() {
        val teamId = UUID.randomUUID()
        val userId = UUID.randomUUID()
        val id1 = TeamMemberEntityId(teamId, userId)
        val id2 = TeamMemberEntityId(teamId, userId)
        assertEquals(id1.hashCode(), id2.hashCode())
    }

    @Test
    fun hashCode_differentValues_returnsDifferentHashCode() {
        val id1 = TeamMemberEntityId(UUID.randomUUID(), UUID.randomUUID())
        val id2 = TeamMemberEntityId(UUID.randomUUID(), UUID.randomUUID())
        assertNotEquals(id1.hashCode(), id2.hashCode())
    }
}
