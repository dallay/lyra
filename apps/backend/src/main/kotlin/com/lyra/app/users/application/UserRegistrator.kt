package com.lyra.app.users.application

import com.lyra.app.users.application.command.RegisterUserCommand
import com.lyra.app.users.application.response.UserResponse
import com.lyra.app.users.domain.ApiDataResponse
import com.lyra.app.users.domain.User
import com.lyra.app.users.domain.UserCreator
import com.lyra.app.users.domain.event.UserCreatedEvent
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.event.EventBroadcaster
import com.lyra.common.domain.bus.event.EventPublisher
import com.lyra.common.domain.error.BusinessRuleValidationException
import org.apache.commons.text.StringEscapeUtils
import org.slf4j.LoggerFactory

@Service
class UserRegistrator(
    private val userCreator: UserCreator,
    eventPublisher: EventPublisher<UserCreatedEvent>
) {
    private val eventPublisher = EventBroadcaster<UserCreatedEvent>()

    init {
        this.eventPublisher.use(eventPublisher)
    }

    suspend fun registerNewUser(registerUserCommand: RegisterUserCommand): ApiDataResponse<UserResponse> {
        log.info(
            "Registering new user with email: {}",
            StringEscapeUtils.escapeJava(registerUserCommand.email),
        )
        return try {
            val user = registerUserCommand.toUser()
            val createdUser = userCreator.create(user)
            publishUserCreatedEvent(createdUser)

            val userResponse = UserResponse(
                createdUser.username.value,
                createdUser.email.value,
                createdUser.name?.firstName?.value,
                createdUser.name?.lastName?.value,
            )

            ApiDataResponse.success(userResponse)
        } catch (e: BusinessRuleValidationException) {
            log.error("Failed to register new user", e)
            ApiDataResponse.failure(e.message)
        } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
            log.error("Failed to register new user", e)
            ApiDataResponse.failure("Failed to register new user. Please try again.")
        }
    }

    private suspend fun publishUserCreatedEvent(user: User) {
        eventPublisher.publish(
            UserCreatedEvent(
                userId = user.id.value.toString(),
                email = user.email.value,
                username = user.username.value,
                firstname = user.name?.firstName?.value,
                lastname = user.name?.lastName?.value,
            ),
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(UserRegistrator::class.java)
    }
}
