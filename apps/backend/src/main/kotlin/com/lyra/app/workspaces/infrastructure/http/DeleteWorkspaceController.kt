package com.lyra.app.workspaces.infrastructure.http

import com.lyra.app.workspaces.application.delete.DeleteWorkspaceCommand
import com.lyra.common.domain.bus.Mediator
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * This class is a REST controller that handles HTTP requests related to workspace deletion.
 * It extends the ApiController class and uses the Mediator pattern for handling commands.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class DeleteWorkspaceController(
    mediator: Mediator,
) : ApiController(mediator) {

    /**
     * This function handles the DELETE HTTP request for deleting a workspace.
     * It uses the path variable 'id' to identify the workspace to be deleted.
     * The function is a suspend function, meaning it is designed to be used with Kotlin coroutines.
     * It dispatches a DeleteWorkspaceCommand with the provided id.
     *
     * @param id The id of the workspace to be deleted.
     * @return The result of the DeleteWorkspaceCommand dispatch.
     */
    @Operation(summary = "Delete a workspace")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Workspace deleted successfully"),
        ApiResponse(responseCode = "404", description = "Workspace not found"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @DeleteMapping("/$ENDPOINT_WORKSPACE/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun delete(
        @PathVariable id: String
    ) {
        log.debug("Deleting workspace with id: $id")
        dispatch(DeleteWorkspaceCommand(id))
    }

    companion object {
        private val log = LoggerFactory.getLogger(DeleteWorkspaceController::class.java)
    }
}
