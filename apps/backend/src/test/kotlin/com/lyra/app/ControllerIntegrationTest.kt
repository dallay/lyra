package com.lyra.app

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.junit.jupiter.Testcontainers

@IntegrationTest
@AutoConfigureWebTestClient
@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [LyraApplication::class],
)
abstract class ControllerIntegrationTest {
    @Autowired
    protected lateinit var webTestClient: WebTestClient
}
