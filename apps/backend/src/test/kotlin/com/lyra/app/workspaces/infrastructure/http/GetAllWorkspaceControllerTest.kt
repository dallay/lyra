package com.lyra.app.workspaces.infrastructure.http

import com.lyra.UnitTest
import com.lyra.app.workspaces.application.WorkspaceResponses
import com.lyra.app.workspaces.application.find.all.AllWorkspaceQuery
import com.lyra.app.workspaces.domain.Workspace
import com.lyra.app.workspaces.infrastructure.WorkspaceStub
import com.lyra.common.domain.bus.Mediator
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

@UnitTest
internal class GetAllWorkspaceControllerTest {
    private val mediator = mockk<Mediator>()
    private val controller: GetAllWorkspaceController = GetAllWorkspaceController(mediator)
    private val webTestClient: WebTestClient = WebTestClient.bindToController(controller).build()
    private val workspaces: List<Workspace> = WorkspaceStub.dummyRandomWorkspaces(6)

    @BeforeEach
    fun setUp() {
        coEvery { mediator.send(any(AllWorkspaceQuery::class)) } returns WorkspaceResponses.from(workspaces)
    }
    @Test
    fun `should get all workspaces`() {
        webTestClient.get()
            .uri("/api/workspace")
            .exchange()
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.data.length()").isEqualTo(workspaces.size)
    }
}
