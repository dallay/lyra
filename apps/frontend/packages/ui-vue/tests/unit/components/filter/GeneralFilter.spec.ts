import { DOMWrapper, mount, VueWrapper } from '@vue/test-utils';
import { beforeEach, expect, test } from 'vitest';
import { FieldProperty } from '@lyra/vm-core';
import { GeneralFilter } from '../../../../src';

let fields: FieldProperty<string | number | Date>[] = [];
let wrapper: VueWrapper<GeneralFilter>;

let outsideComponent = mount(
	{
		template: '<div>Outside component</div>',
	},
	{
		attachTo: document.body,
	}
);

beforeEach(() => {
	fields = [
		{
			name: 'name',
			label: 'Name',
			type: 'text',
			value: 'John',
		},
		{
			name: 'age',
			label: 'Age',
			type: 'number',
			value: 30,
		},
		{
			name: 'email',
			label: 'Email',
			type: 'email',
			value: 'john.doe@test.com',
		},
		{
			name: 'phone',
			label: 'Phone',
			type: 'tel',
			value: '123456789',
		},
		{
			name: 'date',
			label: 'Date',
			type: 'date',
			value: new Date(),
		},
		{
			name: 'status',
			label: 'Status',
			type: 'select',
			options: ['Active', 'Inactive'],
			value: 'Active',
		},
	];
	wrapper = mount(GeneralFilter, {
		props: {
			fields: fields,
		},
	});
	outsideComponent = mount(
		{
			template: '<div>Outside component</div>',
		},
		{
			attachTo: document.body,
		}
	);
});

async function addFilterRule(
	inputValue: string | number | Date = 'John Doe',
	listIndex: number = 0,
	inputType: 'select' | 'input' = 'input'
) {
	await wrapper.find('button').trigger('click');

	await wrapper.findAll('ul li button').at(listIndex).trigger('click');

	await wrapper.find('span').trigger('click');
	// const input = wrapper.find(inputType); change to search for input by id and type
	const input = wrapper.find(`#${fields[listIndex].name}`);

	if (inputType === 'select') {
		const option: DOMWrapper<HTMLOptionElement> = input.find(`option[value="${inputValue}"]`);
		// @ts-expect-error - setSelected is not defined in the type definition
		await option.setSelected();
	} else if (typeof inputValue === 'object') {
		// if input is a date, we need to set the value differently.
		const date = inputValue.toISOString().split('T')[0];
		await input.setValue(date);
	} else {
		await input.setValue(inputValue);
	}
	await outsideComponent.trigger('click');
}

test('should initialize with provided fields', () => {
	expect(wrapper.vm.availableFieldProperties).toEqual(fields);
});
test('should emit applyFilters event when filter if applied', async () => {
	await addFilterRule('John Doe');
	const emitted = wrapper.emitted();
	expect(emitted).toHaveProperty('applyFilters');
	expect(emitted.applyFilters).toHaveLength(3);
	expect(emitted.applyFilters[0][0]).toBe('name=eq:John');
	expect(emitted.applyFilters[1][0]).toBe('name=eq:John');
	expect(emitted.applyFilters[2][0]).toBe('name=eq:John Doe');
});

test('should remove filter when remove button is clicked', async () => {
	await addFilterRule();

	await wrapper.find('span button').trigger('click');

	const emitted = wrapper.emitted();
	expect(emitted).toHaveProperty('removeFilterRule');
	const property = emitted.removeFilterRule[0][0];
	expect(property).toBeTypeOf('object');
	expect(property).toHaveProperty('name', 'name');
	expect(property).toHaveProperty('value', 'John Doe');
	expect(property).toHaveProperty('operator', 'eq');
	expect(property).toHaveProperty('type', 'text');
	expect(property).toHaveProperty('label', 'Name');
});

test('should emit clearInputFilter event when clear button is clicked', async () => {
	await addFilterRule();
	await wrapper.find('span').trigger('click');
	await wrapper.find('#name-clear').trigger('click');

	const emitted = wrapper.emitted();
	expect(emitted).toHaveProperty('clearInputFilter');
});

// tests for the other filter types
test('should emit applyFilters event when filter if applied for number', async () => {
	await addFilterRule(31, 1);
	const emitted = wrapper.emitted();
	expect(emitted).toHaveProperty('applyFilters');
	expect(emitted.applyFilters).toHaveLength(3);
	expect(emitted.applyFilters[2][0]).toBe('age=eq:31');
});

test('should emit applyFilters event when filter if applied for email', async () => {
	await addFilterRule('john.doe@supertest.com', 2);
	const emitted = wrapper.emitted();
	expect(emitted).toHaveProperty('applyFilters');
	expect(emitted.applyFilters).toHaveLength(3);
	expect(emitted.applyFilters[2][0]).toBe('email=eq:john.doe@supertest.com');
});

test('should emit applyFilters event when filter if applied for phone', async () => {
	await addFilterRule('987654314', 3);
	const emitted = wrapper.emitted();
	expect(emitted).toHaveProperty('applyFilters');
	expect(emitted.applyFilters).toHaveLength(3);
	expect(emitted.applyFilters[2][0]).toBe('phone=eq:987654314');
});

test('should emit applyFilters event when filter if applied for date', async () => {
	await addFilterRule(new Date('2022-01-01'), 4);
	const emitted = wrapper.emitted();
	expect(emitted).toHaveProperty('applyFilters');
	expect(emitted.applyFilters).toHaveLength(3);
	expect(emitted.applyFilters[2][0]).toBe('date=eq:2022-01-01');
});

test('should emit applyFilters event when filter if applied for select', async () => {
	await addFilterRule('Inactive', 5, 'select');
	const emitted = wrapper.emitted();
	expect(emitted).toHaveProperty('applyFilters');
	expect(emitted.applyFilters).toHaveLength(3);
	expect(emitted.applyFilters[2][0]).toBe('status=eq:Inactive');
});

test('should emit clearInputFilter event when clear button is clicked for number', async () => {
	await addFilterRule(31, 1);
	await wrapper.find('span').trigger('click');
	await wrapper.find('#age-clear').trigger('click');

	const emitted = wrapper.emitted();
	expect(emitted).toHaveProperty('clearInputFilter');
});

test('should emit clearInputFilter event when clear button is clicked for email', async () => {
	await addFilterRule('john@test.com', 2);
	await wrapper.find('span').trigger('click');
	await wrapper.find('#email-clear').trigger('click');

	const emitted = wrapper.emitted();
	expect(emitted).toHaveProperty('clearInputFilter');
});

test('should emit clearInputFilter event when clear button is clicked for phone', async () => {
	await addFilterRule('987654321', 3);
	await wrapper.find('span').trigger('click');
	await wrapper.find('#phone-clear').trigger('click');

	const emitted = wrapper.emitted();
	expect(emitted).toHaveProperty('clearInputFilter');
});

test('should not emit clearInputFilter event when clear button is clicked for date', async () => {
	await addFilterRule(new Date('2022-01-01'), 4);
	await wrapper.find('span').trigger('click');
	// not find #date-clear button
	const clearButton = wrapper.find('#date-clear');
	expect(clearButton.exists()).toBe(false);
});

test('should not emit clearInputFilter event when clear button is clicked for select', async () => {
	await addFilterRule('Inactive', 5, 'select');
	await wrapper.find('span').trigger('click');
	// not find #status-clear button
	const clearButton = wrapper.find('#status-clear');
	expect(clearButton.exists()).toBe(false);
});
