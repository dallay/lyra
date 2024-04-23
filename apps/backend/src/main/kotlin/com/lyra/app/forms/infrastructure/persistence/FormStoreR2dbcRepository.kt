package com.lyra.app.forms.infrastructure.persistence

import com.lyra.app.forms.domain.Form
import com.lyra.app.forms.domain.FormRepository
import com.lyra.app.forms.domain.exception.FormException
import com.lyra.app.forms.infrastructure.persistence.mapper.FormMapper.toEntity
import com.lyra.app.forms.infrastructure.persistence.repository.FormR2dbcRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Repository

@Repository
class FormStoreR2dbcRepository(
    private val formR2dbcRepository: FormR2dbcRepository
) : FormRepository {
  /*
   * Create a form in the database
   * @param form Form to create
   * @throws FormException if the form already exists in the database
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
     * Update a form
     * @param form Form to update using optimistic locking strategy (version)
     * @throws FormException if the form does not exist in the database
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

    companion object {
        private val log = LoggerFactory.getLogger(FormStoreR2dbcRepository::class.java)
    }
}
