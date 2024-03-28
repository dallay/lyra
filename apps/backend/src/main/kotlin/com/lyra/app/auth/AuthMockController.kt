package com.lyra.app.auth

import com.lyra.common.domain.Generated
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Generated
data class SignInRequest(
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String,
)

@Generated
data class SignInResponse(
    val message: String,
    val accessToken: String,
    val refreshToken: String,
    val otpEnabled: Boolean,
    val otpVerified: Boolean,
)

@Generated
data class User(
    val message: String,
    @Suppress("ConstructorParameterNaming")
    val _id: String,
    val username: String,
    val email: String,
    val fullName: String,
    val status: Boolean,
    val otpEnabled: Boolean,
    val otpVerified: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val role: String,
    val permissions: List<Permission>
)

@Generated
data class Permission(
    val resource: String,
    val action: String
)
@Generated
@Suppress("MaximumLineLength", "MaxLineLength")
@RestController
@RequestMapping(value = ["/api/auth"], produces = ["application/vnd.api.v1+json"])
class AuthController {
    @PostMapping("/sign-in")
    fun signIn(@Valid @RequestBody request: SignInRequest): SignInResponse {
        logger.info("User ${request.username} signed in")
        return SignInResponse(
            message = "User ${request.username} signed in",
            accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InNoeWFtLmNoZW4iLCJpYXQiOjE2NzQwOTMxMzQsImV4cCI6MTY3NDA5NDMzNH0.Qd2XARFcMVjdITuAZKISU-zNIxrx1O5sfrFVci7LkQI",
            refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1dWlkIjoiMmZlNzdjMzktNDRlMi00NDRhLTliN2QtMmYyOTk2YjYxMjc2IiwiaWF0IjoxNjc0MDkzMTM0LCJleHAiOjE2NzQxMzYzMzR9.67x8vrDc1K2qgtl6M4Kys2fP83_lr4lF8Ohiw5MqmUw",
            otpEnabled = false,
            otpVerified = false,
        )
    }

    @GetMapping("/user")
    fun user(): User {
        logger.info("User requested")
        return User(
            message = "OK",
            _id = "6462f6b2c0ae46c0c9fbca5e",
            username = "yap",
            email = "yap@lyra.com",
            fullName = "Yuniel Acosta",
            status = true,
            otpEnabled = false,
            otpVerified = false,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            role = "admin",
            permissions = listOf(
                Permission(
                    resource = "*",
                    action = "*",
                ),
            ),
        )
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AuthController::class.java)
    }
}
