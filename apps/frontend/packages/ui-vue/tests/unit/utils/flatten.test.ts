import { test, expect } from 'vitest';
import { createTextVNode, Fragment } from 'vue';
import { flatten } from '../../../src/utils/flatten';

test('flatten function - handles array of strings', () => {
	const vNodes = ['a', 'b', 'c'];
	const result = flatten(vNodes);
	const expected = vNodes.map(createTextVNode);
	expect(result.map((vNode) => ({ ...vNode, patchFlag: undefined }))).toEqual(
		expected.map((vNode) => ({ ...vNode, patchFlag: undefined }))
	);
});
test('flatten function - handles array of numbers', () => {
	const vNodes = [1, 2, 3];
	const result = flatten(vNodes);
	expect(result).toEqual(vNodes.map((vNode) => createTextVNode(String(vNode))));
});

test('flatten function - handles nested arrays', () => {
	const vNodes = ['a', ['b', 'c'], 'd'];
	const result = flatten(vNodes);
	const expected = ['a', 'b', 'c', 'd'].map(createTextVNode);
	expect(result.map((vNode) => ({ ...vNode, patchFlag: undefined }))).toEqual(
		expected.map((vNode) => ({ ...vNode, patchFlag: undefined }))
	);
});

test('flatten function - handles Fragment type with children', () => {
	const vNodes = [
		createTextVNode('a'),
		{ type: Fragment, children: ['b', 'c'] },
		createTextVNode('d'),
	];
	const result = flatten(vNodes);
	const expected = ['a', 'b', 'c', 'd'].map(createTextVNode);
	expect(result.map((vNode) => ({ ...vNode, patchFlag: undefined }))).toEqual(
		expected.map((vNode) => ({ ...vNode, patchFlag: undefined }))
	);
});

test('flatten function - ignores null and undefined values', () => {
	const vNodes = ['a', null, 'b', undefined, 'c'];
	const result = flatten(vNodes);
	const expected = ['a', 'b', 'c'].map(createTextVNode);
	expect(result.map((vNode) => ({ ...vNode, patchFlag: undefined }))).toEqual(
		expected.map((vNode) => ({ ...vNode, patchFlag: undefined }))
	);
});
