import { describe, test } from 'vitest';
import { range } from './range';
describe('range', () => {
	test('returns an array of numbers from start to end with positive step', () => {
		const result = range(1, 5, 1);
		expect(result).toEqual([1, 2, 3, 4]);
	});

	test('returns an array of numbers from start to end with negative step', () => {
		const result = range(5, 1, -1);
		expect(result).toEqual([5, 4, 3, 2]);
	});

	test('returns an empty array if end is less than start and step is positive', () => {
		const result = range(5, 1, 1);
		expect(result).toEqual([]);
	});

	test('returns an empty array if start is less than end and step is negative', () => {
		const result = range(1, 5, -1);
		expect(result).toEqual([]);
	});

	test('returns an array with one element if start equals end', () => {
		const result = range(5, 5, 1);
		expect(result).toEqual([]);
	});

	test('returns an array from 0 to end if only end is provided', () => {
		const result = range(5);
		expect(result).toEqual([0, 1, 2, 3, 4]);
	});
});
