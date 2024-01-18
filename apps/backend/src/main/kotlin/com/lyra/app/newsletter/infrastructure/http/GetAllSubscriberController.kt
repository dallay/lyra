package com.lyra.app.newsletter.infrastructure.http

import com.lyra.app.newsletter.application.search.all.SearchAllSubscribersQuery
import com.lyra.common.domain.bus.Mediator
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class GetAllSubscriberController(
    mediator: Mediator,
) : ApiController(mediator) {
    @Operation(summary = "Get all subscribers")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Success"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @GetMapping("/newsletter/subscribers")
    suspend fun getAll(): ResponseEntity<Any> {
        val response = ask(SearchAllSubscribersQuery())
        return ResponseEntity.ok(response)
    }
}
