package com.lyra.common.domain.criteria

import java.util.regex.Pattern
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CriteriaTest {
    @Test
    fun equals() {
        val a = Criteria.And(
            listOf(
                Criteria.Equals("a", "a"),
                Criteria.Equals("b", "b"),
                Criteria.Empty,
            ),
        )
        val b = Criteria.And(
            listOf(
                Criteria.Equals("a", "a"),
                Criteria.Equals("b", "b"),
                Criteria.Empty,
            ),
        )

        assertEquals(a, b)
    }

    @Test
    fun notEquals() {
        val a = Criteria.And(
            listOf(
                Criteria.Equals("a", "a"),
                Criteria.Equals("b", "b"),
                Criteria.Empty,
            ),
        )
        val b = Criteria.And(
            listOf(
                Criteria.Equals("a", "a"),
                Criteria.Equals("b", "b"),
                Criteria.Empty,
            ),
        )

        assertEquals(a, b)
    }

    @Test
    fun testEmptyCriteria() {
        assertEquals("()", Criteria.Empty.toString())
    }

    @Test
    fun testAndCriteria() {
        val criteria = Criteria.And(
            listOf(
                Criteria.Equals("name", "John"),
                Criteria.GreaterThan("age", 25),
            ),
        )
        assertEquals("(name = John AND age > 25)", criteria.toString())
    }

    @Test
    fun testIsTrueCriteria() {
        val criteria = Criteria.IsTrue("is_active")
        assertEquals("is_active IS TRUE", criteria.toString())
    }

    @Test
    fun testIsFalseCriteria() {
        val criteria = Criteria.IsFalse("is_deleted")
        assertEquals("is_deleted IS FALSE", criteria.toString())
    }

    @Test
    fun testOrCriteria() {
        val criteria = Criteria.Or(
            listOf(
                Criteria.Equals("name", "John"),
                Criteria.GreaterThan("age", 25),
            ),
        )
        assertEquals("(name = John OR age > 25)", criteria.toString())
    }

    @Test
    fun testEqualsCriteria() {
        val criteria = Criteria.Equals("name", "John")
        assertEquals("name = John", criteria.toString())
    }

    @Test
    fun testNotEqualsCriteria() {
        val criteria = Criteria.NotEquals("name", "John")
        assertEquals("name != John", criteria.toString())
    }

    @Test
    fun testBetweenCriteria() {
        val criteria = Criteria.Between("age", 25..30)
        assertEquals("(age >= 25 && age <= 30)", criteria.toString())
    }

    @Test
    fun testNotBetweenCriteria() {
        val criteria = Criteria.NotBetween("age", 25..30)
        assertEquals("(age < 25 || age > 30)", criteria.toString())
    }

    @Test
    fun testGreaterThanCriteria() {
        val criteria = Criteria.GreaterThan("age", 25)
        assertEquals("age > 25", criteria.toString())
    }

    @Test
    fun testGreaterThanOrEqualsCriteria() {
        val criteria = Criteria.GreaterThanEquals("age", 25)
        assertEquals("age >= 25", criteria.toString())
    }

    @Test
    fun testLessThanCriteria() {
        val criteria = Criteria.LessThan("age", 25)
        assertEquals("age < 25", criteria.toString())
    }

    @Test
    fun testLessThanOrEqualsCriteria() {
        val criteria = Criteria.LessThanEquals("age", 25)
        assertEquals("age <= 25", criteria.toString())
    }

    @Test
    fun testIsNullCriteria() {
        val criteria = Criteria.IsNull("name")
        assertEquals("name = null", criteria.toString())
    }

    @Test
    fun testIsNotNullCriteria() {
        val criteria = Criteria.IsNotNull("name")
        assertEquals("name != null", criteria.toString())
    }

    @Test
    fun testLikeCriteria() {
        val criteria = Criteria.Like("name", "%John%")
        assertEquals("name LIKE %John%", criteria.toString())
    }

    @Test
    fun testIlikeCriteria() {
        val criteria = Criteria.Ilike("name", "%John%")
        assertEquals("name ILIKE %John%", criteria.toString())
    }

    @Test
    fun testNotLikeCriteria() {
        val criteria = Criteria.NotLike("name", "%John%")
        assertEquals("name NOT LIKE %John%", criteria.toString())
    }

    @Test
    fun testRegexpCriteria() {
        val value: Pattern = Pattern.compile("John")
        val criteria = Criteria.Regexp("name", value)
        assertEquals("name REGEXP John", criteria.toString())
    }

    @Test
    fun testNotRegexpCriteria() {
        val value: Pattern = Pattern.compile("John")
        val criteria = Criteria.NotRegexp("name", value)
        assertEquals("name NOT REGEXP John", criteria.toString())
    }

    @Test
    fun testInCriteria() {
        val criteria = Criteria.In("name", listOf("John", "Jane"))
        assertEquals("name IN [John, Jane]", criteria.toString())
    }

    @Test
    fun testNotInCriteria() {
        val criteria = Criteria.NotIn("name", listOf("John", "Jane"))
        assertEquals("name NOT IN [John, Jane]", criteria.toString())
    }

    @Test
    fun testComplexCriteriaWithMultipleNestedActions() {
        val criteria = Criteria.And(
            listOf(
                Criteria.Equals("name", "John"),
                Criteria.Or(
                    listOf(
                        Criteria.Equals("age", 25),
                        Criteria.Equals("age", 30),
                    ),
                ),
                Criteria.Equals("is_active", true),
            ),
        )
        assertEquals(
            "(name = John AND (age = 25 OR age = 30) AND is_active = true)",
            criteria.toString(),
        )
    }
}
