package com.lyra.app.workspaces.infrastructure.persistence.mapper

import com.lyra.app.users.domain.UserId
import com.lyra.app.workspaces.domain.Workspace
import com.lyra.app.workspaces.domain.WorkspaceId
import com.lyra.app.workspaces.infrastructure.persistence.entity.WorkspaceEntity

/**
 * This object provides mapping functions to convert between domain and entity objects.
 */
object WorkspaceMapper {
    /**
     * Converts a [Workspace] domain object to a [WorkspaceEntity].
     *
     * @receiver The [Workspace] domain object to convert.
     * @return The converted [WorkspaceEntity].
     */
    fun Workspace.toEntity(): WorkspaceEntity = WorkspaceEntity(
        workspaceId = id.value,
        workspaceName = name,
        userId = userId.value,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    /**
     * Converts a [WorkspaceEntity] to a [Workspace] domain object.
     *
     * @receiver The [WorkspaceEntity] to convert.
     * @return The converted [Workspace] domain object.
     */
    fun WorkspaceEntity.toDomain(): Workspace = Workspace(
        id = WorkspaceId(workspaceId),
        name = workspaceName,
        userId = UserId(userId),
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
