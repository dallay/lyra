import { BasicFilter, ColumnProperty } from '../Filter';
import { FilterOperator, textOperators } from '../FilterOperator';
import { beforeEach, describe, expect } from 'vitest';

describe('BasicFilter', () => {
	let filter: BasicFilter<ColumnProperty>;

	beforeEach(() => {
		filter = new BasicFilter<ColumnProperty>();
	});

	it('should add a property', () => {
		const property: ColumnProperty = {
			key: 'testKey',
			name: 'testName',
			value: 'testValue',
			operator: FilterOperator.EQUAL,
			availableOperators: textOperators,
		};

		filter.addProperty(property);

		expect(filter.properties).to.include(property);
	});

	it('should remove a property by key', () => {
		const property: ColumnProperty = {
			key: 'testKey',
			name: 'testName',
			value: 'testValue',
			operator: FilterOperator.EQUAL,
			availableOperators: textOperators,
		};

		filter.addProperty(property);
		filter.removeProperty('testKey');

		expect(filter.properties).to.not.include(property);
	});

	it('should not remove a property if key does not exist', () => {
		const property: ColumnProperty = {
			key: 'testKey',
			name: 'testName',
			value: 'testValue',
			operator: FilterOperator.EQUAL,
			availableOperators: textOperators,
		};

		filter.addProperty(property);
		filter.removeProperty('nonExistentKey');

		expect(filter.properties).to.include(property);
	});

	it('should remove all properties', () => {
		const property1: ColumnProperty = {
			key: 'testKey1',
			name: 'testName1',
			value: 'testValue1',
			operator: FilterOperator.EQUAL,
			availableOperators: textOperators,
		};

		const property2: ColumnProperty = {
			key: 'testKey2',
			name: 'testName2',
			value: 'testValue2',
			operator: FilterOperator.EQUAL,
			availableOperators: textOperators,
		};

		filter.addProperty(property1);
		filter.addProperty(property2);
		filter.removeAllProperties();

		expect(filter.properties).to.be.empty;
	});

	it('should convert properties to query string', () => {
		const property: ColumnProperty = {
			key: 'testKey',
			name: 'testName',
			value: 'testValue',
			operator: FilterOperator.EQUAL,
			availableOperators: textOperators,
		};

		filter.addProperty(property);

		const queryString = filter.toQueryString();

		expect(queryString).to.equal('testKey=eq:testValue');
	});

	it('should not include empty, null or undefined values in query string', () => {
		const property1: ColumnProperty = {
			key: 'testKey1',
			name: 'testName1',
			value: '',
			operator: FilterOperator.EQUAL,
			availableOperators: textOperators,
		};

		const property2: ColumnProperty = {
			key: 'testKey2',
			name: 'testName2',
			value: null,
			operator: FilterOperator.EQUAL,
			availableOperators: textOperators,
		};

		const property3: ColumnProperty = {
			key: 'testKey3',
			name: 'testName3',
			value: undefined,
			operator: FilterOperator.EQUAL,
			availableOperators: textOperators,
		};

		filter.addProperty(property1);
		filter.addProperty(property2);
		filter.addProperty(property3);

		const queryString = filter.toQueryString();

		expect(queryString).to.equal('');
	});
});
