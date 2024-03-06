import { test, expect } from 'vitest';
import { mount } from '@vue/test-utils';
import { BasicDropdown } from '../../../../src';

test('BasicDropdown.vue - renders trigger slot content', () => {
	const wrapper = mount(BasicDropdown, {
		slots: {
			trigger: 'Click me',
		},
	});
	expect(wrapper.text()).toContain('Click me');
});

test('BasicDropdown.vue - does not render default slot content when not visible', () => {
	const wrapper = mount(BasicDropdown, {
		slots: {
			default: 'Dropdown content',
		},
	});
	expect(wrapper.text()).not.toContain('Dropdown content');
});

test('BasicDropdown.vue - renders default slot content when visible', async ({ expect }) => {
	const wrapper = mount(BasicDropdown, {
		slots: {
			default: 'Dropdown content',
			trigger: '<button>Click me</button>',
		},
	});
	await wrapper.find('button').trigger('click');
	expect(wrapper.text()).toContain('Dropdown content');
});

test('BasicDropdown.vue - hides default slot content when clicked outside', async ({ expect }) => {
	const wrapper = mount(BasicDropdown, {
		attachTo: document.body,
		slots: {
			default: 'Dropdown content',
			trigger: '<button>Click me</button>',
		},
	});

	await wrapper.find('button').trigger('click');
	expect(wrapper.text()).toContain('Dropdown content');

	const outsideComponent = mount(
		{
			template: '<div>Outside component</div>',
		},
		{
			attachTo: document.body,
		}
	);

	await outsideComponent.trigger('click');

	expect(wrapper.text()).not.toContain('Dropdown content');

	// Desmontar los componentes montados
	wrapper.unmount();
	outsideComponent.unmount();
});

test('BasicDropdown.vue - emits onHide when clicked outside', async ({ expect }) => {
	const wrapper = mount(BasicDropdown, {
		attachTo: document.body,
		slots: {
			default: 'Dropdown content',
			trigger: '<button>Click me</button>',
		},
	});

	await wrapper.find('button').trigger('click');
	expect(wrapper.text()).toContain('Dropdown content');

	const outsideComponent = mount(
		{
			template: '<div>Outside component</div>',
		},
		{
			attachTo: document.body,
		}
	);

	await outsideComponent.trigger('click');

	expect(wrapper.emitted().onHide).toBeTruthy();

	wrapper.unmount();
	outsideComponent.unmount();
});

test('BasicDropdown.vue - emits onToggle when trigger is clicked', async ({ expect }) => {
	const wrapper = mount(BasicDropdown, {
		slots: {
			trigger: '<button>Click me</button>',
		},
	});
	await wrapper.find('button').trigger('click');
	expect(wrapper.emitted().onToggle).toBeTruthy();
});
