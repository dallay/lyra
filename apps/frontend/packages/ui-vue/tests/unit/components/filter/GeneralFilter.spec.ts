import { mount, VueWrapper } from '@vue/test-utils';
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
  },
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
    },
  );
});

async function addFilterRule(inputValue: string = 'John Doe') {
  await wrapper.find('button').trigger('click');

  await wrapper.find('ul li button').trigger('click');

  await wrapper.find('span').trigger('click');
  await wrapper.find('input').setValue(inputValue);
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
  await  wrapper.find('#name-clear').trigger('click');

  const emitted = wrapper.emitted();
  expect(emitted).toHaveProperty('clearInputFilter');
});
