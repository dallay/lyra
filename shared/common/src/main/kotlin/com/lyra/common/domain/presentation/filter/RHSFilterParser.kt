package com.lyra.common.domain.presentation.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.FilterInvalidException
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import org.slf4j.LoggerFactory

@Suppress("UNCHECKED_CAST")
class RHSFilterParser<T : Any>(
    private val clazz: KClass<T>,
    private val objectMapper: ObjectMapper
) {
    private val regex = Regex("(.[^:]+):(.+)")

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    fun parse(query: Map<KProperty1<T, *>, Collection<String?>?>, useOr: Boolean = false): Criteria {
        try {
            val criteriaList: List<Criteria> = query
                .mapNotNull { (key, values) -> processQueryEntry(key, values) }
                .flatten()

            val criteria =
                if (criteriaList.isEmpty()) {
                    Criteria.Empty
                } else if (useOr) {
                    Criteria.Or(criteriaList)
                } else {
                    Criteria.And(
                        criteriaList,
                    )
                }
            return criteria
        } catch (e: Exception) {
            log.error("Error parsing query: {}", query, e)
            throw FilterInvalidException(e.message)
        }
    }

    private fun processQueryEntry(
        key: KProperty1<T, *>,
        values: Collection<String?>?
    ): List<Criteria> {
        val property = clazz.memberProperties.find { it == key } ?: return emptyList()
        val clazz = property.returnType.classifier as? KClass<*>
            ?: throw FilterInvalidException("Can't find operand type. Property: $property")

        return values?.filterNotNull()?.map { value -> processValue(property, clazz, value) }
            ?: emptyList()
    }

    private fun processValue(
        property: KProperty1<T, *>,
        clazz: KClass<*>,
        value: String
    ): Criteria {
        val result = regex.find(value) ?: throw FilterInvalidException()
        val (operator, operand) = result.destructured

        val parsed = convert(operand, clazz)
        return create(property, operator, parsed)
    }

    private fun convert(operand: String, clazz: KClass<*>): Any {
        val candidates = listOfNotNull(
            operand,
            operand.toIntOrNull(),
            operand.toLongOrNull(),
            operand.toBooleanStrictOrNull(),
        )
        var converted: Any? = null
        for (candidate in candidates) {
            try {
                converted = objectMapper.convertValue(candidate, clazz.java)
                break
            } catch (_: RuntimeException) {
            }
        }

        if (converted == null) {
            throw FilterInvalidException("Can't convert operand. Operand: $operand, Type: $clazz")
        }

        return converted
    }

    private fun create(property: KProperty1<T, *>, operator: String, value: Any): Criteria {
        return when (operator) {
            "ne" -> Criteria.NotEquals(property.name, value)
            "eq" -> Criteria.Equals(property.name, value)
            "lk" -> Criteria.Like(property.name, value as String)
            "gt" -> Criteria.GreaterThan(property.name, value as Comparable<Any?>)
            "gte" -> Criteria.GreaterThanEquals(property.name, value as Comparable<Any?>)
            "lt" -> Criteria.LessThan(property.name, value as Comparable<Any?>)
            "lte" -> Criteria.LessThanEquals(property.name, value as Comparable<Any?>)
            else -> throw FilterInvalidException("Not support operator.")
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(RHSFilterParser::class.java)
    }
}
