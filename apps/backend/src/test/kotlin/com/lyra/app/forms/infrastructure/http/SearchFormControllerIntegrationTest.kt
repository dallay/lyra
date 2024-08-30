package com.lyra.app.forms.infrastructure.http

import com.lyra.ControllerIntegrationTest
import com.lyra.app.forms.application.FormResponse
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.core.ParameterizedTypeReference
import org.springframework.test.context.jdbc.Sql

internal class SearchFormControllerIntegrationTest : ControllerIntegrationTest() {
    private val organizationId = "27172d5a-b88e-451c-9787-312706f4570d"
    private val typeRef =
        object : ParameterizedTypeReference<CursorPageResponse<FormResponse>>() {}

    @Test
    @Sql(
        "/db/form/form-batch.sql",
    )
    @Sql(
        "/db/form/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should search all forms`() {
        webTestClient.run {
            get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path("/api/organization/$organizationId/form")
                        .queryParam("size", 10)
                        .build()
                }
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isArray
                .jsonPath("$.data.length()").isEqualTo(5) // 5 forms in the batch script
                .jsonPath("$.nextPageCursor").doesNotExist()
                .jsonPath("$.data[0].id").isEqualTo("1659d4ae-402a-4172-bf8b-0a5c54255587")
                .jsonPath("$.data[0].name").isEqualTo("Programming newsletter")
                .jsonPath("$.data[0].header").isEqualTo("Astrum's Newsletter")
                .jsonPath("$.data[0].description").isEqualTo("The best programming newsletter")
                .jsonPath("$.data[0].inputPlaceholder").isEqualTo("Enter your email")
                .jsonPath("$.data[0].buttonText").isEqualTo("Subscribe")
                .jsonPath("$.data[0].buttonColor").isEqualTo("#2C81E5")
                .jsonPath("$.data[0].backgroundColor").isEqualTo("#DFD150")
                .jsonPath("$.data[0].textColor").isEqualTo("#222222")
                .jsonPath("$.data[0].buttonTextColor").isEqualTo("#FFFFFF")
                .jsonPath("$.data[0].createdAt").isEqualTo("2024-04-21T19:56:07.632")
                .jsonPath("$.data[0].updatedAt").isEqualTo("2024-04-21T19:56:07.711")
                .consumeWith { println(it) }
        }
    }

    @Test
    @Sql(
        "/db/form/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get empty list if no forms`() {
        webTestClient.run {
            get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path("/api/organization/$organizationId/form")
                        .queryParam("size", 10)
                        .build()
                }
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isArray
                .jsonPath("$.data.length()").isEqualTo(0)
                .jsonPath("$.nextPageCursor").doesNotExist()
        }
    }

    @Test
    @Sql(
        "/db/form/form-batch.sql",
    )
    @Sql(
        "/db/form/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get all forms with filtter`() {
        webTestClient.run {
            get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path("/api/organization/$organizationId/form")
                        .queryParam("size", 10)
                        .queryParam("filter[name]", listOf("eq:Programming newsletter"))
                        .build()
                }
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isArray
                .jsonPath("$.data.length()").isEqualTo(1)
                .jsonPath("$.nextPageCursor").doesNotExist()
                .jsonPath("$.data[0].id").isEqualTo("1659d4ae-402a-4172-bf8b-0a5c54255587")
                .jsonPath("$.data[0].name").isEqualTo("Programming newsletter")
                .jsonPath("$.data[0].header").isEqualTo("Astrum's Newsletter")
                .jsonPath("$.data[0].description").isEqualTo("The best programming newsletter")
                .jsonPath("$.data[0].inputPlaceholder").isEqualTo("Enter your email")
                .jsonPath("$.data[0].buttonText").isEqualTo("Subscribe")
                .jsonPath("$.data[0].buttonColor").isEqualTo("#2C81E5")
                .jsonPath("$.data[0].backgroundColor").isEqualTo("#DFD150")
                .jsonPath("$.data[0].textColor").isEqualTo("#222222")
                .jsonPath("$.data[0].buttonTextColor").isEqualTo("#FFFFFF")
                .jsonPath("$.data[0].createdAt").isEqualTo("2024-04-21T19:56:07.632")
                .jsonPath("$.data[0].updatedAt").isEqualTo("2024-04-21T19:56:07.711")
        }
    }

    @Test
    @Sql(
        "/db/form/form-batch.sql",
    )
    @Sql(
        "/db/form/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get all forms with search`() {
        webTestClient.run {
            get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path("/api/organization/$organizationId/form")
                        .queryParam("size", 10)
                        .queryParam("search", "Programming newsletter")
                        .build()
                }
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isArray
                .jsonPath("$.data.length()").isEqualTo(1)
                .jsonPath("$.nextPageCursor").doesNotExist()
                .jsonPath("$.data[0].id").isEqualTo("1659d4ae-402a-4172-bf8b-0a5c54255587")
                .jsonPath("$.data[0].name").isEqualTo("Programming newsletter")
                .jsonPath("$.data[0].header").isEqualTo("Astrum's Newsletter")
                .jsonPath("$.data[0].description").isEqualTo("The best programming newsletter")
                .jsonPath("$.data[0].inputPlaceholder").isEqualTo("Enter your email")
                .jsonPath("$.data[0].buttonText").isEqualTo("Subscribe")
                .jsonPath("$.data[0].buttonColor").isEqualTo("#2C81E5")
                .jsonPath("$.data[0].backgroundColor").isEqualTo("#DFD150")
                .jsonPath("$.data[0].textColor").isEqualTo("#222222")
                .jsonPath("$.data[0].buttonTextColor").isEqualTo("#FFFFFF")
                .jsonPath("$.data[0].createdAt").isEqualTo("2024-04-21T19:56:07.632")
                .jsonPath("$.data[0].updatedAt").isEqualTo("2024-04-21T19:56:07.711")
        }
    }

    @Test
    @Sql(
        "/db/form/form-batch.sql",
    )
    @Sql(
        "/db/form/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get all forms with search and filter`() {
        webTestClient.run {
            get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path("/api/organization/$organizationId/form")
                        .queryParam("size", 10)
                        .queryParam("search", "Programming newsletter")
                        .queryParam("filter[header]", listOf("eq:Astrum's Newsletter"))
                        .build()
                }
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isArray
                .jsonPath("$.data.length()").isEqualTo(1)
                .jsonPath("$.nextPageCursor").doesNotExist()
                .jsonPath("$.data[0].id").isEqualTo("1659d4ae-402a-4172-bf8b-0a5c54255587")
                .jsonPath("$.data[0].name").isEqualTo("Programming newsletter")
                .jsonPath("$.data[0].header").isEqualTo("Astrum's Newsletter")
                .jsonPath("$.data[0].description").isEqualTo("The best programming newsletter")
                .jsonPath("$.data[0].inputPlaceholder").isEqualTo("Enter your email")
                .jsonPath("$.data[0].buttonText").isEqualTo("Subscribe")
                .jsonPath("$.data[0].buttonColor").isEqualTo("#2C81E5")
                .jsonPath("$.data[0].backgroundColor").isEqualTo("#DFD150")
                .jsonPath("$.data[0].textColor").isEqualTo("#222222")
                .jsonPath("$.data[0].buttonTextColor").isEqualTo("#FFFFFF")
                .jsonPath("$.data[0].createdAt").isEqualTo("2024-04-21T19:56:07.632")
                .jsonPath("$.data[0].updatedAt").isEqualTo("2024-04-21T19:56:07.711")
        }
    }

    @Test
    @Sql(
        "/db/form/form-batch.sql",
    )
    @Sql(
        "/db/form/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get all forms with sort`() {
        val sort = listOf("asc:name")
        webTestClient.run {
            get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path("/api/organization/$organizationId/form")
                        .queryParam("size", 10)
                        .queryParam("sort", sort)
                        .build()
                }
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isArray
                .jsonPath("$.data.length()").isEqualTo(5)
                .jsonPath("$.nextPageCursor").doesNotExist()
                .jsonPath("$.data[0].id").isEqualTo("f8df2049-f28a-45f2-bf99-c960cf038cbe")
                .jsonPath("$.data[0].name").isEqualTo("Dare Group")
                .jsonPath("$.data[0].header").isEqualTo("Dare Group's Newsletter")
                .jsonPath("$.data[0].description").isEqualTo("Dare Group")
                .jsonPath("$.data[0].inputPlaceholder").isEqualTo("Enter your email")
                .jsonPath("$.data[0].buttonText").isEqualTo("Subscribe")
                .jsonPath("$.data[0].buttonColor").isEqualTo("#2C81E5")
                .jsonPath("$.data[0].backgroundColor").isEqualTo("#DFD150")
                .jsonPath("$.data[0].textColor").isEqualTo("#222222")
                .jsonPath("$.data[0].buttonTextColor").isEqualTo("#FFFFFF")
                .jsonPath("$.data[0].createdAt").isEqualTo("2024-04-24T19:56:07.632")
                .jsonPath("$.data[0].updatedAt").isEqualTo("2024-04-24T19:56:07.711")
        }
    }

    @Test
    @Sql(
        "/db/form/form-batch.sql",
    )
    @Sql(
        "/db/form/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get all forms with search and filter and sort`() {
        val sort = listOf("asc:name")
        webTestClient.run {
            get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path("/api/organization/$organizationId/form")
                        .queryParam("size", 10)
                        .queryParam("search", "Programming newsletter")
                        .queryParam("filter[header]", listOf("eq:Astrum's Newsletter"))
                        .queryParam("sort", sort)
                        .build()
                }
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isArray
                .jsonPath("$.data.length()").isEqualTo(1)
                .jsonPath("$.nextPageCursor").doesNotExist()
                .jsonPath("$.data[0].id").isEqualTo("1659d4ae-402a-4172-bf8b-0a5c54255587")
                .jsonPath("$.data[0].name").isEqualTo("Programming newsletter")
                .jsonPath("$.data[0].header").isEqualTo("Astrum's Newsletter")
                .jsonPath("$.data[0].description").isEqualTo("The best programming newsletter")
                .jsonPath("$.data[0].inputPlaceholder").isEqualTo("Enter your email")
                .jsonPath("$.data[0].buttonText").isEqualTo("Subscribe")
                .jsonPath("$.data[0].buttonColor").isEqualTo("#2C81E5")
                .jsonPath("$.data[0].backgroundColor").isEqualTo("#DFD150")
                .jsonPath("$.data[0].textColor").isEqualTo("#222222")
                .jsonPath("$.data[0].buttonTextColor").isEqualTo("#FFFFFF")
                .jsonPath("$.data[0].createdAt").isEqualTo("2024-04-21T19:56:07.632")
                .jsonPath("$.data[0].updatedAt").isEqualTo("2024-04-21T19:56:07.711")
        }
    }

    @Test
    @Sql(
        "/db/form/form-batch.sql",
    )
    @Sql(
        "/db/form/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should forward pagination by cursor`() {
        webTestClient.run {
            get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path("/api/organization/$organizationId/form")
                        .queryParam("size", 3)
                        .build()
                }
                .exchange()
                .expectStatus().isOk
                .expectBody(typeRef)
                .consumeWith { response ->
                    val cursor = response.responseBody?.nextPageCursor
                    assertNotNull(cursor)
                    get()
                        .uri { uriBuilder ->
                            uriBuilder
                                .path("/api/organization/$organizationId/form")
                                .queryParam("size", 3)
                                .queryParam("cursor", cursor)
                                .build()
                        }
                        .exchange()
                        .expectStatus().isOk
                        .expectBody(typeRef)
                        .consumeWith { response2 ->
                            val cursor2 = response2.responseBody?.nextPageCursor
                            assertNull(cursor2)
                        }
                }
        }
    }
}
