package com.lyra.app.linkpreview.infrastructure.http

import com.lyra.app.AppConstants.Paths.API
import com.lyra.app.linkpreview.application.GetLinkPreviewQuery
import com.lyra.app.linkpreview.application.LinkPreviewResponse
import com.lyra.common.domain.bus.Mediator
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.constraints.NotEmpty
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Controller for handling link preview requests.
 *
 * @created 16/10/24
 */
@RestController
@RequestMapping(value = [API], produces = ["application/vnd.api.v1+json"])
class LinkPreviewController(
    mediator: Mediator
) : ApiController(mediator) {

    /**
     * Endpoint to get a link preview.
     *
     * @param url the URL to fetch the link preview for
     * @return the link preview response
     */
    @Operation(summary = "Get link preview")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Success"),
        ApiResponse(responseCode = "400", description = "Bad Request"),
        ApiResponse(responseCode = "500", description = "Internal Server Error"),
    )
    @GetMapping("/linkpreview")
    suspend fun getLinkPreview(
        @Parameter(description = "URL to fetch the link preview for", required = true)
        @RequestParam("url") @NotEmpty url: String,
    ): ResponseEntity<LinkPreviewResponse> {
        log.debug("Get link preview for url: {}", sanitizeAndJoinPathVariables(url.toString()))
        val response = ask(
            GetLinkPreviewQuery(url),
        )
        return ResponseEntity.ok(response)
    }

    companion object {
        private val log = LoggerFactory.getLogger(LinkPreviewController::class.java)
    }
}
