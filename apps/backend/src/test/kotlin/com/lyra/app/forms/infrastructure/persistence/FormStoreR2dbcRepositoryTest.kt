package com.lyra.app.forms.infrastructure.persistence

import com.lyra.UnitTest
import com.lyra.app.forms.FormStub
import com.lyra.app.forms.domain.Form
import com.lyra.app.forms.domain.exception.FormException
import com.lyra.app.forms.infrastructure.persistence.mapper.FormMapper.toEntity
import com.lyra.app.forms.infrastructure.persistence.repository.FormR2dbcRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.TransientDataAccessResourceException

@UnitTest
internal class FormStoreR2dbcRepositoryTest {
    private val formR2dbcRepository: FormR2dbcRepository = mockk()
    private val formStoreR2dbcRepository = FormStoreR2dbcRepository(formR2dbcRepository)
    private lateinit var form: Form

    @BeforeEach
    fun setUp() {
        form = FormStub.create()
        coEvery { formR2dbcRepository.save(any()) } returns form.toEntity()
    }

    @Test
    fun `should create form`(): Unit = runBlocking {
        formStoreR2dbcRepository.create(form)
        coEvery { formR2dbcRepository.save(any()) }
    }

    @Test
    fun `should handle duplicate form creation gracefully`(): Unit = runBlocking {
        coEvery { formR2dbcRepository.save(any()) } throws DuplicateKeyException("Duplicate key")
        assertThrows<FormException> {
            formStoreR2dbcRepository.create(form)
        }
    }

    @Test
    fun `should handle unexpected error during form creation`(): Unit = runBlocking {
        coEvery { formR2dbcRepository.save(any()) } throws RuntimeException("Unexpected error")
        assertThrows<RuntimeException> {
            formStoreR2dbcRepository.create(form)
        }
    }

    @Test
    fun `should update form`(): Unit = runBlocking {
        formStoreR2dbcRepository.update(form)
        coEvery { formR2dbcRepository.save(any()) }
    }

    @Test
    fun `should handle unexpected error during form update`(): Unit = runBlocking {
        coEvery { formR2dbcRepository.save(any()) } throws RuntimeException("Unexpected error")
        assertThrows<RuntimeException> {
            formStoreR2dbcRepository.update(form)
        }
    }

    @Test
    fun `should handle error when the form does not exist`(): Unit = runBlocking {
        coEvery { formR2dbcRepository.save(any()) } throws TransientDataAccessResourceException("Unexpected error")
        assertThrows<FormException> {
            formStoreR2dbcRepository.update(form)
        }
    }

    @Test
    fun `should find form by id`(): Unit = runBlocking {
        coEvery { formR2dbcRepository.findById(any()) } returns form.toEntity()
        formStoreR2dbcRepository.findById(form.id)
    }

    @Test
    fun `should delete form`(): Unit = runBlocking {
        coEvery { formR2dbcRepository.deleteById(any()) } returns Unit
        formStoreR2dbcRepository.delete(form.id)
    }
}
