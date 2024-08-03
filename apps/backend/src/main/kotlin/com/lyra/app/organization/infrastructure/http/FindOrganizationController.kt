package com.lyra.app.organization.infrastructure.http

import com.lyra.app.organization.application.find.FindOrganizationQuery
import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.query.Response
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * This class is a REST controller that handles HTTP requests related to finding an organization.
 * It extends the ApiController class and uses the Mediator pattern for handling queries.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class FindOrganizationController(
    mediator: Mediator,
) : ApiController(mediator) {

    /**
     * This function handles the GET HTTP request for finding an organization.
     * It uses the path variable 'id' to identify the organization to be found.
     * The function is a suspend function, meaning it is designed to be used with Kotlin coroutines.
     * It dispatches a FindOrganizationQuery with the provided id.
     * The function returns the response from the query.
     * @param id The id of the organization to be found.
     * @return The result of the FindOrganizationQuery dispatch.
     */
    @Operation(summary = "Find an organization by ID")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Found organization"),
        ApiResponse(responseCode = "404", description = "Organization not found"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @GetMapping("/$ENDPOINT_ORGANIZATION/{id}")
    suspend fun find(@PathVariable id: String): Response {
        log.debug("Finding organization")
        val query = FindOrganizationQuery(id)
        val response = ask(query)
        return response
    }

    companion object {
        private val log = LoggerFactory.getLogger(FindOrganizationController::class.java)
    }
}
