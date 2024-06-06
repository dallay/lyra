package com.lyra.app.workspaces.application.find.all

import com.lyra.UnitTest
import com.lyra.app.workspaces.domain.WorkspaceFinderRepository
import com.lyra.app.workspaces.infrastructure.WorkspaceStub
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class AllWorkspaceQueryHandlerTest {
    private val repository = mockkClass(WorkspaceFinderRepository::class)
    private val finder = AllWorkspaceFinder(repository)
    private val handler = AllWorkspaceQueryHandler(finder)
    private val workspaces = WorkspaceStub.dummyRandomWorkspaces(6)

    @BeforeEach
    fun setUp() {
        coEvery { repository.findAll() } returns workspaces
    }

    @Test
    fun `should find all workspaces`() = runBlocking {
        val query = AllWorkspaceQuery()
        val response = handler.handle(query)
        assertEquals(workspaces.size, response.data.size)
    }
}
