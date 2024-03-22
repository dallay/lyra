import { FilterOperator, filterOperatorFromString, getSupportedOperators } from '../FilterOperator';
import { describe, expect } from 'vitest';

describe('FilterOperator', () => {
	it('should convert string to FilterOperator', () => {
		const operator = filterOperatorFromString('eq');
		expect(operator).to.equal(FilterOperator.EQUAL);
	});

	it('should throw error for invalid string', () => {
		expect(() => filterOperatorFromString('invalid')).to.throw(
			Error,
			'Invalid filter operator: invalid'
		);
	});

	it('should return supported operators for string', () => {
		const operators = getSupportedOperators('string');
		expect(operators).to.deep.equal([
			{ label: 'Equal', value: FilterOperator.EQUAL },
			{ label: 'Not Equal', value: FilterOperator.NOT_EQUAL },
			{ label: 'Like', value: FilterOperator.LIKE },
		]);
	});

	it('should return supported operators for number', () => {
		const operators = getSupportedOperators(123);
		expect(operators).to.deep.equal([
			{ label: 'Equal', value: FilterOperator.EQUAL },
			{ label: 'Not Equal', value: FilterOperator.NOT_EQUAL },
			{ label: 'Greater Than', value: FilterOperator.GREATER_THAN },
			{ label: 'Greater Than or Equal', value: FilterOperator.GREATER_THAN_OR_EQUAL },
			{ label: 'Less Than', value: FilterOperator.LESS_THAN },
			{ label: 'Less Than or Equal', value: FilterOperator.LESS_THAN_OR_EQUAL },
		]);
	});

	it('should return supported operators for date', () => {
		const operators = getSupportedOperators(new Date());
		expect(operators).to.deep.equal([
			{ label: 'Equal', value: FilterOperator.EQUAL },
			{ label: 'Not Equal', value: FilterOperator.NOT_EQUAL },
			{ label: 'Greater Than', value: FilterOperator.GREATER_THAN },
			{ label: 'Greater Than or Equal', value: FilterOperator.GREATER_THAN_OR_EQUAL },
			{ label: 'Less Than', value: FilterOperator.LESS_THAN },
			{ label: 'Less Than or Equal', value: FilterOperator.LESS_THAN_OR_EQUAL },
		]);
	});

	it('should return empty array for unsupported type', () => {
		const operators = getSupportedOperators({});
		expect(operators).to.deep.equal([]);
	});
});
