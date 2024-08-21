package com.lyra.app.controllers

import com.lyra.UnitTest
import com.lyra.common.domain.error.BusinessRuleValidationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.http.HttpStatus

@UnitTest
internal class GlobalExceptionHandlerTest {

    private val handler = GlobalExceptionHandler()

    @Test
    fun `should return bad request problem detail when illegal argument exception occurs`() {
        val exception = mock(IllegalArgumentException::class.java)
        val problemDetail = handler.handleIllegalArgumentException(exception)

        assertEquals(HttpStatus.BAD_REQUEST.value(), problemDetail.status)
        assertEquals("Bad request", problemDetail.title)
    }

    @Test
    fun `should return bad request problem detail when business rule validation exception occurs`() {
        val exception = mock(BusinessRuleValidationException::class.java)
        val problemDetail = handler.handleIllegalArgumentException(exception)

        assertEquals(HttpStatus.BAD_REQUEST.value(), problemDetail.status)
        assertEquals("Bad request", problemDetail.title)
    }

    @Test
    fun `should return internal server error problem detail when exception occurs`() {
        val exception = mock(Exception::class.java)
        val problemDetail = handler.handleException(exception)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), problemDetail.status)
        assertEquals("Internal server error", problemDetail.title)
    }
}
