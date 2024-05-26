package com.lyra.app.users.infrastructure.persistence.keycloak

import com.lyra.app.authentication.domain.Username
import com.lyra.app.config.InfrastructureTestContainers
import com.lyra.app.users.domain.User
import com.lyra.app.users.domain.UserCreator
import com.lyra.app.users.domain.UserStoreException
import com.lyra.common.domain.vo.email.Email
import io.kotest.common.runBlocking
import net.datafaker.Faker
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class KeycloakRepositoryIntegrationTest : InfrastructureTestContainers() {

    @Autowired
    private lateinit var userCreator: UserCreator

    private val faker = Faker()

    @Test
    fun `should create a new user`() = runBlocking {
        val user = User.create(
            email = faker.internet().emailAddress(),
            firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
        )

        val createdUser = userCreator.create(user)
        assertNotNull(createdUser)
        assertEquals(createdUser.email, user.email)
        assertEquals(createdUser.username, user.username)
        assertEquals(createdUser.name?.firstName ?: "", user.name?.firstName ?: "")
        assertEquals(createdUser.name?.lastName ?: "", user.name?.lastName ?: "")
    }

    @Test
    fun `should not create a new user with an existing email`() = runBlocking {
        val user = User.create(
            email = faker.internet().emailAddress(),
            firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
        )

        val createdUser = userCreator.create(user)
        assertNotNull(createdUser)
        assertEquals(createdUser.email, user.email)
        assertEquals(createdUser.username, user.username)
        assertEquals(createdUser.name?.firstName ?: "", user.name?.firstName ?: "")
        assertEquals(createdUser.name?.lastName ?: "", user.name?.lastName ?: "")

        val newUsername = Username(faker.internet().username())
        val duplicateUser = user.copy(username = newUsername)

        // Act & Assert (try to create duplicate user)
        val exception = assertThrows<UserStoreException> {
            userCreator.create(duplicateUser)
        }
        assertEquals(
            "User with email: ${user.email.value} or username: ${newUsername.value} already exists.",
            exception.message,
        )
    }

    @Test
    fun `should not create a new user with an existing username`() = runBlocking {
        val user = User.create(
            email = faker.internet().emailAddress(),
            firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
        )

        val createdUser = userCreator.create(user)
        assertNotNull(createdUser)
        assertEquals(createdUser.email, user.email)
        assertEquals(createdUser.username, user.username)
        assertEquals(createdUser.name?.firstName ?: "", user.name?.firstName ?: "")
        assertEquals(createdUser.name?.lastName ?: "", user.name?.lastName ?: "")

        val newEmail = Email("newuser@test.com")
        val duplicateUser = user.copy(email = newEmail)

        // Act & Assert (try to create duplicate user)
        val exception = assertThrows<UserStoreException> {
            userCreator.create(duplicateUser)
        }
        assertEquals(
            "User with email: ${newEmail.value} or username: ${user.username.value} already exists.",
            exception.message,
        )
    }

    @Test
    fun `should not create a new user with an existing email and username`() = runBlocking {
        val user = User.create(
            email = faker.internet().emailAddress(),
            firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
        )

        val createdUser = userCreator.create(user)
        assertNotNull(createdUser)
        assertEquals(createdUser.email, user.email)
        assertEquals(createdUser.username, user.username)
        assertEquals(createdUser.name?.firstName ?: "", user.name?.firstName ?: "")
        assertEquals(createdUser.name?.lastName ?: "", user.name?.lastName ?: "")

        val newUsername = Username(faker.internet().username())
        val newEmail = Email("ultra@gmail.com")
        var duplicateUser = user.copy(username = newUsername)

        // Act & Assert (try to create duplicate user)
        var exception = assertThrows<UserStoreException> {
            userCreator.create(duplicateUser)
        }
        assertEquals(
            "User with email: ${user.email.value} or username: ${newUsername.value} already exists.",
            exception.message,
        )

        duplicateUser = user.copy(email = newEmail)

        // Act & Assert (try to create duplicate user)
        exception = assertThrows<UserStoreException> {
            userCreator.create(duplicateUser)
        }
        assertEquals(
            "User with email: ${newEmail.value} or username: ${user.username.value} already exists.",
            exception.message,
        )
    }
}
