import { FilterOperator, FilterOperatorOption } from './FilterOperator';
export type InputType =
	| 'text'
	| 'email'
	| 'tel'
	| 'select'
	| 'url'
	| 'number'
	| 'password'
	| 'checkbox'
	| 'date';
export type ColumnProperty = {
	key: string;
	name: string;
	value: unknown;
	operator: FilterOperator;
	availableOperators: FilterOperatorOption[];
	icon?: string;
	inputType?: InputType;
	options?: SelectOption[];
};

export type SelectOption = {
	label: string;
	value: string;
};

export interface Filter<PROPERTY extends ColumnProperty> {
	properties: PROPERTY[];
	removeProperty: (key: string) => void;
	removeAllProperties: () => void;
	addProperty: (property: PROPERTY) => void;
	toQueryString: () => string;
}

export class BasicFilter<PROPERTY extends ColumnProperty> implements Filter<PROPERTY> {
	properties: PROPERTY[];

	constructor(properties: PROPERTY[] = []) {
		this.properties = properties;
	}

	removeProperty(key: string) {
		this.properties = this.properties.filter((p) => p.key !== key);
	}

	removeAllProperties() {
		this.properties = [];
	}

	addProperty(property: PROPERTY) {
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
				let value = property.value;
				// if the value is a date, convert it to local date string format (2024-02-20T17:14:00Z)
				if (value instanceof Date) {
					value = value.toISOString();
				}
				return `${property.key}=${property.operator}:${value}`;
			})
			.join('&');
	}
}
