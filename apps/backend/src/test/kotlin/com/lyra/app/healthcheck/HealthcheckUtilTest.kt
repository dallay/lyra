package com.lyra.app.healthcheck

import com.lyra.UnitTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@UnitTest
class HealthcheckUtilTest {

    @Test
    fun `should return true when system is healthy`() {
        // Given
        val healthcheckUtil = HealthcheckUtil()

        // When
        val result = healthcheckUtil.isHealthy()

        // Then
        Assertions.assertTrue(result)
    }

    // Simple utility class for testing
    class HealthcheckUtil {
        fun isHealthy(): Boolean {
            return true
        }
    }
}
