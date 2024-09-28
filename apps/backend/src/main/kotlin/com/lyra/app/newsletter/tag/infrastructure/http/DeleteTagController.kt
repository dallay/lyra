package com.lyra.app.newsletter.tag.infrastructure.http

import com.lyra.app.AppConstants.Paths.API
import com.lyra.app.AppConstants.Paths.TAG
import com.lyra.app.newsletter.tag.application.delete.DeleteTagCommand
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

@RestController
@RequestMapping(value = [API], produces = ["application/vnd.api.v1+json"])
class DeleteTagController(
    mediator: Mediator,
) : ApiController(mediator) {
    @Operation(summary = "Delete a tag")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Tag deleted successfully"),
        ApiResponse(responseCode = "404", description = "Tag not found"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @DeleteMapping("$TAG/{tagId}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun delete(
        @PathVariable organizationId: String,
        @PathVariable tagId: String
    ) {
        log.debug("Deleting tag with id: {}", sanitizeAndJoinPathVariables(organizationId, tagId))
        dispatch(DeleteTagCommand(organizationId, tagId))
    }

    companion object {
        private val log = LoggerFactory.getLogger(DeleteTagController::class.java)
    }
}
