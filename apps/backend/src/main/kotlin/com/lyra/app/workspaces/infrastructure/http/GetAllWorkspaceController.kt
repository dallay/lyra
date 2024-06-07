package com.lyra.app.workspaces.infrastructure.http

import com.lyra.app.workspaces.application.find.all.AllWorkspaceQuery
import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.query.Response
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

/**
 * This controller handles the HTTP requests related to getting all workspaces.
 *
 * @property mediator The mediator used to handle queries.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class GetAllWorkspaceController(
    mediator: Mediator
) : ApiController(mediator) {

    /**
     * This function handles the GET request to get all workspaces.
     *
     * It logs the action and then uses the mediator to ask for all workspaces.
     * It then returns the response from the mediator.
     *
     * @return A Response object containing the result of the query.
     */
    @Operation(summary = "Get all workspaces")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Success"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @GetMapping(ENDPOINT_WORKSPACE)
    @ResponseBody
    suspend fun findAll(): Response {
        log.debug("Get All workspaces")

        val response = ask(
            AllWorkspaceQuery(),
        )
        return response
    }

    companion object {
        private val log = LoggerFactory.getLogger(GetAllWorkspaceController::class.java)
    }
}
