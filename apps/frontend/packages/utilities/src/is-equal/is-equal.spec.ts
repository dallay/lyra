import { isEqual } from './is-equal';

describe('isEqual', () => {
	it('should return true for identical primitive values', () => {
		expect(isEqual(1, 1)).toBe(true);
		expect(isEqual('a', 'a')).toBe(true);
		expect(isEqual(true, true)).toBe(true);
	});

	it('should return false for different primitive values', () => {
		expect(isEqual(1, 2)).toBe(false);
		expect(isEqual('a', 'b')).toBe(false);
		expect(isEqual(true, false)).toBe(false);
	});

	it('should return true for identical object values', () => {
		const objA = { a: 1, b: 2 };
		const objB = { a: 1, b: 2 };
		expect(isEqual(objA, objB)).toBe(true);
	});

	it('should return false for different object values', () => {
		const objA = { a: 1, b: 2 };
		const objB = { a: 2, b: 1 };
		expect(isEqual(objA, objB)).toBe(false);
	});

	it('should return true for identical nested object values', () => {
		const objA = { a: 1, b: { c: 3 } };
		const objB = { a: 1, b: { c: 3 } };
		expect(isEqual(objA, objB)).toBe(true);
	});

	it('should return false for different nested object values', () => {
		const objA = { a: 1, b: { c: 3 } };
		const objB = { a: 1, b: { c: 4 } };
		expect(isEqual(objA, objB)).toBe(false);
	});

	it('should return true for identical arrays', () => {
		expect(isEqual([1, 2, 3], [1, 2, 3])).toBe(true);
	});

	it('should return false for different arrays', () => {
		expect(isEqual([1, 2, 3], [1, 2, 4])).toBe(false);
	});

	it('should return true for identical nested arrays', () => {
		expect(isEqual([1, [2, 3]], [1, [2, 3]])).toBe(true);
	});

	it('should return false for different nested arrays', () => {
		expect(isEqual([1, [2, 3]], [1, [2, 4]])).toBe(false);
	});
});
