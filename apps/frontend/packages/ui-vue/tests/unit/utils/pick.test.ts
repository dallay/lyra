import { test, expect } from 'vitest';
import pick from '../../../src/utils/pick';

test('pick function - returns an object with picked properties', () => {
	const object = { a: 1, b: '2', c: 3 };
	const result = pick(object, 'a', 'c');
	expect(result).toEqual({ a: 1, c: 3 });
});

test('pick function - returns an empty object when source object is null', () => {
	const result = pick(null, 'a', 'c');
	expect(result).toEqual({});
});

test('pick function - returns an empty object when no properties are picked', () => {
	const object = { a: 1, b: '2', c: 3 };
	const result = pick(object);
	expect(result).toEqual({});
});

test('pick function - ignores non-existing properties', () => {
	const object = { a: 1, b: '2', c: 3 };
	const result = pick(object, 'a', 'd');
	expect(result).toEqual({ a: 1 });
});
