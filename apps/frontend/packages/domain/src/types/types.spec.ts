import { type CriteriaParam, type QuerySort, buildParams } from './types';

describe('buildParams', () => {
	it('builds parameters with only size', () => {
		const params = buildParams(undefined, undefined, 20);
		expect(params).toEqual({ size: '20' });
	});

	it('builds parameters with criteria search', () => {
		const criteria: CriteriaParam = { search: 'test' };
		const params = buildParams(criteria);
		expect(params).toEqual({ search: 'test', size: '10' });
	});

	it('builds parameters with criteria filter', () => {
		const criteria: CriteriaParam = { filter: 'color=red&size=large' };
		const params = buildParams(criteria);
		expect(params).toEqual({ 'filter[color]': 'red', 'filter[size]': 'large', size: '10' });
	});

	it('builds parameters with sort', () => {
		const sort: QuerySort = {
			field: 'name',
			direction: 'asc',
			toQueryString: () => 'sort=name,asc',
		};
		const params = buildParams(undefined, sort);
		expect(params).toEqual({ sort: 'name,asc', size: '10' });
	});

	it('builds parameters with cursor', () => {
		const params = buildParams(undefined, undefined, 10, 'cursor');
		expect(params).toEqual({ size: '10', cursor: 'cursor' });
	});

	it('builds parameters with all options', () => {
		const criteria: CriteriaParam = { search: 'test', filter: 'color=red&size=large' };
		const sort: QuerySort = {
			field: 'name',
			direction: 'asc',
			toQueryString: () => 'sort=name,asc',
		};
		const params = buildParams(criteria, sort, 20, 'cursor');
		expect(params).toEqual({
			search: 'test',
			'filter[color]': 'red',
			'filter[size]': 'large',
			sort: 'name,asc',
			size: '20',
			cursor: 'cursor',
		});
	});
});
