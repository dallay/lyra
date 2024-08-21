package com.lyra

import ch.qos.logback.classic.Logger
import com.lyra.ApplicationStartupTraces.initApplication
import com.lyra.ApplicationStartupTraces.of
import com.lyra.app.TestAppender
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
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
        environment.setProperty("spring.application.name", "Lyra")
        Assertions.assertThat(of(environment)).contains("  Application 'Lyra' is running!")
    }

    @Test
    fun `init the application with incompatible profiles`() {
        val environment = MockEnvironment()
        environment.setActiveProfiles("prod", "dev")

        val logger = LoggerFactory.getLogger(ApplicationStartupTraces::class.java) as Logger
        val testAppender = TestAppender()
        logger.addAppender(testAppender)
        testAppender.start()

        initApplication(environment)

        val errorMessage = "It should not run with both the 'dev' and 'prod' profiles at the same time."
        val logEvent = testAppender.events.find { it.message.contains(errorMessage) }

        assertTrue(logEvent != null, "Expected log message not found.")
    }

    companion object {
        private val SEPARATOR = "-".repeat(58)
    }
}
