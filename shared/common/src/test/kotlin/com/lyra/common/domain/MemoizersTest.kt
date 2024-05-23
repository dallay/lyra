package com.lyra.common.domain

import java.util.concurrent.atomic.AtomicInteger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class MemoizersTest {

    @Test
    fun shouldGetFunctionResult() {
        val memoizer: (Double) -> Double = Memoizers.of { d: Double -> d * d }

        assertEquals(4.0, memoizer(2.0))
    }

    @Test
    fun shouldMemoizeFunctionResult() {
        val result = AtomicInteger()

        val memoizer: (Any) -> Int = Memoizers.of { _: Any -> result.incrementAndGet() }

        assertEquals(memoizer(1), memoizer(1))
        assertNotEquals(memoizer(1), memoizer(2))
    }

    @Test
    fun shouldMemoizeNullResult() {
        val factory = NullFactory()
        val memoizer: (Any) -> String = Memoizers.of(factory)

        memoizer(1)
        memoizer(1)

        assertEquals(1, factory.callsCount())
        val m1 = memoizer(1)
        assertNotNull(m1)
        assertEquals("1", m1)
        assertEquals(1, factory.callsCount())
    }

    @Test
    fun shouldMemoizeSupplier() {
        val supplier = Memoizers.of { _: Unit -> "Hello World!" }

        assertEquals("Hello World!", supplier(Unit))
    }

    private class NullFactory : (Any) -> String {

        private val callsCount = AtomicInteger()

        fun callsCount(): Int = callsCount.get()

        override fun invoke(p1: Any): String {
            callsCount.incrementAndGet()
            return callsCount.toString()
        }
    }
}
