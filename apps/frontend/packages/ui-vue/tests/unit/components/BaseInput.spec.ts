import { expect, test } from 'vitest';
import { mount } from '@vue/test-utils';
import { BaseInput } from '../../../src';
test('renders input field with provided id', async () => {
  const wrapper = mount(BaseInput, {
    props: {
      id: 'test-id',
    },
  })
  expect(wrapper.find('#test-id').exists()).toBe(true)
});

test('renders input field with provided id', async () => {
  const wrapper = mount(BaseInput, {
    props: {
      id: 'test-id',
    },
  })
  expect(wrapper.find('#test-id').exists()).toBe(true)
});

test('clears input value when clear button is clicked', async () => {
  const wrapper = mount(BaseInput, {
    props: {
      id: 'test-id',
    },
  })
  await wrapper.find('input').setValue('test value')
  await wrapper.find('button').trigger('click')
  expect(wrapper.find('input').element.value).toBe('')
});

test('does not show clear button when input type is select', async () => {
  const wrapper = mount(BaseInput, {
    props: {
      type: 'select',
    },
  })
  await wrapper.find('input').setValue('test value')
  expect(wrapper.find('button').exists()).toBe(false)
});

test('does not show clear button when input type is select', async () => {
  const wrapper = mount(BaseInput, {
    props: {
      type: 'select',
    },
  })
  await wrapper.find('input').setValue('test value')
  expect(wrapper.find('button').exists()).toBe(false)
});

test('shows clear button when input type is text and value is not empty', async () => {
  const wrapper = mount(BaseInput, {
    props: {
      type: 'text',
    },
  })
  await wrapper.find('input').setValue('test value')
  expect(wrapper.find('button').exists()).toBe(true)
});
