package com.lyra.app.forms.infrastructure.persistence

import com.lyra.app.forms.domain.Form
import com.lyra.app.forms.domain.FormFinderRepository
import com.lyra.app.forms.domain.FormId
import com.lyra.app.forms.domain.FormRepository
import com.lyra.app.forms.domain.exception.FormException
import com.lyra.app.forms.infrastructure.persistence.mapper.FormMapper.toDomain
import com.lyra.app.forms.infrastructure.persistence.mapper.FormMapper.toEntity
import com.lyra.app.forms.infrastructure.persistence.repository.FormR2dbcRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Repository

@Repository
class FormStoreR2dbcRepository(
    private val formR2dbcRepository: FormR2dbcRepository
) : FormRepository, FormFinderRepository {
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
    override suspend fun findById(id: FormId): Form? = formR2dbcRepository.findById(id.value)?.toDomain()

    companion object {
        private val log = LoggerFactory.getLogger(FormStoreR2dbcRepository::class.java)
    }
}
