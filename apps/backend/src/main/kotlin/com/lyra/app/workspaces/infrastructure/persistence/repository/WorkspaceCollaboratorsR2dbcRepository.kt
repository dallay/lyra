package com.lyra.app.workspaces.infrastructure.persistence.repository

import com.lyra.app.workspaces.infrastructure.persistence.entity.WorkspaceCollaboratorEntity
import com.lyra.app.workspaces.infrastructure.persistence.entity.WorkspaceCollaboratorId
import com.lyra.spring.boot.repository.ReactiveSearchRepository
import java.util.*
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkspaceCollaboratorsR2dbcRepository :
    CoroutineCrudRepository<WorkspaceCollaboratorEntity, WorkspaceCollaboratorId>,
    ReactiveSearchRepository<WorkspaceCollaboratorEntity> {
    suspend fun findByWorkspaceIdAndUserId(workspaceId: UUID, userId: UUID): WorkspaceCollaboratorEntity
    suspend fun deleteByWorkspaceIdAndUserId(workspaceId: UUID, userId: UUID)

    /**
     * Upserts a workspace collaborator.
     * This is a workaround until the Spring R2DBC team provides native support for composite primary keys.
     * The issues where this topic is being discussed are:
     * [Support for Composite Keys using @Embeded and @Id](https://github.com/spring-projects/spring-data-relational/issues/574)
     * [Add support for composite Id's](https://github.com/spring-projects/spring-data-r2dbc/issues/158)
     *
     */
    @Modifying
    @Query(
        """
    INSERT INTO workspace_collaborators (workspace_id, user_id, role, added_at, created_at, updated_at)
    VALUES (:#{#entity.workspaceId}, :#{#entity.userId}, :#{#entity.role}::role_type, :#{#entity.addedAt}, :#{#entity.createdAt}, :#{#entity.updatedAt})
    ON CONFLICT (workspace_id, user_id) DO UPDATE SET role = :#{#entity.role}::role_type, updated_at = NOW()
    """,
    )
    suspend fun upsert(entity: WorkspaceCollaboratorEntity): Int
}
