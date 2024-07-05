package com.lyra.app.team.infrastructure.http

import com.lyra.app.team.application.delete.DeleteTeamCommand
import com.lyra.common.domain.bus.Mediator
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class DeleteTeamController(mediator: Mediator) : ApiController(mediator) {

    @Operation(summary = "Delete a team")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Team deleted successfully"),
        ApiResponse(responseCode = "404", description = "Team not found"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @DeleteMapping("/$ENDPOINT_TEAM/{id}")
    suspend fun delete(@PathVariable id: String) {
        log.debug("Deleting team with id: $id")
        dispatch(DeleteTeamCommand(id))
    }

    companion object {
        private val log = LoggerFactory.getLogger(DeleteTeamController::class.java)
    }
}
