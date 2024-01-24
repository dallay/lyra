package com.lyra.app.newsletter.infrastructure.persistence

import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.app.newsletter.domain.SubscriberRepository
import com.lyra.app.newsletter.domain.SubscriberStatus
import com.lyra.app.newsletter.infrastructure.persistence.entity.SubscriberEntity
import com.lyra.app.newsletter.infrastructure.persistence.mapper.SubscriberMapper.toDomain
import com.lyra.app.newsletter.infrastructure.persistence.mapper.SubscriberMapper.toEntity
import com.lyra.app.newsletter.infrastructure.persistence.repository.SubscriberRegistratorR2dbcRepository
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.pagination.OffsetPage
import com.lyra.common.domain.presentation.sort.Sort
import com.lyra.spring.boot.presentation.sort.toSpringSort
import com.lyra.spring.boot.repository.R2DBCCriteriaParser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

private const val DEFAULT_LIMIT = 10

@Repository
class SubscriberRepositoryRepository(
    private val subscriberRegistratorR2dbcRepository: SubscriberRegistratorR2dbcRepository,
) : SubscriberRepository {
    private val criteriaParser = R2DBCCriteriaParser(SubscriberEntity::class)
    override suspend fun create(subscriber: Subscriber) {
        val entity = subscriber.toEntity()

        subscriberRegistratorR2dbcRepository
            .save(entity)
            .onErrorResume { throwable ->
                if (throwable is DuplicateKeyException) {
                    log.info("Subscriber already exists in the database: ${subscriber.email.email}")
                    Mono.empty() // Ignore the exception and continue with an empty Mono
                } else {
                    log.error(
                        "Error while saving subscriber to database: ${subscriber.email.email}",
                        throwable,
                    )
                    Mono.error(throwable) // Propagate the exception
                }
            }
            .subscribe()
    }

    override suspend fun searchAll(
        criteria: Criteria?,
        limit: Int?,
        offset: Long?,
        sort: Sort?
    ): OffsetPage<Subscriber> {
        val criteriaParsed = criteriaParser.parse(criteria ?: Criteria.Empty)
        val sortCriteria = sort.toSpringSort()
        val pageable = PageRequest.of(offset?.toInt() ?: 0, limit ?: DEFAULT_LIMIT, sortCriteria)

        return subscriberRegistratorR2dbcRepository.findAll(criteriaParsed, pageable, SubscriberEntity::class)
            .awaitFirstOrNull()
            ?.let { pageEntity ->
                OffsetPage(
                    data = pageEntity.content.map { it.toDomain() },
                    total = pageEntity.totalElements,
                    perPage = pageEntity.size,
                    page = pageEntity.number,
                )
            } ?: OffsetPage(emptyList(), 0, 0, 0)
    }

    override suspend fun searchActive(): Flow<Subscriber> {
        return subscriberRegistratorR2dbcRepository.findAllByStatus(SubscriberStatus.ENABLED)
            .map { it.toDomain() }.asFlow()
    }

    companion object {
        private val log = LoggerFactory.getLogger(SubscriberRepositoryRepository::class.java)
    }
}
