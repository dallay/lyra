package com.lyra.app.workspaces.infrastructure.http

import com.lyra.app.workspaces.application.collaborator.AddWorkspaceCollaboratorCommand
import com.lyra.app.workspaces.infrastructure.http.request.AddWorkspaceCollaboratorRequest
import com.lyra.common.domain.bus.Mediator
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.net.URI
import java.time.LocalDateTime
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * This controller handles the addition of a collaborator to a workspace.
 *
 * @property mediator The [Mediator] used to dispatch commands.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class AddWorkspaceCollaboratorController(
    private val mediator: Mediator,
) : ApiController(mediator) {

    /**
     * This method handles the PUT request to add a collaborator to a workspace.
     *
     * @param id The ID of the workspace.
     * @param request The request body containing the details of the collaborator to be added.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @Operation(summary = "Create or add a workspace collaborator with the given data")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Created"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @PutMapping("/workspace/collaborator")
    suspend fun create(
        @Validated @RequestBody request: AddWorkspaceCollaboratorRequest
    ): ResponseEntity<String> {
        log.debug(
            "Creating Workspace Collaborator with workspaceId: {} and userId: {}",
            request.workspaceId, request.userId,
        )
        dispatch(
            AddWorkspaceCollaboratorCommand(
                request.workspaceId,
                request.userId,
                request.role,
                LocalDateTime.now().toString(),
            ),
        )
        return ResponseEntity.created(URI.create("/api/workspace/collaborator")).build()
    }
    companion object {
        private val log = LoggerFactory.getLogger(AddWorkspaceCollaboratorController::class.java)
    }
}
