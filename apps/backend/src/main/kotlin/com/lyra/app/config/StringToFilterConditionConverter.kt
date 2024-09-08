package com.lyra.app.config

import com.lyra.common.domain.presentation.pagination.FilterCondition
import com.lyra.common.domain.presentation.pagination.LogicalOperator
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * A Spring component that converts a String to a FilterCondition.
 * This converter is used to parse filter conditions from string representations.
 */
@Component
class StringToFilterConditionConverter : Converter<String, FilterCondition> {

    /**
     * Converts a string representation of a filter condition to a FilterCondition object.
     *
     * @param source the string representation of the filter condition.
     *               The string should start with "AND:" or "OR:" followed by comma-separated values.
     *               If no logical operator is specified, "AND" is used by default.
     * @return a FilterCondition object containing the logical operator and the list of values.
     */
    override fun convert(source: String): FilterCondition {
        val operator: LogicalOperator
        val values: List<String>

        if (source.startsWith("AND:", ignoreCase = true)) {
            operator = LogicalOperator.AND
            values = source.removePrefix("AND:").split(",")
        } else if (source.startsWith("OR:", ignoreCase = true)) {
            operator = LogicalOperator.OR
            values = source.removePrefix("OR:").split(",")
        } else {
            operator = LogicalOperator.AND
            values = source.split(",")
        }

        return FilterCondition(operator, values)
    }
}
