package com.lyra.app.users.infrastructure.http

import com.lyra.app.users.application.response.UserResponse
import com.lyra.app.users.infrastructure.service.AccountResourceService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.security.Principal
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

/**
 * The [AccountResourceController] class is responsible for handling HTTP requests related to account information.
 * It is a Spring RestController with the base path*/
@RestController
@RequestMapping("/api", produces = ["application/vnd.api.v1+json"])
class AccountResourceController(private val accountResourceService: AccountResourceService) {
    /**
     * Gets the account information for the authenticated user.
     *
     * @param principal the Principal object representing the authenticated user.
     * @return a Mono object encapsulating the UserResponse containing the account information.
     */
    @Operation(summary = "Get account information")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Account information retrieved successfully"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @GetMapping("/account")
    fun getAccount(principal: Principal): Mono<UserResponse> {
        log.debug("Getting user account information")
        return accountResourceService.getAccount(principal as AbstractAuthenticationToken)
    }
    companion object {
        private val log = LoggerFactory.getLogger(AccountResourceController::class.java)
    }
}
