package com.lyra.app.newsletter.subscriber.infrastructure.http

import com.fasterxml.jackson.databind.ObjectMapper
import com.lyra.ControllerTest
import com.lyra.app.newsletter.subscriber.SubscriberStub
import com.lyra.app.newsletter.subscriber.application.SubscriberResponse
import com.lyra.app.newsletter.subscriber.application.search.all.SearchAllSubscribersQuery
import com.lyra.app.newsletter.subscriber.infrastructure.persistence.entity.SubscriberEntity
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.filter.RHSFilterParser
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.spring.boot.presentation.filter.RHSFilterParserFactory
import com.lyra.spring.boot.presentation.sort.SortParser
import com.lyra.spring.boot.presentation.sort.SortParserFactory
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

private const val NUM_SUBSCRIBER = 14

internal class GetAllSubscriberControllerTest : ControllerTest() {
    private val rhsFilterParserFactory = mockk<RHSFilterParserFactory>()
    private val response = SubscriberStub.dummyRandomSubscriberPageResponse(
        NUM_SUBSCRIBER,
    )
    private val rhsFilterParser = mockk<RHSFilterParser<SubscriberEntity>>()

    private lateinit var controller: GetAllSubscriberController
    override lateinit var webTestClient: WebTestClient
    private val organizationId = "a0654720-35dc-49d0-b508-1f7df5d915f1"

    @BeforeEach
    override fun setUp() {
        every { rhsFilterParserFactory.create(SubscriberEntity::class) } returns rhsFilterParser
        every { rhsFilterParser.parse(any()) } returns Criteria.Empty
        every { rhsFilterParser.parse(any(), any()) } returns Criteria.Empty

        val sortParserFactory = mockk<SortParserFactory>()
        val sortParser = SortParser(SubscriberEntity::class, ObjectMapper())
        every { sortParserFactory.create(SubscriberEntity::class) } returns sortParser

        coEvery { mediator.send(any(SearchAllSubscribersQuery::class)) } returns response
        controller = GetAllSubscriberController(mediator, rhsFilterParserFactory, sortParserFactory)
        webTestClient = WebTestClient.bindToController(controller).build()
    }

    @Test
    fun `should get all subscribers`() {
        val data: Collection<SubscriberResponse> = response.data
        webTestClient.get()
            .uri("/api/organization/$organizationId/newsletter/subscriber")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.nextPageCursor").isEqualTo(response.nextPageCursor ?: "")
            .jsonPath("$.data[0].id").isEqualTo(data.first().id)
            .jsonPath("$.data[0].email").isEqualTo(data.first().email)
            .jsonPath("$.data[0].name").isEqualTo(data.first().name)
            .jsonPath("$.data[0].status").isEqualTo(data.first().status)
        coEvery { mediator.send(any(SearchAllSubscribersQuery::class)) }
    }

    @Test
    fun `should get all subscribers by search`() {
        val search = "search value"
        val criteria = Criteria.Or(
            listOf(
                Criteria.Like("email", search),
                Criteria.Like("firstname", search),
                Criteria.Like("lastname", search),
            ),
        )
        val query = SearchAllSubscribersQuery(criteria)
        coEvery { mediator.send(query) } returns response
        every { rhsFilterParserFactory.create(SubscriberEntity::class) } returns rhsFilterParser
        every { rhsFilterParser.parse(any(), eq(true)) } returns criteria
        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/api/organization/$organizationId/newsletter/subscriber")
                    .queryParam("search", search)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.nextPageCursor").isEqualTo(response.nextPageCursor ?: "")
            .jsonPath("$.data[0].id").isEqualTo(response.data.first().id)
            .jsonPath("$.data[0].email").isEqualTo(response.data.first().email)
            .jsonPath("$.data[0].name").isEqualTo(response.data.first().name)
            .jsonPath("$.data[0].status").isEqualTo(response.data.first().status)
        coEvery { mediator.send(query) }
    }

    @Test
    fun `should get all subscribers with sort`() {
        val sort = listOf("asc:email", "desc:firstname", "asc:lastname", "desc:status")
        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/api/organization/$organizationId/newsletter/subscriber")
                    .queryParam("sort", sort)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.nextPageCursor").isEqualTo(response.nextPageCursor ?: "")
            .jsonPath("$.data[0].id").isEqualTo(response.data.first().id)
            .jsonPath("$.data[0].email").isEqualTo(response.data.first().email)
            .jsonPath("$.data[0].name").isEqualTo(response.data.first().name)
            .jsonPath("$.data[0].status").isEqualTo(response.data.first().status)
    }

    @Test
    fun `should paginate subscribers using nextCursor from response`() {
        val pageSize = 5

        // Assuming the first response contains more than 5 subscribers
        val firstResponse = SubscriberStub.dummyRandomSubscriberPageResponse(5)

        val firstQuery = SearchAllSubscribersQuery(size = pageSize)
        coEvery { mediator.send(firstQuery) } returns firstResponse

        val firstRequest = webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/api/organization/$organizationId/newsletter/subscriber")
                    .queryParam("size", pageSize)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody(CursorPageResponse::class.java)
            .returnResult()
            .responseBody

        // the second response contains less than 5 subscribers (last page)
        val secondResponse = SubscriberStub.dummyRandomSubscriberPageResponse(3)

        val secondQuery =
            SearchAllSubscribersQuery(size = pageSize, cursor = firstRequest?.nextPageCursor)
        coEvery { mediator.send(secondQuery) } returns secondResponse

        if (firstRequest != null) {
            webTestClient.get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path("/api/organization/$organizationId/newsletter/subscriber")
                        .queryParam("size", pageSize)
                        .queryParam("cursor", firstRequest.nextPageCursor)
                        .build()
                }
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isArray
                .jsonPath("$.data.length()").isEqualTo(secondResponse.data.size)
                .jsonPath("$.nextPageCursor").isEqualTo(secondResponse.nextPageCursor ?: "")
        }
    }
}
