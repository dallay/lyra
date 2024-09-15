package com.lyra.common.domain.criteria

import java.util.regex.Pattern
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class RuntimeCriteriaParserTest {
    data class TestData(
        var name: String? = null,
        var age: Int? = null,
        var activate: Boolean? = null
    )

    private data class TestCase(
        val query: Criteria,
        val expectTrue: List<TestData> = listOf(),
        val expectFalse: List<TestData> = listOf(),
    )

    private val parser = RuntimeCriteriaParser(TestData::class)

    @Test
    fun parse() {
        val testCases = listOf(
            *notTestCaseList().toTypedArray(),
            *isTestCaseList().toTypedArray(),
            *betweenAndNotBetweenTestCaseList().toTypedArray(),
            *lestThanTestCaseList().toTypedArray(),
            *greaterThanTestCaseList().toTypedArray(),
            *isNullAndNotNullTestCaseList().toTypedArray(),
            *likeAndNotLikeTestCaseList().toTypedArray(),
            *regexpAndNotRegexpTestCaseList().toTypedArray(),
            *inAndNotInTestCaseList().toTypedArray(),
            *isTruAndIsFalseTestCaseList().toTypedArray(),
            TestCase(
                query = Criteria.And(listOf()),
            ),
            TestCase(
                query = Criteria.And(listOf(where(TestData::activate).isTrue())),
                expectTrue = listOf(TestData(activate = true)),
                expectFalse = listOf(TestData(activate = false), TestData(activate = null)),
            ),
            TestCase(
                query = where(TestData::activate).isTrue().and(where(TestData::name).`is`("test")),
                expectTrue = listOf(TestData(activate = true, name = "test")),
                expectFalse = listOf(
                    TestData(activate = false, name = "test"),
                    TestData(activate = true, name = "!test"),
                ),
            ),
            TestCase(
                query = Criteria.Or(listOf()),
            ),
            TestCase(
                query = where(TestData::activate).isTrue().or(where(TestData::name).`is`("test")),
                expectTrue = listOf(
                    TestData(activate = true, name = "test"),
                    TestData(activate = false, name = "test"),
                    TestData(activate = true, name = "!test"),
                ),
                expectFalse = listOf(TestData(activate = false, name = "!test")),
            ),
            TestCase(
                query = Criteria.Or(listOf(where(TestData::activate).isTrue())),
                expectTrue = listOf(TestData(activate = true)),
                expectFalse = listOf(TestData(activate = false), TestData(activate = null)),
            ),
        )

        testCases.forEach { testCase ->
            val criteria = parser.parse(testCase.query)
            testCase.expectTrue.forEach {
                assertTrue(criteria?.invoke(it) ?: false)
            }
            testCase.expectFalse.forEach {
                assertFalse(criteria?.invoke(it) ?: true)
            }
            if (testCase.expectFalse.isEmpty() && testCase.expectTrue.isEmpty()) {
                assertNull(criteria)
            }
        }
    }
    private fun notTestCaseList(): List<TestCase> = listOf(
        TestCase(
            query = where(TestData::name).not("test"),
            expectTrue = listOf(TestData(name = "!test"), TestData(name = null)),
            expectFalse = listOf(TestData(name = "test")),
        ),
        TestCase(
            query = where("invalid").not("test"),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
    )

    private fun isTestCaseList(): List<TestCase> = listOf(
        TestCase(
            query = where(TestData::name).`is`("test"),
            expectTrue = listOf(TestData(name = "test")),
            expectFalse = listOf(TestData(name = "!test"), TestData(name = null)),
        ),
        TestCase(
            query = where("invalid").`is`("test"),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
    )

    private fun betweenAndNotBetweenTestCaseList(): List<TestCase> = listOf(
        TestCase(
            query = where(TestData::age).between(0..10),
            expectTrue = listOf(TestData(age = 0), TestData(age = 5), TestData(age = 10)),
            expectFalse = listOf(TestData(age = -1), TestData(age = 11), TestData(age = null)),
        ),
        TestCase(
            query = where("invalid").between(0..10),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
        TestCase(
            query = where(TestData::age).notBetween(0..10),
            expectTrue = listOf(TestData(age = -1), TestData(age = 11), TestData(age = null)),
            expectFalse = listOf(TestData(age = 0), TestData(age = 5), TestData(age = 10)),
        ),
        TestCase(
            query = where("invalid").notBetween(0..10),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
    )
    private fun lestThanTestCaseList(): List<TestCase> = listOf(
        TestCase(
            query = where(TestData::age).lessThan(0),
            expectTrue = listOf(TestData(age = -1)),
            expectFalse = listOf(TestData(age = 0), TestData(age = null)),
        ),
        TestCase(
            query = where("invalid").lessThan(0),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
        TestCase(
            query = where(TestData::age).lessThanOrEquals(0),
            expectTrue = listOf(TestData(age = 0)),
            expectFalse = listOf(TestData(age = 1), TestData(age = null)),
        ),
        TestCase(
            query = where("invalid").lessThanOrEquals(0),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
    )
    private fun greaterThanTestCaseList(): List<TestCase> = listOf(
        TestCase(
            query = where(TestData::age).greaterThan(0),
            expectTrue = listOf(TestData(age = 1)),
            expectFalse = listOf(TestData(age = 0), TestData(age = null)),
        ),
        TestCase(
            query = where("invalid").greaterThan(0),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
        TestCase(
            query = where(TestData::age).greaterThanOrEquals(0),
            expectTrue = listOf(TestData(age = 0)),
            expectFalse = listOf(TestData(age = -1), TestData(age = null)),
        ),
        TestCase(
            query = where("invalid").greaterThanOrEquals(0),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
    )
    private fun isNullAndNotNullTestCaseList(): List<TestCase> = listOf(
        TestCase(
            query = where(TestData::age).isNull(),
            expectTrue = listOf(TestData(age = null)),
            expectFalse = listOf(TestData(age = 0)),
        ),
        TestCase(
            query = where("invalid").isNull(),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
        TestCase(
            query = where(TestData::age).isNotNull(),
            expectTrue = listOf(TestData(age = 0)),
            expectFalse = listOf(TestData(age = null)),
        ),
        TestCase(
            query = where("invalid").isNotNull(),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
    )
    private fun likeAndNotLikeTestCaseList(): List<TestCase> = listOf(
        TestCase(
            query = where(TestData::name).like("%test%"),
            expectTrue = listOf(TestData(name = "testtesttest")),
            expectFalse = listOf(TestData(name = "any"), TestData(name = null)),
        ),
        TestCase(
            query = where("invalid").like("%test%"),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
        TestCase(
            query = where(TestData::name).notLike("%test%"),
            expectTrue = listOf(TestData(name = "any")),
            expectFalse = listOf(TestData(name = "testtesttest"), TestData(name = null)),
        ),
        TestCase(
            query = where("invalid").notLike("%test%"),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
        // ilike tests
        TestCase(
            query = where(TestData::name).ilike("%test%"),
            expectTrue = listOf(TestData(name = "IneedTotestThis")),
            expectFalse = listOf(
                TestData(name = "any"),
                TestData(name = null),
                TestData(name = "TESTTESTTEST"),
            ),
        ),
        TestCase(
            query = where("invalid").ilike("%test%"),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
    )
    private fun regexpAndNotRegexpTestCaseList(): List<TestCase> = listOf(
        TestCase(
            query = where(TestData::name).regexp(Pattern.compile(".*test.*")),
            expectTrue = listOf(TestData(name = "testtesttest")),
            expectFalse = listOf(TestData(name = "any"), TestData(name = null)),
        ),
        TestCase(
            query = where("invalid").regexp(Pattern.compile(".*test.*")),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
        TestCase(
            query = where(TestData::name).notRegexp(Pattern.compile(".*test.*")),
            expectTrue = listOf(TestData(name = "any")),
            expectFalse = listOf(TestData(name = "testtesttest"), TestData(name = null)),
        ),
        TestCase(
            query = where("invalid").notRegexp(Pattern.compile(".*test.*")),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
    )
    private fun inAndNotInTestCaseList(): List<TestCase> = listOf(
        TestCase(
            query = where(TestData::name).`in`("test1", "test2"),
            expectTrue = listOf(TestData(name = "test1"), TestData(name = "test2")),
            expectFalse = listOf(TestData(name = "any"), TestData(name = null)),
        ),
        TestCase(
            query = where("invalid").`in`("test1", "test2"),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
        TestCase(
            query = where(TestData::name).notIn("test1", "test2"),
            expectTrue = listOf(TestData(name = "any")),
            expectFalse = listOf(
                TestData(name = "test1"),
                TestData(name = "test2"),
                TestData(name = null),
            ),
        ),
        TestCase(
            query = where("invalid").notIn("test1", "test2"),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
    )
    private fun isTruAndIsFalseTestCaseList(): List<TestCase> = listOf(
        TestCase(
            query = where(TestData::activate).isTrue(),
            expectTrue = listOf(TestData(activate = true)),
            expectFalse = listOf(TestData(activate = false), TestData(activate = null)),
        ),
        TestCase(
            query = where("invalid").isTrue(),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
        TestCase(
            query = where(TestData::activate).isFalse(),
            expectTrue = listOf(TestData(activate = false)),
            expectFalse = listOf(TestData(activate = true), TestData(activate = null)),
        ),
        TestCase(
            query = where("invalid").isFalse(),
            expectTrue = listOf(),
            expectFalse = listOf(TestData()),
        ),
    )
}
