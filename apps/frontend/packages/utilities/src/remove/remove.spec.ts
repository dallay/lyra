import { remove } from './remove';

describe('remove', () => {
	it('removes items that satisfy the predicate', () => {
		const array = [1, 2, 3, 4];
		const removed = remove(array, (n) => n % 2 === 0);
		expect(array).toEqual([1, 3]);
		expect(removed).toEqual([2, 4]);
	});

	it('returns an empty array when no items satisfy the predicate', () => {
		const array = [1, 3, 5];
		const removed = remove(array, (n) => n % 2 === 0);
		expect(array).toEqual([1, 3, 5]);
		expect(removed).toEqual([]);
	});

	it('removes all items when all items satisfy the predicate', () => {
		const array = [2, 4, 6];
		const removed = remove(array, (n) => n % 2 === 0);
		expect(array).toEqual([]);
		expect(removed).toEqual([2, 4, 6]);
	});

	it('does not modify the array when it is empty', () => {
		const array: number[] = [];
		const removed = remove(array, (n) => n % 2 === 0);
		expect(array).toEqual([]);
		expect(removed).toEqual([]);
	});
});
