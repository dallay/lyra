import { Property } from '@/filters/Property.ts';

/**
 * Interface for filter options.
 * @interface
 * @property {string} label - The label of the filter.
 * @property {string} [placeholder] - The placeholder of the filter.
 * @property {string} [type] - The type of the filter.
 * @property {string[]} [options] - The options of the filter.
 */
export interface FilterOptions {
	label: string;
	placeholder?: string;
	type?: string;
	options?: string[];
}
/**
 * Type for filter type.
 * @typedef {('text'|'email'|'tel'|'select'|'url'|'number'|'password'|'checkbox'|'date')} FilterType
 */
export type FilterType =
	| 'text'
	| 'email'
	| 'tel'
	| 'select'
	| 'url'
	| 'number'
	| 'password'
	| 'checkbox'
	| 'date';

/**
 * Interface for filter.
 * @interface
 * @property {string} name - The name of the filter.
 * @property {Property<T>[]} properties - The properties of the filter.
 * @property {(id: string) => void} removeProperty - The function to remove a property.
 * @property {() => void} removeAllProperties - The function to remove all properties.
 * @property {(property: Property<T>) => void} addProperty - The function to add a property.
 * @property {() => string} toQueryString - The function to convert properties to query string.
 */
export interface Filter<T> {
	name: string;
	properties: Property<T>[];
	removeProperty: (id: string) => void;
	removeAllProperties: () => void;
	addProperty: (property: Property<T>) => void;
	toQueryString: () => string;
}
/**
 * Class for basic filter.
 * @class
 * @implements {Filter<T>}
 * @property {string} name - The name of the filter.
 * @property {Property<T>[]} properties - The properties of the filter.
 */
export class BasicFilter<T> implements Filter<T> {
	name: string;
	properties: Property<T>[];

	/**
	 * Create a basic filter.
	 * @constructor
	 * @param {string} name - The name of the filter.
	 * @param {Property<T>[]} properties - The properties of the filter.
	 */
	constructor(name: string, properties: Property<T>[] = []) {
		this.name = name;
		this.properties = properties;
	}

	/**
	 * Remove a property.
	 * @param {string} id - The id of the property.
	 */
	removeProperty(id: string): void {
		this.properties = this.properties.filter((property) => property.id !== id);
	}

	/**
	 * Remove all properties.
	 */
	removeAllProperties(): void {
		this.properties = [];
	}

	/**
	 * Add a property.
	 * @param {Property<T>} property - The property to add.
	 */
	addProperty(property: Property<T>): void {
		this.properties.push(property);
	}

	/**
	 * Convert properties to query string.
	 * @returns {string} The query string.
	 */
	toQueryString(): string {
		return this.properties
			.filter(
				(property) =>
					property.value !== '' && property.value !== null && property.value !== undefined
			)
			.map((property) => {
				return `${property.name}=${property.operator}:${property.value}`;
			})
			.join('&');
	}
}
