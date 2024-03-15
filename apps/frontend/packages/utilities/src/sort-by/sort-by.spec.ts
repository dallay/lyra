import { sortBy } from './sort-by';

describe('sortBy', () => {
	it('sorts items by string', () => {
		const result = sortBy([{ name: 'banana' }, { name: 'apple' }], 'name');
		expect(result).toEqual([{ name: 'apple' }, { name: 'banana' }]);
	});

	it('sorts items by number using a key getter function', () => {
		const result = sortBy(
			[
				{ name: 'banana', age: 2 },
				{ name: 'apple', age: 1 },
			],
			(item) => item.age
		);
		expect(result).toEqual([
			{ name: 'apple', age: 1 },
			{ name: 'banana', age: 2 },
		]);
	});

	it('returns an empty array for an empty array input', () => {
		const result = sortBy([], 'name');
		expect(result).toEqual([]);
	});

	it('returns the same array for an array with one item', () => {
		const result = sortBy([{ name: 'apple' }], 'name');
		expect(result).toEqual([{ name: 'apple' }]);
	});
});
