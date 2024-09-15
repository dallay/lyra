package com.lyra.common.domain.criteria

import com.lyra.common.domain.regexp.SqlLikeTranspiler
import java.util.regex.Pattern
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * This class is a parser for criteria that are evaluated at runtime.
 * It uses reflection to parse the criteria and return a lambda function that can be used to evaluate the criteria.
 *
 * @param T The type of the object to evaluate the criteria.
 * @property clazz The class of the object to evaluate the criteria.
 * @property properties A map of the properties of the class.
 * @constructor Creates a new RuntimeCriteriaParser.
 */
@Suppress("MethodOverloading")
class RuntimeCriteriaParser<T : Any>(
    clazz: KClass<T>
) : CriteriaParser<((T) -> Boolean)?> {
    private val properties = clazz.memberProperties.associateBy { it.name }

    @Suppress("CyclomaticComplexMethod", "CognitiveComplexMethod")
    override fun parse(criteria: Criteria): ((T) -> Boolean)? {
        return when (criteria) {
            is Criteria.Empty -> null
            is Criteria.And -> parse(criteria)
            is Criteria.Or -> parse(criteria)
            is Criteria.Equals -> parse(criteria)
            is Criteria.NotEquals -> parse(criteria)
            is Criteria.Between -> parse(criteria)
            is Criteria.NotBetween -> parse(criteria)
            is Criteria.LessThan -> parse(criteria)
            is Criteria.LessThanEquals -> parse(criteria)
            is Criteria.GreaterThan -> parse(criteria)
            is Criteria.GreaterThanEquals -> parse(criteria)
            is Criteria.IsNull -> parse(criteria)
            is Criteria.IsNotNull -> parse(criteria)
            is CriteriaLike -> parse(criteria)
            is Criteria.NotLike -> parse(criteria)
            is Criteria.Regexp -> parse(criteria)
            is Criteria.NotRegexp -> parse(criteria)
            is Criteria.In -> parse(criteria)
            is Criteria.NotIn -> parse(criteria)
            is Criteria.IsTrue -> parse(criteria)
            is Criteria.IsFalse -> parse(criteria)
        }
    }

    private fun parse(criteria: Criteria.And): ((T) -> Boolean)? {
        if (criteria.value.isEmpty()) {
            return null
        }
        return criteria.value.mapNotNull { parse(it) }
            .reduce { acc, cur -> { acc(it) && cur(it) } }
    }

    private fun parse(criteria: Criteria.Or): ((T) -> Boolean)? {
        if (criteria.value.isEmpty()) {
            return null
        }
        return criteria.value.mapNotNull { parse(it) }
            .reduce { acc, cur -> { acc(it) || cur(it) } }
    }

    private fun parse(criteria: Criteria.Equals): (T) -> Boolean {
        return {
            properties[criteria.key]?.let { property ->
                property.get(it) == criteria.value
            } == true
        }
    }

    private fun parse(criteria: Criteria.NotEquals): (T) -> Boolean {
        return {
            properties[criteria.key]?.let { property ->
                property.get(it) != criteria.value
            } == true
        }
    }

    private fun parse(criteria: Criteria.Between): (T) -> Boolean {
        return {
            properties[criteria.key]?.let { property ->
                property.get(it)?.let { value ->
                    if (value is Comparable<*>) {
                        @Suppress("UNCHECKED_CAST")
                        value as Comparable<Any?>
                        value >= criteria.value.start && value <= criteria.value.endInclusive
                    } else {
                        false
                    }
                } == true
            } == true
        }
    }

    private fun parse(criteria: Criteria.NotBetween): (T) -> Boolean {
        return {
            properties[criteria.key]?.let { property ->
                property.get(it)?.let { value ->
                    if (value is Comparable<*>) {
                        @Suppress("UNCHECKED_CAST")
                        value as Comparable<Any?>
                        value < criteria.value.start || value > criteria.value.endInclusive
                    } else {
                        false
                    }
                } != false
            } == true
        }
    }

    private fun parse(criteria: Criteria.LessThan): (T) -> Boolean {
        return {
            properties[criteria.key]?.let { property ->
                property.get(it)?.let { value ->
                    if (value is Comparable<*>) {
                        @Suppress("UNCHECKED_CAST")
                        value as Comparable<Any?>
                        value < criteria.value
                    } else {
                        false
                    }
                } == true
            } == true
        }
    }

    private fun parse(criteria: Criteria.LessThanEquals): (T) -> Boolean {
        return {
            properties[criteria.key]?.let { property ->
                property.get(it)?.let { value ->
                    if (value is Comparable<*>) {
                        @Suppress("UNCHECKED_CAST")
                        value as Comparable<Any?>
                        value <= criteria.value
                    } else {
                        false
                    }
                } == true
            } == true
        }
    }

    private fun parse(criteria: Criteria.GreaterThan): (T) -> Boolean {
        return {
            properties[criteria.key]?.let { property ->
                property.get(it)?.let { value ->
                    if (value is Comparable<*>) {
                        @Suppress("UNCHECKED_CAST")
                        value as Comparable<Any?>
                        value > criteria.value
                    } else {
                        false
                    }
                } == true
            } == true
        }
    }

    private fun parse(criteria: Criteria.GreaterThanEquals): (T) -> Boolean {
        return {
            properties[criteria.key]?.let { property ->
                property.get(it)?.let { value ->
                    if (value is Comparable<*>) {
                        @Suppress("UNCHECKED_CAST")
                        value as Comparable<Any?>
                        value >= criteria.value
                    } else {
                        false
                    }
                } == true
            } == true
        }
    }

    private fun parse(criteria: Criteria.IsNull): (T) -> Boolean {
        return {
            properties[criteria.key]?.let { property ->
                property.get(it) == null
            } == true
        }
    }

    private fun parse(criteria: Criteria.IsNotNull): (T) -> Boolean {
        return {
            properties[criteria.key]?.let { property ->
                property.get(it) != null
            } == true
        }
    }

    private fun parse(criteria: CriteriaLike, ignoreCase: Boolean = false): (T) -> Boolean {
        val regexFlags = if (ignoreCase) Pattern.CASE_INSENSITIVE else 0
        val pattern = Pattern.compile(SqlLikeTranspiler.toRegEx(criteria.value), regexFlags)

        return {
            properties[criteria.key]?.get(it)?.let { value ->
                if (value is CharSequence) {
                    pattern.matcher(value).find()
                } else {
                    false
                }
            } == true
        }
    }

    private fun parse(criteria: Criteria.Like): (T) -> Boolean = parse(criteria, ignoreCase = false)
    private fun parse(criteria: Criteria.Ilike): (T) -> Boolean = parse(criteria, ignoreCase = true)

    private fun parse(criteria: Criteria.NotLike): (T) -> Boolean {
        val pattern = Pattern.compile(SqlLikeTranspiler.toRegEx(criteria.value))
        return {
            properties[criteria.key]?.get(it)?.let { value ->
                if (value is CharSequence) {
                    !pattern.matcher(value).find()
                } else {
                    false
                }
            } == true
        }
    }

    private fun parse(criteria: Criteria.Regexp): (T) -> Boolean {
        return {
            properties[criteria.key]?.get(it)?.let { value ->
                if (value is CharSequence) {
                    criteria.value.matcher(value).find()
                } else {
                    false
                }
            } == true
        }
    }

    private fun parse(criteria: Criteria.NotRegexp): (T) -> Boolean {
        return {
            properties[criteria.key]?.get(it)?.let { value ->
                if (value is CharSequence) {
                    !criteria.value.matcher(value).find()
                } else {
                    false
                }
            } == true
        }
    }

    private fun parse(criteria: Criteria.In): (T) -> Boolean {
        return {
            properties[criteria.key]?.get(it)?.let { value ->
                criteria.value.contains(value)
            } == true
        }
    }

    private fun parse(criteria: Criteria.NotIn): (T) -> Boolean {
        return {
            properties[criteria.key]?.get(it)?.let { value ->
                !criteria.value.contains(value)
            } == true
        }
    }

    private fun parse(criteria: Criteria.IsTrue): (T) -> Boolean {
        return {
            properties[criteria.key]?.get(it)?.let { value ->
                value == true
            } == true
        }
    }

    private fun parse(criteria: Criteria.IsFalse): (T) -> Boolean {
        return {
            properties[criteria.key]?.get(it)?.let { value ->
                value == false
            } == true
        }
    }
}
