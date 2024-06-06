package com.lyra.app.workspaces.infrastructure.http

import com.lyra.app.workspaces.application.update.UpdateWorkspaceCommand
import com.lyra.app.workspaces.infrastructure.http.request.UpdateWorkspaceRequest
import com.lyra.common.domain.bus.Mediator
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * This class is a REST controller for updating workspaces.
 * It extends the ApiController class and uses the Mediator pattern for handling requests.
 *
 * @property mediator The mediator used for handling requests.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class UpdateWorkspaceController(
    private val mediator: Mediator,
) : ApiController(mediator) {

    /**
     * This method handles the PUT request for updating a workspace.
     * It validates the request body and dispatches an UpdateWorkspaceCommand.
     *
     * @param id The ID of the workspace to update.
     * @param request The request body containing the new workspace data.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @Operation(summary = "Update a workspace with the given data")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Updated workspace"),
        ApiResponse(responseCode = "400", description = "Bad request error (validation error)"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @PutMapping("/$ENDPOINT_WORKSPACE/update/{id}")
    suspend fun update(
        @PathVariable id: String,
        @Validated @RequestBody request: UpdateWorkspaceRequest
    ): ResponseEntity<String> {
        log.debug("Updating workspace with ID: {}", id)
        dispatch(
            UpdateWorkspaceCommand(
                id,
                request.name,
            ),
        )
        return ResponseEntity.ok("Workspace updated successfully")
    }

    companion object {
        private val log = LoggerFactory.getLogger(UpdateWorkspaceController::class.java)
    }
}
