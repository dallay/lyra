package com.lyra.app.forms.infrastructure.persistence

import com.lyra.app.forms.domain.Form
import com.lyra.app.forms.domain.FormDestroyerRepository
import com.lyra.app.forms.domain.FormFinderRepository
import com.lyra.app.forms.domain.FormId
import com.lyra.app.forms.domain.FormRepository
import com.lyra.app.forms.domain.exception.FormException
import com.lyra.app.forms.infrastructure.persistence.entity.FormEntity
import com.lyra.app.forms.infrastructure.persistence.mapper.FormMapper.toDomain
import com.lyra.app.forms.infrastructure.persistence.mapper.FormMapper.toEntity
import com.lyra.app.forms.infrastructure.persistence.repository.FormR2dbcRepository
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.pagination.Cursor
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.common.domain.presentation.pagination.TimestampCursor
import com.lyra.common.domain.presentation.sort.Sort
import com.lyra.spring.boot.presentation.sort.toSpringSort
import com.lyra.spring.boot.repository.R2DBCCriteriaParser
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Repository

private const val DEFAULT_LIMIT = 20

@Repository
class FormStoreR2dbcRepository(
    private val formR2dbcRepository: FormR2dbcRepository
) : FormRepository, FormFinderRepository, FormDestroyerRepository {
    private val criteriaParser = R2DBCCriteriaParser(FormEntity::class)
    /**
     * This function is used to create a new form.
     * It is a suspending function, meaning it can be paused and resumed at a later time.
     * This makes it suitable for use in a coroutine context, where it can be used for non-blocking IO operations.
     *
     * @param form The form to create.
     */
    /**
     * This function is used to create a new form.
     * It is a suspending function, meaning it can be paused and resumed at a later time.
     * This makes it suitable for use in a coroutine context, where it can be used for non-blocking IO operations.
     *
     * @param form The form to create.
     */
    override suspend fun create(form: Form) {
        try {
            formR2dbcRepository.save(form.toEntity())
        } catch (e: DuplicateKeyException) {
            log.error("Form already exists in the database: ${form.id.value}")
            throw FormException("Error creating form", e)
        }
    }

    /**
     * This function is used to update an existing form.
     * It is a suspending function, meaning it can be paused and resumed at a later time.
     * This makes it suitable for use in a coroutine context, where it can be used for non-blocking IO operations.
     *
     * @param form The form to update.
     */

    /**
     * This function is used to update an existing form.
     * It is a suspending function, meaning it can be paused and resumed at a later time.
     * This makes it suitable for use in a coroutine context, where it can be used for non-blocking IO operations.
     *
     * @param form The form to update.
     */
    override suspend fun update(form: Form) {
        try {
            val entity = form.toEntity()
            formR2dbcRepository.save(entity)
        } catch (e: org.springframework.dao.TransientDataAccessResourceException) {
            log.error("Error updating form with id: ${form.id.value}")
            throw FormException("Error updating form because it does not exist", e)
        }
    }

    /**
     * This function is used to find a form by its id.
     * It is a suspending function, meaning it can be paused and resumed at a later time.
     * This makes it suitable for use in a coroutine context, where it can be used for non-blocking IO operations.
     *
     * @param id The id of the form to find.
     * @return The form if found, or null if not found.
     */

    /**
     * This function is used to find a form by its id.
     * It is a suspending function, meaning it can be paused and resumed at a later time.
     * This makes it suitable for use in a coroutine context, where it can be used for non-blocking IO operations.
     *
     * @param id The id of the form to find.
     * @return The form if found, or null if not found.
     */
    override suspend fun findById(id: FormId): Form? =
        formR2dbcRepository.findById(id.value)?.toDomain()

    /**
     * Find a form by form id and organization id.
     * @param formId The form id.
     * @param organizationId The organization id.
     * @return The form if found, or null if not found.
     */
    override suspend fun findByFormIdAndOrganizationId(
        formId: FormId,
        organizationId: OrganizationId
    ): Form? {
        val entity = formR2dbcRepository.findByIdAndOrganizationId(formId.value, organizationId.value)
        return entity?.toDomain()
    }

    /**
     * This function is used to search all [Form] by cursor.
     *
     * @param criteria The criteria to use for the search.
     * @param size The size of the page to return.
     * @param sort The sort order to use for the results.
     * @param cursor The cursor to use for the search.
     * @return A CursorPageResponse containing the search results.
     */

    /**
     * This function is used to search all [Form] by cursor.
     *
     * @param criteria The criteria to use for the search.
     * @param size The size of the page to return.
     * @param sort The sort order to use for the results.
     * @param cursor The cursor to use for the search.
     * @return A CursorPageResponse containing the search results.
     */
    override suspend fun search(
        criteria: Criteria?,
        size: Int?,
        sort: Sort?,
        cursor: Cursor?
    ): CursorPageResponse<Form> {
        log.debug(
            "Searching all forms with criteria: {}, size: {}, cursor: {}, sort: {}",
            criteria,
            size,
            cursor,
            sort,
        )
        val criteriaParsed = criteriaParser.parse(criteria ?: Criteria.Empty)
        val springSort = sort?.toSpringSort() ?: org.springframework.data.domain.Sort.unsorted()
        val pageResponse = formR2dbcRepository.findAllByCursor(
            criteriaParsed,
            size ?: DEFAULT_LIMIT,
            FormEntity::class,
            springSort,
            cursor ?: TimestampCursor.DEFAULT_CURSOR,
        )
        val content = pageResponse.data.map { it.toDomain() }
        return CursorPageResponse(content, pageResponse.nextPageCursor)
    }

    /**
     * Deletes a form with the given id.
     *
     * @param formId The id of the form to be deleted.
     */
    override suspend fun delete(formId: FormId) = formR2dbcRepository.deleteById(formId.value)

    companion object {
        private val log = LoggerFactory.getLogger(FormStoreR2dbcRepository::class.java)
    }
}
