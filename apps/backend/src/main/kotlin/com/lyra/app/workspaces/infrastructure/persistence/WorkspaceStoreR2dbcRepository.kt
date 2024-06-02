package com.lyra.app.workspaces.infrastructure.persistence

import com.lyra.app.workspaces.domain.Workspace
import com.lyra.app.workspaces.domain.WorkspaceCollaborators
import com.lyra.app.workspaces.domain.WorkspaceRepository
import com.lyra.app.workspaces.domain.exception.WorkspaceException
import com.lyra.app.workspaces.infrastructure.persistence.mapper.WorkspaceCollaboratorsMapper.toEntity
import com.lyra.app.workspaces.infrastructure.persistence.mapper.WorkspaceMapper.toEntity
import com.lyra.app.workspaces.infrastructure.persistence.repository.WorkspaceCollaboratorsR2dbcRepository
import com.lyra.app.workspaces.infrastructure.persistence.repository.WorkspaceR2dbcRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Repository

// private const val DEFAULT_LIMIT = 20

@Repository
class WorkspaceStoreR2dbcRepository(
    private val workspaceRepository: WorkspaceR2dbcRepository,
    private val workspaceCollaboratorRepository: WorkspaceCollaboratorsR2dbcRepository,
) : WorkspaceRepository {
//    private val criteriaParser = R2DBCCriteriaParser(WorkspaceEntity::class)

    /**
     * Create a new workspace.
     *
     * @param workspace The workspace to be created.
     */
    override suspend fun create(workspace: Workspace) {
        log.debug("Creating workspace with id: {}", workspace.id)
        try {
            workspaceRepository.save(workspace.toEntity())
        } catch (e: DuplicateKeyException) {
            log.error("Workspace already exists in the database: ${workspace.id.value}")
            throw WorkspaceException("Error creating workspace", e)
        }
    }

    /**
     * Creates a new workspace collaborator.
     *
     * @param workspace The [WorkspaceCollaborators] to create.
     */
    override suspend fun create(workspace: WorkspaceCollaborators) {
        log.debug("Creating workspace collaborator with id: {}", workspace.id)
        try {
            val workspaceCollaboratorEntity = workspace.toEntity()
            workspaceCollaboratorRepository.upsert(workspaceCollaboratorEntity)
        } catch (e: DuplicateKeyException) {
            log.error("Workspace Collaborator already exists in the database: ${workspace.id.value}")
            throw WorkspaceException("Error creating workspace collaborator", e)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(WorkspaceStoreR2dbcRepository::class.java)
    }
}
