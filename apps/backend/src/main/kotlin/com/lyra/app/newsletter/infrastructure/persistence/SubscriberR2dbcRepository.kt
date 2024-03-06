package com.lyra.app.newsletter.infrastructure.persistence

import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.app.newsletter.domain.SubscriberRepository
import com.lyra.app.newsletter.domain.SubscriberStatus
import com.lyra.app.newsletter.infrastructure.persistence.entity.SubscriberEntity
import com.lyra.app.newsletter.infrastructure.persistence.mapper.SubscriberMapper.toDomain
import com.lyra.app.newsletter.infrastructure.persistence.mapper.SubscriberMapper.toEntity
import com.lyra.app.newsletter.infrastructure.persistence.repository.SubscriberRegistratorR2dbcRepository
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.pagination.Cursor
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.common.domain.presentation.pagination.OffsetPageResponse
import com.lyra.common.domain.presentation.pagination.TimestampCursor
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
import org.springframework.data.domain.Sort as SpringSort

private const val DEFAULT_LIMIT = 10

@Repository
class SubscriberR2dbcRepository(
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

    override suspend fun searchAllByOffset(
        criteria: Criteria?,
        size: Int?,
        page: Int?,
        sort: Sort?
    ): OffsetPageResponse<Subscriber> {
        log.debug(
            "Get all subscribers with filters: {} and sort: {} and pagination: size={}, page={}",
            criteria,
            sort,
            size,
            page,
        )
        val criteriaParsed = criteriaParser.parse(criteria ?: Criteria.Empty)
        val sortCriteria = sort.toSpringSort()
        // Pageable paging = PageRequest.of(page, size);
        val pageable = PageRequest.of(page ?: 0, size ?: DEFAULT_LIMIT, sortCriteria)

        return subscriberRegistratorR2dbcRepository.findAll(
            criteriaParsed,
            pageable,
            SubscriberEntity::class,
        )
            .awaitFirstOrNull()
            ?.let { pageEntity ->
                OffsetPageResponse(
                    data = pageEntity.content.map { it.toDomain() },
                    total = pageEntity.totalElements,
                    perPage = pageEntity.size,
                    page = pageEntity.number,
                    totalPages = pageEntity.totalPages,
                )
            } ?: OffsetPageResponse(emptyList(), 0, 0, 0)
    }

    override suspend fun searchAllByCursor(
        criteria: Criteria?,
        size: Int?,
        sort: Sort?,
        cursor: Cursor?
    ): CursorPageResponse<Subscriber> {
        log.debug(
            "Get all subscribers with filters: {} and sort: {} and pagination: size={}, cursor={}",
            criteria,
            sort,
            size,
            cursor,
        )
        val criteriaParsed = criteriaParser.parse(criteria ?: Criteria.Empty)
        val springSort = sort?.toSpringSort() ?: SpringSort.unsorted()
        return subscriberRegistratorR2dbcRepository.findAllByCursor(
            criteriaParsed,
            size ?: DEFAULT_LIMIT,
            SubscriberEntity::class,
            springSort,
            cursor ?: TimestampCursor.DEFAULT_CURSOR,
        ).map { pageResponse ->
            val content = pageResponse.data.map { it.toDomain() }
            CursorPageResponse(content, pageResponse.nextPageCursor)
        }.awaitFirstOrNull() ?: CursorPageResponse(emptyList(), null)
    }

    override suspend fun searchActive(): Flow<Subscriber> {
        return subscriberRegistratorR2dbcRepository.findAllByStatus(SubscriberStatus.ENABLED)
            .map { it.toDomain() }.asFlow()
    }

    companion object {
        private val log = LoggerFactory.getLogger(SubscriberR2dbcRepository::class.java)
    }
}
