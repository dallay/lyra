package com.lyra.app.workspaces.domain

import com.lyra.app.users.domain.UserId
import java.time.LocalDateTime
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

class WorkspaceTest {
    private lateinit var workspace: Workspace
    private val id = WorkspaceId(UUID.randomUUID())
    private val userId = UserId(UUID.randomUUID())
    private val createdAt = LocalDateTime.now()
    private val updatedAt = LocalDateTime.now()

    @BeforeEach
    fun setup() {
        workspace = Workspace.create(id.value, "Test Workspace", userId.value, createdAt, updatedAt)
    }

    @Test
    fun `create workspace should set correct properties`() {
        assertEquals(id, workspace.id)
        assertEquals("Test Workspace", workspace.name)
        assertEquals(userId, workspace.userId)
        assertEquals(createdAt, workspace.createdAt)
        assertEquals(updatedAt, workspace.updatedAt)
    }

    @Test
    fun `update workspace should change name and update timestamp`() {
        val newName = "Updated Workspace"
        workspace.update(newName)

        assertEquals(newName, workspace.name)
        assertTrue(workspace.updatedAt!!.isAfter(updatedAt))
    }

    @Test
    fun `add collaborator should add new collaborator to workspace`() {
        val collaboratorId = UserId(UUID.randomUUID())
        val role = mock(WorkspaceRole::class.java)

        workspace.addCollaborator(collaboratorId, role)

        assertTrue(workspace.collaborators().any { it.userId == collaboratorId && it.role == role })
    }

    @Test
    fun `add collaborator should not add existing collaborator`() {
        val collaboratorId = UserId(UUID.randomUUID())
        val role = mock(WorkspaceRole::class.java)
        workspace.addCollaborator(collaboratorId, role)

        val initialSize = workspace.collaborators().size

        workspace.addCollaborator(collaboratorId, role)

        assertEquals(initialSize, workspace.collaborators().size)
    }

    @Test
    fun `remove collaborator should remove existing collaborator`() {
        val collaboratorId = UserId(UUID.randomUUID())
        val role = mock(WorkspaceRole::class.java)
        workspace.addCollaborator(collaboratorId, role)

        workspace.removeCollaborator(collaboratorId)

        assertFalse(workspace.collaborators().any { it.userId == collaboratorId })
    }

    @Test
    fun `update collaborator role should change role of existing collaborator`() {
        val collaboratorId = UserId(UUID.randomUUID())
        val role = mock(WorkspaceRole::class.java)
        workspace.addCollaborator(collaboratorId, role)

        val newRole = mock(WorkspaceRole::class.java)
        workspace.updateCollaboratorRole(collaboratorId, newRole)

        assertTrue(workspace.collaborators().any { it.userId == collaboratorId && it.role == newRole })
    }
}
