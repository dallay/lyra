package com.lyra.app.workspaces.infrastructure.persistence.mapper

import com.lyra.app.users.domain.UserId
import com.lyra.app.workspaces.domain.WorkspaceCollaborators
import com.lyra.app.workspaces.domain.WorkspaceId
import com.lyra.app.workspaces.domain.WorkspaceRole
import com.lyra.app.workspaces.infrastructure.persistence.entity.WorkspaceCollaboratorEntity

/**
 * This object provides mapping functions to convert between domain and entity objects.
 */
object WorkspaceCollaboratorsMapper {

    /**
     * Converts a [WorkspaceCollaborators] domain object to a [WorkspaceCollaboratorEntity].
     *
     * @receiver The [WorkspaceCollaborators] domain object to convert.
     * @return The converted [WorkspaceCollaboratorEntity].
     */
    fun WorkspaceCollaborators.toEntity(): WorkspaceCollaboratorEntity = WorkspaceCollaboratorEntity(
        workspaceId = id.value,
        userId = userId.value,
        role = role.toString(),
        addedAt = addedAt,
    )

    /**
     * Converts a [WorkspaceCollaboratorEntity] to a [WorkspaceCollaborators] domain object.
     *
     * @receiver The [WorkspaceCollaboratorEntity] to convert.
     * @return The converted [WorkspaceCollaborators] domain object.
     */
    fun WorkspaceCollaboratorEntity.toDomain(): WorkspaceCollaborators = WorkspaceCollaborators(
        id = WorkspaceId(workspaceId),
        userId = UserId(userId),
        role = WorkspaceRole.valueOf(role),
        addedAt = addedAt,
    )
}
