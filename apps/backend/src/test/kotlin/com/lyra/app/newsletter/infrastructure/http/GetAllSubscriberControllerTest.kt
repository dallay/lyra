package com.lyra.app.newsletter.infrastructure.http

import com.fasterxml.jackson.databind.ObjectMapper
import com.lyra.app.newsletter.SubscriberStub
import com.lyra.app.newsletter.application.SubscriberResponse
import com.lyra.app.newsletter.application.search.all.SearchAllSubscribersQuery
import com.lyra.app.newsletter.infrastructure.persistence.entity.SubscriberEntity
import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.filter.RHSFilterParser
import com.lyra.spring.boot.presentation.filter.RHSFilterParserFactory
import com.lyra.spring.boot.presentation.sort.SortParser
import com.lyra.spring.boot.presentation.sort.SortParserFactory
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlin.reflect.KProperty1
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

private const val ENDPOINT = "/api/newsletter/subscribers"
private const val NUM_SUBSCRIBER = 14
class GetAllSubscriberControllerTest {
    private val mediator = mockk<Mediator>()
    private val rhsFilterParserFactory = mockk<RHSFilterParserFactory>()
    private val response = SubscriberStub.dummyRandomSubscriberResponseOffsetPage(
        NUM_SUBSCRIBER,
    )

    private lateinit var controller: GetAllSubscriberController
    private lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setUp() {
        val rhsFilterParser = mockk<RHSFilterParser<SubscriberEntity>>()
        every { rhsFilterParserFactory.create(SubscriberEntity::class) } returns rhsFilterParser
        every { rhsFilterParser.parse(any()) } returns Criteria.Empty

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
            .uri(ENDPOINT)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.total").isEqualTo(response.total ?: 0)
            .jsonPath("$.perPage").isEqualTo(response.perPage)
            .jsonPath("$.page").isEqualTo(response.page ?: 0)
            .jsonPath("$.data[0].id").isEqualTo(data.first().id)
            .jsonPath("$.data[0].email").isEqualTo(data.first().email)
            .jsonPath("$.data[0].name").isEqualTo(data.first().name)
            .jsonPath("$.data[0].status").isEqualTo(data.first().status)
        coEvery { mediator.send(any(SearchAllSubscribersQuery::class)) }
    }

    @Test
    fun `should get all subscribers with filter`() {
        val filter = mapOf<KProperty1<SubscriberEntity, *>, Collection<String?>>(
            SubscriberEntity::email to listOf("eq:email"),
            SubscriberEntity::firstname to listOf("eq:firstname"),
            SubscriberEntity::lastname to listOf("eq:lastname"),
            SubscriberEntity::status to listOf("eq:status"),
        )
        val criteria = Criteria.And(
            listOf(
                Criteria.Equals("email", "email"),
                Criteria.Equals("firstname", "firstname"),
                Criteria.Equals("lastname", "lastname"),
                Criteria.Equals("status", "status"),
            ),
        )
        val query = SearchAllSubscribersQuery(criteria)
        coEvery { mediator.send(query) } returns response
        val rhsFilterParser = mockk<RHSFilterParser<SubscriberEntity>>()
        every { rhsFilterParserFactory.create(SubscriberEntity::class) } returns rhsFilterParser
        every { rhsFilterParser.parse(filter) } returns criteria
        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(ENDPOINT)
                    .queryParam("email", filter[SubscriberEntity::email])
                    .queryParam("firstname", filter[SubscriberEntity::firstname])
                    .queryParam("lastname", filter[SubscriberEntity::lastname])
                    .queryParam("status", filter[SubscriberEntity::status])
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.total").isEqualTo(response.total ?: 0)
            .jsonPath("$.perPage").isEqualTo(response.perPage)
            .jsonPath("$.page").isEqualTo(response.page ?: 0)
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
                    .path(ENDPOINT)
                    .queryParam("sort", sort)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.total").isEqualTo(response.total ?: 0)
            .jsonPath("$.perPage").isEqualTo(response.perPage)
            .jsonPath("$.page").isEqualTo(response.page ?: 0)
            .jsonPath("$.data[0].id").isEqualTo(response.data.first().id)
            .jsonPath("$.data[0].email").isEqualTo(response.data.first().email)
            .jsonPath("$.data[0].name").isEqualTo(response.data.first().name)
            .jsonPath("$.data[0].status").isEqualTo(response.data.first().status)
    }
}
