package com.lyra.app.linkpreview.infrastructure.http

import com.lyra.ControllerIntegrationTest
import com.lyra.app.linkpreview.application.LinkPreviewResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LinkPreviewControllerIntegrationTest : ControllerIntegrationTest() {
    @Test
    fun returnsLinkPreviewSuccessfully() {
        val url = "https://www.yunielacosta.com/"
        webTestClient.run {
            get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path("/api/linkpreview")
                        .queryParam("url", url)
                        .build()
                }
                .exchange()
                .expectStatus().isOk
                .expectBody(LinkPreviewResponse::class.java)
                .consumeWith { response ->
                    val result = response.responseBody
                    println(result)
                    assertEquals("Yuniel Acosta | Software Engineer", result?.title)
                    assertEquals(
                        "Programmer, writer, technology and science enthusiast, " +
                            "specialized in building web applications. Interested in Vue, " +
                            "Typescript, Node.js, Java/Kotlin and Spring Boot.",
                        result?.description,
                    )
                    assertEquals("/uploads/me.webp", result?.imageUrl)
                    assertEquals("https://www.yunielacosta.com/", result?.url)
                }
        }
    }
}
