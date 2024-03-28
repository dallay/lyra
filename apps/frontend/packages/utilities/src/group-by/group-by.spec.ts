import { groupBy } from './group-by';

describe('groupBy', () => {
	it('groups items by string length', () => {
		const result = groupBy(['one', 'two', 'three'], (item) => item.length);
		expect(result).toEqual({ 3: ['one', 'two'], 5: ['three'] });
	});

	it('groups items by number floor', () => {
		const result = groupBy([1.3, 2.1, 2.4], (num) => Math.floor(num));
		expect(result).toEqual({ 1: [1.3], 2: [2.1, 2.4] });
	});

	it('returns an empty object for an empty array', () => {
		const result = groupBy([], (item) => item);
		expect(result).toEqual({});
	});

	it('groups items by identity for non-array items', () => {
		const result = groupBy([1, 2, 2, 3, 3, 3], (num) => num);
		expect(result).toEqual({ 1: [1], 2: [2, 2], 3: [3, 3, 3] });
	});
});
