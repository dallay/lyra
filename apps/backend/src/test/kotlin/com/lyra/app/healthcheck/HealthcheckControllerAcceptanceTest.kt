package com.lyra.app.healthcheck

import com.lyra.IntegrationTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.test.web.reactive.server.WebTestClient

@IntegrationTest
@AutoConfigureWebTestClient
class HealthcheckControllerAcceptanceTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `should successfully access healthcheck`() {
        webTestClient.get().uri("/api/health-check")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .consumeWith { response ->
                Assertions.assertEquals("OK", response.responseBody)
            }
    }
}
