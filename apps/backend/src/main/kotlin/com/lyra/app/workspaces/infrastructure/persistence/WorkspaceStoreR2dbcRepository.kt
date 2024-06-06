package com.lyra.app.workspaces.infrastructure.persistence

import com.lyra.app.workspaces.domain.Workspace
import com.lyra.app.workspaces.domain.WorkspaceCollaborators
import com.lyra.app.workspaces.domain.WorkspaceDestroyerRepository
import com.lyra.app.workspaces.domain.WorkspaceFinderRepository
import com.lyra.app.workspaces.domain.WorkspaceId
import com.lyra.app.workspaces.domain.WorkspaceRepository
import com.lyra.app.workspaces.domain.exception.WorkspaceCollaboratorException
import com.lyra.app.workspaces.domain.exception.WorkspaceException
import com.lyra.app.workspaces.infrastructure.persistence.mapper.WorkspaceCollaboratorsMapper.toEntity
import com.lyra.app.workspaces.infrastructure.persistence.mapper.WorkspaceMapper.toDomain
import com.lyra.app.workspaces.infrastructure.persistence.mapper.WorkspaceMapper.toEntity
import com.lyra.app.workspaces.infrastructure.persistence.repository.WorkspaceCollaboratorsR2dbcRepository
import com.lyra.app.workspaces.infrastructure.persistence.repository.WorkspaceR2dbcRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Repository

// private const val DEFAULT_LIMIT = 20

@Repository
class WorkspaceStoreR2dbcRepository(
    private val workspaceRepository: WorkspaceR2dbcRepository,
    private val workspaceCollaboratorRepository: WorkspaceCollaboratorsR2dbcRepository,
) : WorkspaceRepository, WorkspaceFinderRepository, WorkspaceDestroyerRepository {
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
        log.debug(
            "Creating workspace collaborator with workspaceId: {} and userId: {}",
            workspace.id, workspace.userId,
        )
        try {
            val workspaceCollaboratorEntity = workspace.toEntity()
            workspaceCollaboratorRepository.upsert(workspaceCollaboratorEntity)
        } catch (e: DuplicateKeyException) {
            log.error("Workspace Collaborator already exists in the database: ${workspace.id.value}")
            throw WorkspaceCollaboratorException("Error creating workspace collaborator", e)
        }
    }

    /**
     * Updates a workspace.
     *
     * @param workspace The workspace to be updated.
     */
    override suspend fun update(workspace: Workspace) {
        log.debug("Updating workspace with id: {}", workspace.id)
        try {
            workspaceRepository.save(workspace.toEntity())
        } catch (e: DuplicateKeyException) {
            log.error("Workspace already exists in the database: {}", workspace.id.value)
            throw WorkspaceException("Error updating workspace", e)
        } catch (e: org.springframework.dao.TransientDataAccessResourceException) {
            log.error("Error updating form with id: ${workspace.id.value}")
            throw WorkspaceException("Error updating form because it does not exist", e)
        }
    }

    /**
     * Find a workspace by its unique identifier.
     *
     * @param id The unique identifier of the workspace.
     * @return The workspace with the given unique identifier.
     */
    override suspend fun findById(id: WorkspaceId): Workspace? {
        log.debug("Finding workspace with id: {}", id)
        return workspaceRepository.findById(id.value)?.toDomain()
    }

    /**
     * Find all workspaces.
     *
     * @return A list of all workspaces.
     */
    override suspend fun findAll(): List<Workspace> {
        log.debug("Finding all workspaces")
        return workspaceRepository.findAll().map { it.toDomain() }.toList()
    }

    /**
     * Deletes a workspace.
     *
     * @param id The workspace id.
     */
    override suspend fun delete(id: WorkspaceId) {
        log.debug("Deleting workspace with id: {}", id)
        workspaceRepository.deleteById(id.value)
    }

    companion object {
        private val log = LoggerFactory.getLogger(WorkspaceStoreR2dbcRepository::class.java)
    }
}
