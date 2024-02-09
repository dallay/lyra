import { expect, test } from 'vitest';
import { mount } from '@vue/test-utils';
import SlotListener from '@/components/slot-listener/SlotListener.vue';


test('emits focus event when focused', async () => {
  const wrapper = mount(SlotListener, {
    slots: {
      default: '<button></button>'
    }
  });
  await wrapper.find('button').trigger('focus');
  expect(wrapper.emitted('focus')).toBeTruthy();
});

test('emits blur event when blurred', async () => {
  const wrapper = mount(SlotListener, {
    slots: {
      default: '<button></button>'
    }
  });
  await wrapper.find('button').trigger('blur');
  expect(wrapper.emitted('blur')).toBeTruthy();
});

test('emits click event when clicked', async () => {
  const wrapper = mount(SlotListener, {
    slots: {
      default: '<button></button>'
    }
  });
  await wrapper.find('button').trigger('click');
  expect(wrapper.emitted('click')).toBeTruthy();
});

test('emits mouseenter event when mouse enters', async () => {
  const wrapper = mount(SlotListener, {
    slots: {
      default: '<button></button>'
    }
  });
  await wrapper.find('button').trigger('mouseenter');
  expect(wrapper.emitted('mouseenter')).toBeTruthy();
});

test('emits mouseleave event when mouse leaves', async () => {
  const wrapper = mount(SlotListener, {
    slots: {
      default: '<button></button>'
    }
  });
  await wrapper.find('button').trigger('mouseleave');
  expect(wrapper.emitted('mouseleave')).toBeTruthy();
});
