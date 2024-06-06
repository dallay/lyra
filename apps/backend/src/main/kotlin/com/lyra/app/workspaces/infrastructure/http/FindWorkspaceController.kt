package com.lyra.app.workspaces.infrastructure.http

import com.lyra.app.workspaces.application.find.FindWorkspaceQuery
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

@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class FindWorkspaceController(
    mediator: Mediator,
) : ApiController(mediator) {
    @Operation(summary = "Find a workspace by ID")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Found workspace"),
        ApiResponse(responseCode = "404", description = "Workspace not found"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @GetMapping("/$ENDPOINT_WORKSPACE/{id}")
    suspend fun find(@PathVariable id: String): Response {
        log.debug("Finding workspace")
        val query = FindWorkspaceQuery(id)
        val response = ask(query)
        return response
    }

    companion object {
        private val log = LoggerFactory.getLogger(FindWorkspaceController::class.java)
    }
}
