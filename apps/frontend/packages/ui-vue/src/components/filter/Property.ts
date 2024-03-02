import { type FilterType } from './Filter';
import { FilterOperator, type FilterOperatorOption } from './FilterOperator';

/**
 * Interface for Property.
 * @type
 * @property {string} id - The id of the property.
 * @property {string} name - The name of the property.
 * @property {string} label - The label of the property.
 * @property {FilterType} type - The type of the property.
 * @property {FilterOperator} operator - The operator of the property.
 * @property {T} value - The value of the property.
 * @property {FilterOperatorOption[]} availableOperators - The available operators of the property.
 * @property {T[]} [options] - The options of the property.
 */
export type Property<T> = {
	id: string;
	name: string;
	label: string;
	type: FilterType;
	operator: FilterOperator;
	value?: T;
	availableOperators: FilterOperatorOption[];
	options?: T[];
};
