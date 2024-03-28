import { chunk } from './chunk';

describe('chunk', () => {
	it('should divide an array into chunks of the specified size', () => {
		const result = chunk(['a', 'b', 'c', 'd'], 2);
		expect(result).toEqual([
			['a', 'b'],
			['c', 'd'],
		]);
	});

	it('should handle arrays that cannot be divided evenly', () => {
		const result = chunk(['a', 'b', 'c', 'd'], 3);
		expect(result).toEqual([['a', 'b', 'c'], ['d']]);
	});

	it('should return an empty array when given an empty array', () => {
		const result = chunk([], 2);
		expect(result).toEqual([]);
	});

	it('should return the original array as a single chunk when the chunk size is greater than the array length', () => {
		const result = chunk(['a', 'b', 'c', 'd'], 5);
		expect(result).toEqual([['a', 'b', 'c', 'd']]);
	});

	it('should return an array of single-item arrays when the chunk size is 1', () => {
		const result = chunk(['a', 'b', 'c', 'd'], 1);
		expect(result).toEqual([['a'], ['b'], ['c'], ['d']]);
	});
});
