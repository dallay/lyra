/**
 * Enum for filter operators.
 * @enum {string}
 */
export enum FilterOperator {
	EQUAL = 'eq',
	NOT_EQUAL = 'ne',
	GREATER_THAN = 'gt',
	GREATER_THAN_OR_EQUAL = 'gte',
	LESS_THAN = 'lt',
	LESS_THAN_OR_EQUAL = 'lte',
	LIKE = 'lk',
}

export function filterOperatorFromString(value: string): FilterOperator {
	switch (value) {
		case 'eq':
			return FilterOperator.EQUAL;
		case 'ne':
			return FilterOperator.NOT_EQUAL;
		case 'gt':
			return FilterOperator.GREATER_THAN;
		case 'gte':
			return FilterOperator.GREATER_THAN_OR_EQUAL;
		case 'lt':
			return FilterOperator.LESS_THAN;
		case 'lte':
			return FilterOperator.LESS_THAN_OR_EQUAL;
		case 'lk':
			return FilterOperator.LIKE;
		default:
			throw new Error(`Invalid filter operator: ${value}`);
	}
}

/**
 * Interface for filter operator options.
 * @interface
 * @property {string} label - The label of the filter operator.
 * @property {FilterOperator} value - The value of the filter operator.
 */
export interface FilterOperatorOption {
	label: string;
	value: FilterOperator;
}

/**
 * Array of all filter operator options.
 * @type {FilterOperatorOption[]}
 */
export const allFilterOperators: FilterOperatorOption[] = [
	{ label: 'Equal', value: FilterOperator.EQUAL },
	{ label: 'Not Equal', value: FilterOperator.NOT_EQUAL },
	{ label: 'Greater Than', value: FilterOperator.GREATER_THAN },
	{ label: 'Greater Than or Equal', value: FilterOperator.GREATER_THAN_OR_EQUAL },
	{ label: 'Less Than', value: FilterOperator.LESS_THAN },
	{ label: 'Less Than or Equal', value: FilterOperator.LESS_THAN_OR_EQUAL },
	{ label: 'Like', value: FilterOperator.LIKE },
];

export const textOperators: FilterOperatorOption[] = [
	{ label: 'Equal', value: FilterOperator.EQUAL },
	{ label: 'Not Equal', value: FilterOperator.NOT_EQUAL },
	{ label: 'Like', value: FilterOperator.LIKE },
];

export const numberOperators: FilterOperatorOption[] = [
	{ label: 'Equal', value: FilterOperator.EQUAL },
	{ label: 'Not Equal', value: FilterOperator.NOT_EQUAL },
	{ label: 'Greater Than', value: FilterOperator.GREATER_THAN },
	{ label: 'Greater Than or Equal', value: FilterOperator.GREATER_THAN_OR_EQUAL },
	{ label: 'Less Than', value: FilterOperator.LESS_THAN },
	{ label: 'Less Than or Equal', value: FilterOperator.LESS_THAN_OR_EQUAL },
];

export const dateOperators: FilterOperatorOption[] = [
	{ label: 'Equal', value: FilterOperator.EQUAL },
	{ label: 'Not Equal', value: FilterOperator.NOT_EQUAL },
	{ label: 'Greater Than', value: FilterOperator.GREATER_THAN },
	{ label: 'Greater Than or Equal', value: FilterOperator.GREATER_THAN_OR_EQUAL },
	{ label: 'Less Than', value: FilterOperator.LESS_THAN },
	{ label: 'Less Than or Equal', value: FilterOperator.LESS_THAN_OR_EQUAL },
];

export function getSupportedOperators(value: unknown): FilterOperatorOption[] {
	let supportedOperators: FilterOperatorOption[] = [];
	if (typeof value === 'string') {
		supportedOperators = textOperators;
	} else if (typeof value === 'number') {
		supportedOperators = numberOperators;
	} else if (value instanceof Date) {
		supportedOperators = dateOperators;
	}
	return supportedOperators;
}
