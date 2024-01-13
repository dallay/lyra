package com.lyra.app.newsletter.infrastructure.persistence

import com.lyra.app.UnitTest
import com.lyra.app.newsletter.SubscriberStub
import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.app.newsletter.infrastructure.persistence.mapper.SubscriberMapper.toEntity
import com.lyra.app.newsletter.infrastructure.persistence.repository.SubscriberRegistratorR2dbcRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.dao.DuplicateKeyException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@UnitTest
internal class SubscriberRepositoryRepositoryTest {
    private val subscriberRegistratorR2dbcRepository: SubscriberRegistratorR2dbcRepository = mockk()
    private val subscriberRepositoryRepository =
        SubscriberRepositoryRepository(subscriberRegistratorR2dbcRepository)
    private lateinit var subscribers: List<Subscriber>

    @BeforeEach
    fun setUp() {
        subscribers = runBlocking { SubscriberStub.dummyRandomSubscribersFlow(10).toList() }
        val subscribersEntities = subscribers.map { it.toEntity() }.toList()
        coEvery { subscriberRegistratorR2dbcRepository.save(any()) } returns Mono.just(
            subscribersEntities.first(),
        )
        coEvery { subscriberRegistratorR2dbcRepository.findAll() } returns Flux.fromIterable(
            subscribersEntities,
        )
        coEvery { subscriberRegistratorR2dbcRepository.findAllByStatus(any()) } returns Flux.fromIterable(
            subscribersEntities,
        )
    }

    @Test
    fun `should save a new subscriber`() {
        // Given
        val subscriber = subscribers.first()
        // When
        runBlocking {
            subscriberRepositoryRepository.create(subscriber)
        }
        // Then
        coEvery { subscriberRegistratorR2dbcRepository.save(any()) }
    }

    @Test
    fun `should not save a new subscriber if it already exists`() {
        // Given
        val subscriber = subscribers.first()
        coEvery { subscriberRegistratorR2dbcRepository.save(any()) } returns Mono.error(
            DuplicateKeyException("Duplicate key"),
        )
        // When
        runBlocking {
            subscriberRepositoryRepository.create(subscriber)
        }
        // Then
        coEvery { subscriberRegistratorR2dbcRepository.save(any()) }
    }

    @Test
    fun `should not save a new subscriber if an unknown exception occur`() {
        // Given
        val subscriber = subscribers.first()
        coEvery { subscriberRegistratorR2dbcRepository.save(any()) } returns Mono.error(
            Exception("Unknown exception"),
        )
        // When
        runBlocking {
            subscriberRepositoryRepository.create(subscriber)
        }
        // Then
        coEvery { subscriberRegistratorR2dbcRepository.save(any()) }
    }

    @Test
    fun `should search all subscribers`() = runBlocking {
        // Given
        // When
        val response = subscriberRepositoryRepository.searchAll().toList()
        // Then
        coEvery { subscriberRegistratorR2dbcRepository.findAll() }
        assertEquals(subscribers, response)
    }

    @Test
    fun `should search all active subscribers`() = runBlocking {
        // Given
        // When
        val response = subscriberRepositoryRepository.searchActive().toList()
        // Then
        coEvery { subscriberRegistratorR2dbcRepository.findAllByStatus(any()) }
        assertEquals(subscribers, response)
    }
}
