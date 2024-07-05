package com.lyra.app.organization.infrastructure.http

import com.lyra.app.organization.application.find.all.AllOrganizationQuery
import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.query.QueryResponse
import com.lyra.common.domain.bus.query.Response
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

/**
 * This controller handles the HTTP requests related to getting all organizations.
 *
 * @property mediator The mediator used to handle queries.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class GetAllOrganizationController(
    mediator: Mediator
) : ApiController(mediator) {

    /**
     * This function handles the GET request to get all organizations.
     *
     * It logs the action and then uses the mediator to ask for all organizations.
     * It then returns the response from the mediator.
     *
     * @return A Response object containing the result of the query.
     */
    @Operation(summary = "Get all organizations")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Success"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @GetMapping(ENDPOINT_ORGANIZATION)
    @ResponseBody
    suspend fun findAll(): Response {
        val authentication = authentication()
        val jwt = authentication?.principal as? Jwt
        val userId = jwt?.claims?.get("sub") as? String
        log.debug("Get All organizations for user: {}", userId)
        if (userId == null) {
            return QueryResponse("User not found")
        }
        val response = ask(
            AllOrganizationQuery(userId),
        )
        return response
    }

    companion object {
        private val log = LoggerFactory.getLogger(GetAllOrganizationController::class.java)
    }
}
