package com.lyra.app.authentication.application.logout

import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 * This service class is responsible for logging out a user.
 * It uses an instance of [UserLogoutService] to perform the logout operation.
 *
 * @property userLogoutService An instance of [UserLogoutService] used to log out the user.
 */
@Service
class UserLogoutCommandHandler(private val userLogoutService: UserLogoutService) : CommandHandler<UserLogoutCommand> {
    override suspend fun handle(command: UserLogoutCommand) {
        log.debug("Logging out user")
        userLogoutService.logout(command.refreshToken)
    }
    companion object {
        private val log = LoggerFactory.getLogger(UserLogoutCommandHandler::class.java)
    }
}
