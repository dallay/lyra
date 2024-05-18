package com.lyra.spring.boot.repository

import com.lyra.common.domain.presentation.pagination.TimestampCursor
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import java.time.LocalDateTime
import kotlin.random.Random
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.reactivestreams.Subscriber
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

data class MyEntity(
    val fieldName: String,
    val id: Long = Random.nextLong(from = 1000, until = 3000),
    val createdAt: LocalDateTime = LocalDateTime.now()
)

internal class ReactiveSearchRepositoryImplTest {
    private val r2dbcTemplate: R2dbcEntityTemplate = mockk()
    private val reactiveSearchRepository = ReactiveSearchRepositoryImpl<MyEntity>(r2dbcTemplate)
    private val dummyEntity: MyEntity =
        MyEntity("test", 1, LocalDateTime.parse("2021-01-01T00:00:00"))

    @BeforeEach
    fun setUp() {
        val flux: Flux<MyEntity> = flowOf(dummyEntity).asFlux()
        val subscriber: Subscriber<MyEntity> = mockk(relaxed = true)
        coEvery { r2dbcTemplate.select(MyEntity::class.java).matching(any()).all() } returns flux
        every { flux.subscribe(subscriber) } just Runs
        coEvery {
            r2dbcTemplate.select(MyEntity::class.java).matching(any()).all().collectList()
        } returns Mono.just(listOf(dummyEntity))
        coEvery {
            r2dbcTemplate.select(MyEntity::class.java).matching(any()).count()
        } returns Mono.just(1)
    }

    @Test
    fun `should fetch all entities that match the given criteria`() = runBlocking {
        coEvery { r2dbcTemplate.select(MyEntity::class.java).matching(any()).all() } returns flowOf(
            dummyEntity,
        ).asFlux()
        val result = reactiveSearchRepository.findAll(Criteria.empty(), MyEntity::class).toList()
        assertEquals(listOf(dummyEntity), result)
    }

    @Test
    fun `should fetch all entities that match the given criteria with pagination`() = runBlocking {
        val result =
            reactiveSearchRepository.findAll(Criteria.empty(), PageRequest.of(0, 1), MyEntity::class)
        assertEquals(listOf(dummyEntity), result.content)
    }

    @Test
    fun `should fetch all entities that match the given criteria with pagination and a cursor`() =
        runBlocking {
            val cursor = TimestampCursor(createdAt = LocalDateTime.parse("2021-01-01T00:00:00"))
            val result = reactiveSearchRepository.findAllByCursor(
                Criteria.empty(),
                1,
                MyEntity::class,
                Sort.unsorted(),
                cursor,
            )
            assertEquals(listOf(dummyEntity), result.data)
        }
}
