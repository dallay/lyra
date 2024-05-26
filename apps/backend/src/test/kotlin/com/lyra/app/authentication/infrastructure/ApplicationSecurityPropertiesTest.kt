package com.lyra.app.authentication.infrastructure

import com.lyra.UnitTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.context.properties.bind.BindResult
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.mock.env.MockEnvironment

@UnitTest
internal class ApplicationSecurityPropertiesTest {

    private val environment: MockEnvironment = MockEnvironment()
        .withProperty("application.security.oauth2.clientId", "testClientId")
        .withProperty("application.security.oauth2.clientSecret", "testClientSecret")
        .withProperty("application.security.cors.allowedOrigins", "testAllowedOrigins")
        .withProperty("application.security.cors.allowedMethods", "testAllowedMethods")
        .withProperty("application.security.contentSecurityPolicy", "testContentSecurityPolicy")
    private val binder: Binder = Binder.get(environment)

    @Test
    fun `should bind oauth2 properties correctly`() {
        val result: BindResult<ApplicationSecurityProperties> =
            binder.bind("application.security", ApplicationSecurityProperties::class.java)

        val properties = result.get()

        assertEquals("testClientId", properties.oauth2.clientId)
        assertEquals("testClientSecret", properties.oauth2.clientSecret)
    }

    @Test
    fun `should bind cors properties correctly`() {
        val result: BindResult<ApplicationSecurityProperties> =
            binder.bind("application.security", ApplicationSecurityProperties::class.java)

        val properties = result.get()

        assertEquals(listOf("testAllowedOrigins"), properties.cors.allowedOrigins)
        assertEquals(listOf("testAllowedMethods"), properties.cors.allowedMethods)
    }

    @Test
    fun `should bind contentSecurityPolicy property correctly`() {
        val result: BindResult<ApplicationSecurityProperties> =
            binder.bind("application.security", ApplicationSecurityProperties::class.java)

        val properties = result.get()

        assertEquals("testContentSecurityPolicy", properties.contentSecurityPolicy)
    }
}
