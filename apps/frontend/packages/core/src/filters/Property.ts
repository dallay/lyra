import { FilterType } from '@/filters/Filter.ts';
import { FilterOperator, FilterOperatorOption } from '@/filters/FilterOperator.ts';
import { allFilterOperators } from '../../dist';

/**
 * Interface for Property.
 * @interface
 * @property {string} id - The id of the property.
 * @property {string} name - The name of the property.
 * @property {string} label - The label of the property.
 * @property {FilterType} type - The type of the property.
 * @property {FilterOperator} operator - The operator of the property.
 * @property {T} value - The value of the property.
 * @property {FilterOperatorOption[]} availableOperators - The available operators of the property.
 * @property {T[]} [options] - The options of the property.
 */
export interface Property<T> {
	id: string;
	name: string;
	label: string;
	type: FilterType;
	operator: FilterOperator;
	value?: T | T[] | null;
	availableOperators: FilterOperatorOption[];
	options?: T[];
}

/**
 * Interface for FieldProperty.
 * @interface
 * @property {string} name - The name of the field property.
 * @property {string} label - The label of the field property.
 * @property {FilterType} type - The type of the field property.
 * @property {T} value - The value of the field property.
 * @property {T[]} [options] - The options of the field property.
 */
export interface FieldProperty<T> {
	name: string;
	label: string;
	type: FilterType;
	value: T;
	options?: T[];
}

export function convertFieldPropertyToProperty<T>(fieldProperty: FieldProperty<T>): Property<T> {
	return {
		...fieldProperty,
		operator: allFilterOperators[0].value,
		availableOperators: allFilterOperators,
		id: crypto.randomUUID(),
	};
}

export function convertPropertyToFieldProperty<T>(property: Property<T>): FieldProperty<T> {
	return {
		name: property.name,
		label: property.label,
		type: property.type,
		value: property.value as T,
	};
}
