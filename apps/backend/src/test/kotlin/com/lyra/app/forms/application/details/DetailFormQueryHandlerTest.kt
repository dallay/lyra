package com.lyra.app.forms.application.details

import com.lyra.UnitTest
import com.lyra.app.forms.FormStub
import com.lyra.app.forms.application.FormResponse
import com.lyra.app.forms.domain.FormId
import com.lyra.app.forms.domain.exception.FormNotFoundException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class DetailFormQueryHandlerTest {

    private lateinit var detailFormFetcher: DetailFormFetcher
    private lateinit var findFormQueryHandler: DetailFormQueryHandler

    @BeforeEach
    fun setup() {
        detailFormFetcher = mockk()
        findFormQueryHandler = DetailFormQueryHandler(detailFormFetcher)
    }

    @Test
    fun `should return form response when form is found`() = runBlocking {
        // Given
        val formUuid = UUID.randomUUID().toString()
        val organizaitionUuid = UUID.randomUUID().toString()
        val formId = FormId(formUuid)
        val form = FormStub.create(id = formUuid, organizationId = organizaitionUuid)
        val formResponse = FormResponse.from(form)
        coEvery {
            detailFormFetcher.find(
                formId = formId,
            )
        } returns form

        // When
        val result = findFormQueryHandler.handle(DetailFormQuery(formUuid))

        // Then
        assertEquals(formResponse, result)
        coVerify(exactly = 1) { detailFormFetcher.find(formId) }
    }

    @Test
    fun `should throw exception when form is not found`() {
        // Given
        val formUuid = UUID.randomUUID().toString()
        val formId = FormId(formUuid)
        coEvery { detailFormFetcher.find(formId) } returns null

        // Then
        assertThrows(FormNotFoundException::class.java) {
            // When
            runBlocking {
                findFormQueryHandler.handle(DetailFormQuery(formUuid))
            }
        }
        coVerify(exactly = 1) { detailFormFetcher.find(formId) }
    }
}
