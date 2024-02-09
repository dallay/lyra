import { expect, test } from 'vitest';
import { mount } from '@vue/test-utils';
import BaseSelectInput from '../../../src/components/BaseSelectInput.vue';

test('renders label when provided', () => {
	const wrapper = mount(BaseSelectInput, {
		props: {
			label: 'Test Label',
			options: ['Option 1', 'Option 2'],
		},
	});

	expect(wrapper.find('label').text()).toBe('Test Label');
});

test('renders options when provided', () => {
	const wrapper = mount(BaseSelectInput, {
		props: {
			options: ['Option 1', 'Option 2'],
		},
	});

	expect(wrapper.findAll('option').length).toBe(2);
	expect(wrapper.findAll('option')[0].text()).toBe('Option 1');
	expect(wrapper.findAll('option')[1].text()).toBe('Option 2');
});

test('emits model value when option is selected', async () => {
	const wrapper = mount(BaseSelectInput, {
		props: {
			options: ['Option 1', 'Option 2'],
		},
	});

	await wrapper.find('select').setValue('Option 2');

	expect(wrapper.emitted('update:modelValue')).toBeTruthy();
	expect(wrapper.emitted('update:modelValue')[0]).toEqual(['Option 2']);
});

test('handles no options provided', () => {
	const wrapper = mount(BaseSelectInput, {
		props: {
			options: [],
		},
	});

	expect(wrapper.findAll('option').length).toBe(0);
});
