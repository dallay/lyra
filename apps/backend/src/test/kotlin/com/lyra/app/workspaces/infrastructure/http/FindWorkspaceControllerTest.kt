package com.lyra.app.workspaces.infrastructure.http

import com.lyra.UnitTest
import com.lyra.app.workspaces.application.WorkspaceResponse
import com.lyra.app.workspaces.application.find.FindWorkspaceQuery
import com.lyra.app.workspaces.infrastructure.WorkspaceStub
import com.lyra.common.domain.bus.Mediator
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

@UnitTest
internal class FindWorkspaceControllerTest {
    private val mediator: Mediator = mockk()
    private lateinit var controller: FindWorkspaceController
    private lateinit var webTestClient: WebTestClient
    private val id = UUID.randomUUID().toString()

    @BeforeEach
    fun setup() {
        controller = FindWorkspaceController(mediator)
        webTestClient = WebTestClient.bindToController(controller).build()
    }

    @Test
    fun `should return workspace when workspace is found`() {
        val workspace = WorkspaceStub.create()
        val query = FindWorkspaceQuery(id)
        val response = WorkspaceResponse.from(workspace)
        coEvery { mediator.send(query) } returns response

        webTestClient.get()
            .uri("/api/workspace/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody(WorkspaceResponse::class.java)
            .isEqualTo(response)
        coEvery { mediator.send(query) }
    }
}
