package com.lyra.app.workspaces.infrastructure.persistence

import com.lyra.UnitTest
import com.lyra.app.workspaces.domain.Workspace
import com.lyra.app.workspaces.domain.WorkspaceCollaborators
import com.lyra.app.workspaces.domain.exception.WorkspaceCollaboratorException
import com.lyra.app.workspaces.domain.exception.WorkspaceException
import com.lyra.app.workspaces.infrastructure.WorkspaceStub
import com.lyra.app.workspaces.infrastructure.persistence.mapper.WorkspaceMapper.toEntity
import com.lyra.app.workspaces.infrastructure.persistence.repository.WorkspaceCollaboratorsR2dbcRepository
import com.lyra.app.workspaces.infrastructure.persistence.repository.WorkspaceR2dbcRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.TransientDataAccessResourceException

@UnitTest
internal class WorkspaceStoreR2dbcRepositoryTest {
    private val workspaceRepository: WorkspaceR2dbcRepository = mockk()
    private val workspaceCollaboratorRepository: WorkspaceCollaboratorsR2dbcRepository = mockk()
    private val workspaceStoreR2dbcRepository =
        WorkspaceStoreR2dbcRepository(workspaceRepository, workspaceCollaboratorRepository)
    private lateinit var workspace: Workspace
    private lateinit var workspaceCollaborators: WorkspaceCollaborators

    @BeforeEach
    fun setUp() {
        workspace = WorkspaceStub.create()
        workspaceCollaborators = WorkspaceStub.createCollaborator()
        coEvery { workspaceRepository.save(any()) } returns workspace.toEntity()
        coEvery { workspaceCollaboratorRepository.upsert(any()) } returns 1
    }

    @Test
    fun `should create workspace`(): Unit = runBlocking {
        workspaceStoreR2dbcRepository.create(workspace)
        coEvery { workspaceRepository.save(any()) }
    }

    @Test
    fun `should create workspace collaborator`(): Unit = runBlocking {
        workspaceStoreR2dbcRepository.create(workspaceCollaborators)
        coEvery { workspaceCollaboratorRepository.upsert(any()) }
    }

    @Test
    fun `should handle duplicate workspace creation gracefully`(): Unit = runBlocking {
        coEvery { workspaceRepository.save(any()) } throws DuplicateKeyException("Duplicate key")
        assertThrows<WorkspaceException> {
            workspaceStoreR2dbcRepository.create(workspace)
        }
    }
    @Test
    fun `should handle duplicate workspace collaborator creation gracefully`(): Unit = runBlocking {
        coEvery { workspaceCollaboratorRepository.upsert(any()) } throws DuplicateKeyException("Duplicate key")
        assertThrows<WorkspaceCollaboratorException> {
            workspaceStoreR2dbcRepository.create(workspaceCollaborators)
        }
    }

    @Test
    fun `should update workspace`(): Unit = runBlocking {
        workspaceStoreR2dbcRepository.update(workspace)
        coEvery { workspaceRepository.save(any()) }
    }
    @Test
    fun `should handle unexpected error during workspace update`(): Unit = runBlocking {
        coEvery { workspaceRepository.save(any()) } throws RuntimeException("Unexpected error")
        assertThrows<RuntimeException> {
            workspaceStoreR2dbcRepository.update(workspace)
        }
    }

    @Test
    fun `should handle error when the form does not exist`(): Unit = runBlocking {
        coEvery { workspaceRepository.save(any()) } throws TransientDataAccessResourceException("Unexpected error")
        assertThrows<WorkspaceException> {
            workspaceStoreR2dbcRepository.update(workspace)
        }
    }

    @Test
    fun `should delete workspace`(): Unit = runBlocking {
        coEvery { workspaceRepository.deleteById(any()) } returns Unit
        workspaceStoreR2dbcRepository.delete(workspace.id)
    }

    @Test
    fun `should find workspace by id`(): Unit = runBlocking {
        coEvery { workspaceRepository.findById(any()) } returns workspace.toEntity()
        workspaceStoreR2dbcRepository.findById(workspace.id)
    }

    @Test
    fun `should find all workspaces`(): Unit = runBlocking {
        coEvery { workspaceRepository.findAll() } returns flowOf(workspace.toEntity())
        workspaceStoreR2dbcRepository.findAll()
    }

    @Test
    fun `should delete workspace collaborator`(): Unit = runBlocking {
        coEvery { workspaceCollaboratorRepository.delete(any(), any()) } returns 0
        workspaceStoreR2dbcRepository.removeCollaborator(workspace.id, workspaceCollaborators.userId)
    }
}
