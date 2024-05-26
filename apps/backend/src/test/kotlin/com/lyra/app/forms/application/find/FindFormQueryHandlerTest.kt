package com.lyra.app.forms.application.find

import com.lyra.UnitTest
import com.lyra.app.forms.FormStub
import com.lyra.app.forms.application.FormResponse
import com.lyra.app.forms.domain.FormId
import com.lyra.app.forms.domain.exception.FormNotFoundException
import io.mockk.coEvery
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
        formFinder = mockk<FormFinder>()
        findFormQueryHandler = FindFormQueryHandler(formFinder)
    }

    @Test
    fun `should return form response when form is found`() = runBlocking {
        val id = UUID.randomUUID().toString()
        val formId = FormId(id)
        val form = FormStub.create()
        val formResponse = FormResponse.from(form)
        coEvery { formFinder.find(formId) } returns form

        val result = findFormQueryHandler.handle(FindFormQuery(id))

        assertEquals(formResponse, result)
    }

    @Test
    fun `should throw exception when form is not found`() {
        val id = UUID.randomUUID().toString()
        val formId = FormId(id)
        coEvery { formFinder.find(formId) } returns null

        assertThrows(FormNotFoundException::class.java) {
            runBlocking {
                findFormQueryHandler.handle(FindFormQuery(id))
            }
        }
    }
}
