import { expect, test } from 'vitest';
import { mount } from '@vue/test-utils';
import FilterRule from '../../../../src/components/filter/FilterRule.vue';
import { allFilterOperators, FilterOperator, Property } from '@lyra/vm-core';

test('emits removeFilterRule event when remove button is clicked', async () => {
	const property: Property<string> = {
		id: crypto.randomUUID(),
		name: 'test',
		label: 'Test',
		operator: FilterOperator.EQUAL,
		availableOperators: allFilterOperators,
		value: 'test',
		type: 'text',
	};

	const wrapper = mount(FilterRule, {
		props: { modelValue: property },
	});

	await wrapper.find('button').trigger('click');

	expect(wrapper.emitted()).toHaveProperty('removeFilterRule');
});

test('emits applyFilters event when dropdown is hidden', async () => {
	const property: Property<string> = {
		id: crypto.randomUUID(),
		name: 'test',
		label: 'Test',
		operator: FilterOperator.EQUAL,
		availableOperators: allFilterOperators,
		value: 'test',
		type: 'text',
	};

	const wrapper = mount(FilterRule, {
		props: { modelValue: property },
	});

	await wrapper.find('span').trigger('click');
	await wrapper.find('input').setValue('new value');

	const outsideComponent = mount(
		{
			template: '<div>Outside component</div>',
		},
		{
			attachTo: document.body,
		}
	);

	await outsideComponent.trigger('click');

	expect(wrapper.emitted()).toHaveProperty('applyFilters');
});

test('clears input when clear button is clicked', async () => {
	const property: Property<string> = {
		id: crypto.randomUUID(),
		name: 'test',
		label: 'Test',
		operator: FilterOperator.EQUAL,
		availableOperators: allFilterOperators,
		value: 'test',
		type: 'text',
	};

	const wrapper = mount(FilterRule, {
		props: { modelValue: property },
	});

	await wrapper.find('span').trigger('click');
	await wrapper.find('input').setValue('new value');

	await wrapper.find(`#${property.name}-clear`).trigger('click');

	expect(wrapper.vm.modelValue.value).toBeNull();
});

test('updates property value when input is changed', async () => {
	const property: Property<string> = {
		id: crypto.randomUUID(),
		name: 'test',
		label: 'Test',
		operator: FilterOperator.EQUAL,
		availableOperators: allFilterOperators,
		value: 'test',
		type: 'text',
	};

	const wrapper = mount(FilterRule, {
		props: { modelValue: property },
	});

	await wrapper.find('span').trigger('click');
	await wrapper.find('input').setValue('new value');

	expect(wrapper.vm.modelValue.value).toBe('new value');
});
