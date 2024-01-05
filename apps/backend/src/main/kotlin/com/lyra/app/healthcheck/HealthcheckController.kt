package com.lyra.app.healthcheck

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

/**
 * Controller class for performing health checks.
 * This class handles the '/health-check' endpoint.
 * The class contains a method to perform a health check, which returns a Mono of String.
 * The method returns a Mono of String with the value 'OK'.
 * @created 8/7/23
 */
@RestController
@RequestMapping("/api")
class HealthcheckController {

    /**
     * Checks the health status of the application.
     *
     * @return a Mono emitting a string "OK" indicating that the application is healthy.
     */
    @Operation(summary = "Health check endpoint")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @GetMapping("/health-check")
    fun healthcheck(): Mono<String> = Mono.just("OK")
}
