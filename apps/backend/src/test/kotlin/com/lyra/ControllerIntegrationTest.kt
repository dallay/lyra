package com.lyra

import com.lyra.app.config.InfrastructureTestContainers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@AutoConfigureWebTestClient
@ActiveProfiles("test")
abstract class ControllerIntegrationTest : InfrastructureTestContainers() {
    @Autowired
    protected lateinit var webTestClient: WebTestClient
}
