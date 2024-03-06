import { test, expect } from 'vitest';
import { createTextVNode } from 'vue';
import { getFirstSlotVNode } from '../../../src/utils/getFirstSlotNode';

test('getFirstSlotVNode - returns first VNode when slot has one child', () => {
	const slots = {
		default: () => [createTextVNode('a')],
	};
	const result = getFirstSlotVNode(slots);
	expect(result).toEqual(createTextVNode('a'));
});

test('getFirstSlotVNode - returns null when slot has multiple children', () => {
	const slots = {
		default: () => [createTextVNode('a'), createTextVNode('b')],
	};
	const result = getFirstSlotVNode(slots);
	expect(result).toBeNull();
});

test('getFirstSlotVNode - returns null when slot is empty', () => {
	const slots = {};
	const result = getFirstSlotVNode(slots);
	expect(result).toBeNull();
});

test('getFirstSlotVNode - uses provided slot name', () => {
	const slots = {
		custom: () => [createTextVNode('a')],
	};
	const result = getFirstSlotVNode(slots, 'custom');
	expect(result).toEqual(createTextVNode('a'));
});

test('getFirstSlotVNode - passes props to slot function', () => {
	const slots = {
		default: (props) => [createTextVNode(props.text)],
	};
	const result = getFirstSlotVNode(slots, 'default', { text: 'a' });
	expect(result).toEqual(createTextVNode('a'));
});
