package com.lyra.app

import com.lyra.app.ApplicationStartupTraces.of
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.mock.env.MockEnvironment

@UnitTest
internal class ApplicationStartupTracesTest {
    @Test
    fun shouldBuildTraceForEmptyNonWebEnvironment() {
        Assertions.assertThat(of(MockEnvironment()))
            .contains("  Application is running!")
            .doesNotContain("Local")
            .doesNotContain("External")
            .doesNotContain("Profile(s)")
            .doesNotContain("Config Server:")
    }

    @Test
    fun shouldBuildTraceForEmptyWebEnvironment() {
        val environment = MockEnvironment()
        environment.setProperty("server.port", "80")
        Assertions.assertThat(of(environment))
            .contains("  Application is running!")
            .contains("  Local: \thttp://localhost:80/")
            .containsPattern("  External: \thttp://[^:]+:80/")
    }

    @Test
    fun shouldBuildTraceForApplicationWithWebConfiguration() {
        val environment = MockEnvironment()
        environment.setProperty("server.ssl.key-store", "key")
        environment.setProperty("server.port", "8080")
        environment.setProperty("server.servlet.context-path", "/custom-path")
        environment.setProperty("configserver.status", "config")
        environment.setActiveProfiles("local", "mongo")
        @Suppress("TrimMultilineRawString")
        Assertions.assertThat(of(environment))
            .contains("  Local: \thttps://localhost:8080/custom-path")
            .containsPattern("  External: \thttps://[^:]+:8080/custom-path")
            .contains("Profile(s): \tlocal, mongo")
            .contains(
                """$SEPARATOR
  Config Server: config
$SEPARATOR""",
            )
    }

    @Test
    fun shouldBuildTraceForEnvironmentWithApplicationName() {
        val environment = MockEnvironment()
        environment.setProperty("spring.application.name", "jhlite")
        Assertions.assertThat(of(environment)).contains("  Application 'jhlite' is running!")
    }

    companion object {
        private val SEPARATOR = "-".repeat(58)
    }
}
