package com.lyra.app.users.infrastructure.http.request

import com.lyra.app.users.application.command.RegisterUserCommand
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * Represents a request to register a user.
 *
 * This class is used to encapsulate the required data for registering a user.
 * It has properties for username, email, password, firstname, and lastname.
 *
 *
 * @created 2/7/23
 * @property email The email of the user. It must not be blank, must be a valid email address,
 * and must be less than 255 characters.
 * @property password The password of the user. It must not be blank and must be between 8 and 100 characters.
 * @property firstname The firstname of the user. It must not be blank and must be between 3 and 100 characters.
 * @property lastname The lastname of the user. It must not be blank and must be between 3 and 100 characters.
 */
data class RegisterUserRequest(
    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Email must be valid")
    @field:Size(max = 255, message = "Email must be less than 255 characters")
    val email: String,
    @field:NotBlank(message = "Password cannot be blank")
    @field:Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    val password: String,
    @field:NotBlank(message = "Firstname cannot be blank")
    @field:Size(min = 3, max = 100, message = "Firstname must be between 3 and 100 characters")
    val firstname: String,
    @field:NotBlank(message = "Lastname cannot be blank")
    @field:Size(min = 3, max = 100, message = "Lastname must be between 3 and 100 characters")
    val lastname: String
) {
    /**
     * Converts the current object to a RegisterUserCommand object.
     *
     * @return The converted RegisterUserCommand object.
     * @see RegisterUserCommand for more information about the RegisterUserCommand object.
     */
    fun toRegisterUserCommand(): RegisterUserCommand {
        return RegisterUserCommand(
            email,
            password,
            firstname,
            lastname,
        )
    }
}
