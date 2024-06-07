package com.lyra.app.workspaces.infrastructure

import com.lyra.app.users.domain.UserId
import com.lyra.app.workspaces.domain.Workspace
import com.lyra.app.workspaces.domain.WorkspaceCollaborators
import com.lyra.app.workspaces.domain.WorkspaceId
import com.lyra.app.workspaces.domain.WorkspaceRole
import com.lyra.app.workspaces.infrastructure.http.request.CreateWorkspaceRequest
import com.lyra.app.workspaces.infrastructure.http.request.UpdateWorkspaceRequest
import java.util.*
import net.datafaker.Faker

object WorkspaceStub {
    private val faker = Faker()
    fun create(
        id: UUID = UUID.randomUUID(),
        name: String = generateName(),
        userId: UUID = UUID.randomUUID()
    ): Workspace = Workspace(
        id = WorkspaceId(id),
        name = name,
        userId = UserId(userId),
    )

    fun createCollaborator(
        workspaceId: UUID = UUID.randomUUID(),
        userId: UUID = UUID.randomUUID(),
        role: WorkspaceRole = WorkspaceRole.COLLABORATOR,
    ): WorkspaceCollaborators = WorkspaceCollaborators.create(
        workspaceId = workspaceId.toString(),
        userId = userId.toString(),
        role = role,
    )

    fun generateRequest(
        name: String = generateName(),
        userId: String = UUID.randomUUID().toString(),
    ): CreateWorkspaceRequest = CreateWorkspaceRequest(
        name = name,
        userId = userId,
    )

    fun generateUpdateRequest(
        name: String = generateName(),
    ): UpdateWorkspaceRequest = UpdateWorkspaceRequest(
        name = name,
    )

    fun dummyRandomWorkspaces(size: Int): List<Workspace> {
        val workspaces = (0 until size).map {
            create()
        }
        return workspaces
    }

    private fun generateName(): String {
        val randomNum = faker.number().numberBetween(1, 4)
        return "Test: ${faker.lorem().words(randomNum).joinToString(" ")}"
    }
}
