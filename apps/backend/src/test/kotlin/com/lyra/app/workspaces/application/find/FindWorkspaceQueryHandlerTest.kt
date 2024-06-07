package com.lyra.app.workspaces.application.find

import com.lyra.UnitTest
import com.lyra.app.workspaces.application.WorkspaceResponse
import com.lyra.app.workspaces.domain.WorkspaceId
import com.lyra.app.workspaces.domain.exception.WorkspaceNotFoundException
import com.lyra.app.workspaces.infrastructure.WorkspaceStub
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class FindWorkspaceQueryHandlerTest {

    private lateinit var workspaceFinder: WorkspaceFinder
    private lateinit var findWorkspaceQueryHandler: FindWorkspaceQueryHandler

    @BeforeEach
    fun setup() {
        workspaceFinder = mockk<WorkspaceFinder>()
        findWorkspaceQueryHandler = FindWorkspaceQueryHandler(workspaceFinder)
    }

    @Test
    fun `should return workspace response when workspace is found`() = runBlocking {
        val id = UUID.randomUUID().toString()
        val workspaceId = WorkspaceId(id)
        val workspace = WorkspaceStub.create()
        val workspaceResponse = WorkspaceResponse.from(workspace)
        coEvery { workspaceFinder.find(workspaceId) } returns workspace

        val result = findWorkspaceQueryHandler.handle(FindWorkspaceQuery(id))

        assertEquals(workspaceResponse, result)
    }

    @Test
    fun `should throw exception when workspace is not found`() {
        val id = UUID.randomUUID().toString()
        val workspaceId = WorkspaceId(id)
        coEvery { workspaceFinder.find(workspaceId) } returns null

        assertThrows(WorkspaceNotFoundException::class.java) {
            runBlocking {
                findWorkspaceQueryHandler.handle(FindWorkspaceQuery(id))
            }
        }
    }
}
