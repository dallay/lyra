package com.lyra.app.authentication.infrastructure

import com.lyra.app.authentication.domain.Role
import com.lyra.app.config.CucumberAuthenticationConfiguration
import io.cucumber.java.en.Given
import io.jsonwebtoken.Jwts
import java.time.Instant
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions

@Component
class AuthenticationSteps {

    private val users: Map<String, User> = UsersBuilder()
        .add("admin", Role.ADMIN)
        .add("user", Role.USER)
        .build()

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Given("I am logged in as {string}")
    fun authenticateUser(username: String) {
        val userToAuthenticate = users[username] ?: throw IllegalArgumentException(unknownUserMessage(username))

        val token = userToAuthenticate.token()

        webTestClient = webTestClient.mutate()
            .filter(
                ExchangeFilterFunctions
                    .basicAuthentication(username, token),
            )
            .build()
    }

    private fun unknownUserMessage(user: String): String = "Trying to authenticate an unknown user: $user"

    @Given("I am logged out")
    fun logout() {
        webTestClient = webTestClient.mutate()
            .filter(
                ExchangeFilterFunctions
                    .basicAuthentication("", ""),
            )
            .defaultHeader(HttpHeaders.AUTHORIZATION, "")
            .build()
    }

    private class UsersBuilder {
        private val users: MutableMap<String, User> = ConcurrentHashMap()

        fun add(username: String, vararg roles: Role): UsersBuilder {
            users[username] = User(username, roles)
            return this
        }

        fun build(): Map<String, User> = Collections.unmodifiableMap(users)
    }

    private class User(username: String, roles: Array<out Role>) {
        private val claims: Map<String, Any> = mapOf(
            "preferred_username" to username,
            "roles" to roles.map { it.key() },
        )

        fun token(): String {
            return Jwts.builder()
                .setSubject("authentication")
                .signWith(CucumberAuthenticationConfiguration.JWT_KEY)
                .setClaims(claims)
                .setExpiration(Date.from(Instant.now().plusSeconds(300)))
                .compact()
        }
    }
}
