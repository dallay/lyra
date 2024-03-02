import { expect, test } from 'vitest';
import {
  convertFieldPropertyToProperty,
  convertPropertyToFieldProperty,
  FieldProperty,
  Property,
} from '../../../../src/components/filter/Property';
import { allFilterOperators } from '../../../../src/components/filter/FilterOperator';

test('convert field property to property', () => {
  const fieldProperty: FieldProperty<string> = {
    name: 'name',
    label: 'Name',
    type: 'text',
    value: 'John',
  };
  const property: Property<string> = {
    id: 'id',
    name: 'name',
    label: 'Name',
    type: 'text',
    operator: allFilterOperators[0].value,
    value: 'John',
    availableOperators: allFilterOperators,
  };
  const convertedProperty = convertFieldPropertyToProperty(fieldProperty);
  expect({ ...convertedProperty, id: 'id' }).toEqual(property);
});

test('convert field property with options to property', () => {
  const fieldProperty: FieldProperty<string> = {
    name: 'status',
    label: 'Status',
    type: 'select',
    value: 'Active',
    options: ['Active', 'Inactive'],
  };
  const property: Property<string> = {
    id: 'id',
    name: 'status',
    label: 'Status',
    type: 'select',
    operator: allFilterOperators[0].value,
    value: 'Active',
    availableOperators: allFilterOperators,
    options: ['Active', 'Inactive'],
  };
  const convertedProperty = convertFieldPropertyToProperty(fieldProperty);
  expect({ ...convertedProperty, id: 'id' }).toEqual(property);
});
test('convert property to field property', () => {
  const property: Property<string> = {
    id: 'id',
    name: 'name',
    label: 'Name',
    type: 'text',
    operator: allFilterOperators[0].value,
    value: 'John',
    availableOperators: allFilterOperators,
  };
  const fieldProperty: FieldProperty<string> = {
    name: 'name',
    label: 'Name',
    type: 'text',
    value: 'John',
  };
  const convertedFieldProperty = convertPropertyToFieldProperty(property);
  expect(convertedFieldProperty).toEqual(fieldProperty);
});

test('convert property with options to field property', () => {
  const property: Property<string> = {
    id: 'id',
    name: 'status',
    label: 'Status',
    type: 'select',
    operator: allFilterOperators[0].value,
    value: 'Active',
    availableOperators: allFilterOperators,
    options: ['Active', 'Inactive'],
  };
  const fieldProperty: FieldProperty<string> = {
    name: 'status',
    label: 'Status',
    type: 'select',
    value: 'Active',
    options: ['Active', 'Inactive'],
  };
  const convertedFieldProperty = convertPropertyToFieldProperty(property);
  expect(convertedFieldProperty).toEqual(fieldProperty);
});
