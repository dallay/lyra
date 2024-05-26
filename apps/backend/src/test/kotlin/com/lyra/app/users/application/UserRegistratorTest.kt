package com.lyra.app.users.application

import com.lyra.UnitTest
import com.lyra.app.users.application.command.RegisterUserCommand
import com.lyra.app.users.application.response.UserResponse
import com.lyra.app.users.domain.ApiDataResponse
import com.lyra.app.users.domain.ApiResponseStatus
import com.lyra.app.users.domain.event.UserCreatedEvent
import com.lyra.common.domain.vo.credential.Credential
import io.kotest.common.runBlocking
import net.datafaker.Faker
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

@UnitTest
class UserRegistratorTest {
    private val userRepository = InMemoryUserRepository()
    private val eventPublisher = InMemoryEventPublisher<UserCreatedEvent>()
    private val userRegistrator = UserRegistrator(userRepository, eventPublisher)
    private val faker = Faker()

    @Test
    fun `should register new user`() = runBlocking {
        val registerUserCommand = createRegisterUserCommand()

        val result: ApiDataResponse<UserResponse> =
            userRegistrator.registerNewUser(registerUserCommand)

        val data = result.data
        assertNotNull(data)
        assertEquals(registerUserCommand.email, data?.username)
        assertEquals(registerUserCommand.email, data?.email)
        assertEquals(registerUserCommand.firstname, data?.firstname)
        assertEquals(registerUserCommand.lastname, data?.lastname)
    }

    @Test
    fun `should not register new user with wrong email`() = runBlocking {
        val invalidEmail = "test"
        val registerUserCommand = createRegisterUserCommand(email = invalidEmail)

        val result: ApiDataResponse<UserResponse> =
            userRegistrator.registerNewUser(registerUserCommand)

        assertNotNull(result)
        assertEquals(result.status, ApiResponseStatus.FAILURE)
        assertNull(result.data)
        assertNotNull(result.error)
        assertEquals("The email <$invalidEmail> is not valid", result.error)
    }

    @Test
    fun `should not register new user with wrong password`() = runBlocking {
        val invalidPassword = "ab@W"
        val registerUserCommand = createRegisterUserCommand(password = invalidPassword)

        val result: ApiDataResponse<UserResponse> =
            userRegistrator.registerNewUser(registerUserCommand)

        assertNotNull(result)
        assertEquals(result.status, ApiResponseStatus.FAILURE)
        assertNull(result.data)
        assertNotNull(result.error)
        assertEquals("Credential value must be at least 8 characters", result.error)
    }

    @Test
    fun `should not register new user with wrong lastname`() = runBlocking {
        val charUppercase = 'A'..'Z'
        val charLowercase = 'a'..'z'
        val invalidLastname = (charUppercase + charLowercase).shuffled().joinToString("").repeat(4)
        val registerUserCommand = createRegisterUserCommand(lastname = invalidLastname)

        val result: ApiDataResponse<UserResponse> =
            userRegistrator.registerNewUser(registerUserCommand)

        assertNotNull(result)
        assertEquals(result.status, ApiResponseStatus.FAILURE)
        assertNull(result.data)
        assertNotNull(result.error)
        assertEquals("The last name <$invalidLastname> is not valid", result.error)
    }

    @Test
    fun `should not register new user with existing email`() = runBlocking {
        val registerUserCommand = createRegisterUserCommand(email = "test@google.com")

        var result: ApiDataResponse<UserResponse> =
            userRegistrator.registerNewUser(registerUserCommand)

        val data = result.data
        assertNotNull(data)
        assertEquals(registerUserCommand.email, data?.username)
        assertEquals(registerUserCommand.email, data?.email)
        assertEquals(registerUserCommand.firstname, data?.firstname)
        assertEquals(registerUserCommand.lastname, data?.lastname)

        val registerUserCommand2 = createRegisterUserCommand(email = "test@google.com")

        result = userRegistrator.registerNewUser(registerUserCommand2)

        assertNotNull(result)
        assertEquals(result.status, ApiResponseStatus.FAILURE)
        assertNull(result.data)
        assertNotNull(result.error)
        assertEquals("User with email: test@google.com or username: test@google.com already exists.", result.error)
    }

    @Test
    fun `should produce user registered event`() = runBlocking {
        val registerUserCommand = createRegisterUserCommand()

        val result: ApiDataResponse<UserResponse> =
            userRegistrator.registerNewUser(registerUserCommand)

        val data = result.data
        assertNotNull(data)
        assertEquals(registerUserCommand.email, data?.username)
        assertEquals(registerUserCommand.email, data?.email)
        assertEquals(registerUserCommand.firstname, data?.firstname)
        assertEquals(registerUserCommand.lastname, data?.lastname)

        val event = eventPublisher.getEvents().first()
        assertNotNull(event)
        assertEquals(UserCreatedEvent::class.java, event::class.java)
        assertEquals(registerUserCommand.email, event.username)
        assertEquals(registerUserCommand.email, event.email)
        assertEquals(registerUserCommand.firstname, event.firstname)
        assertEquals(registerUserCommand.lastname, event.lastname)
    }

    private fun createRegisterUserCommand(
        email: String = faker.internet().emailAddress(),
        password: String = Credential.generateRandomCredentialPassword(),
        firstname: String = faker.name().firstName(),
        lastname: String = faker.name().lastName()
    ) = RegisterUserCommand(
        email = email,
        password = password,
        firstname = firstname,
        lastname = lastname,
    )
}
