package com.lyra.app.linkpreview.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.app.linkpreview.application.GetLinkPreviewQuery
import com.lyra.app.linkpreview.application.LinkPreviewResponse
import io.mockk.coEvery
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

internal class LinkPreviewControllerTest : ControllerTest() {

    private val controller = LinkPreviewController(mediator)
    override lateinit var webTestClient: WebTestClient
    @BeforeEach
    override fun setUp() {
        webTestClient = WebTestClient.bindToController(controller).build()
    }
    @Test
    fun returnsLinkPreviewSuccessfully() = runBlocking {
        val url = "https://example.com"
        val response =
            LinkPreviewResponse(
                "Example Title",
                "Example Description", "https://example.com/image.jpg", "https://example.com",
            )
        coEvery { mediator.send(GetLinkPreviewQuery(url)) } returns response

        val result = webTestClient.get()
            .uri("/api/linkpreview?url=$url")
            .exchange()
            .expectStatus().isOk
            .expectBody(LinkPreviewResponse::class.java)
            .returnResult()
            .responseBody

        assertEquals(response, result)
    }

    @Test
    fun `returns Bad Request When Url Is Empty`(): Unit = runBlocking {
        val url = ""
        val response =
            LinkPreviewResponse(
                "Example Title",
                "Example Description", "https://example.com/image.jpg", "https://example.com",
            )
        coEvery { mediator.send(GetLinkPreviewQuery(url)) } returns response

        webTestClient.get()
            .uri("/api/linkpreview?url=$url")
            .exchange()
            .expectStatus().isBadRequest
    }
}
