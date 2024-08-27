package com.lyra.app.forms.application.find

import com.lyra.UnitTest
import com.lyra.app.forms.FormStub
import com.lyra.app.forms.application.FormResponse
import com.lyra.app.forms.domain.FormId
import com.lyra.app.forms.domain.exception.FormNotFoundException
import com.lyra.app.organization.domain.OrganizationId
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
internal class FindFormQueryHandlerTest {

    private lateinit var formFinder: FormFinder
    private lateinit var findFormQueryHandler: FindFormQueryHandler

    @BeforeEach
    fun setup() {
        formFinder = mockk()
        findFormQueryHandler = FindFormQueryHandler(formFinder)
    }

    @Test
    fun `should return form response when form is found`() = runBlocking {
        // Given
        val formUuid = UUID.randomUUID().toString()
        val organizaitionUuid = UUID.randomUUID().toString()
        val formId = FormId(formUuid)
        val organizationId = OrganizationId(organizaitionUuid)
        val form = FormStub.create(id = formUuid, organizationId = organizaitionUuid)
        val formResponse = FormResponse.from(form)
        coEvery {
            formFinder.find(
                organizationId = organizationId,
                formId = formId,
            )
        } returns form

        // When
        val result = findFormQueryHandler.handle(FindFormQuery(organizationId = organizaitionUuid, formId = formUuid))

        // Then
        assertEquals(formResponse, result)
        coVerify(exactly = 1) { formFinder.find(organizationId, formId) }
    }

    @Test
    fun `should throw exception when form is not found`() {
        // Given
        val formUuid = UUID.randomUUID().toString()
        val organizationUuid = UUID.randomUUID().toString()
        val formId = FormId(formUuid)
        val organizationId = OrganizationId(organizationUuid)
        coEvery { formFinder.find(organizationId = organizationId, formId = formId) } returns null

        // Then
        assertThrows(FormNotFoundException::class.java) {
            // When
            runBlocking {
                findFormQueryHandler.handle(FindFormQuery(organizationId = organizationUuid, formId = formUuid))
            }
        }
        coVerify(exactly = 1) { formFinder.find(organizationId, formId) }
    }
}
