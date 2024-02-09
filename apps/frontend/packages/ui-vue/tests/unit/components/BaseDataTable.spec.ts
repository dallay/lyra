import { expect, test } from 'vitest';
import { mount } from '@vue/test-utils';
import BaseDataTable from '../../../src/components/BaseDataTable.vue';

test('renders table when items are present', () => {
	const wrapper = mount(BaseDataTable, {
		props: {
			columns: [{ key: 'name', label: 'Name', sortable: true }],
			items: [{ name: 'Item 1' }],
		},
	});

	expect(wrapper.find('table').exists()).toBe(true);
});

test('does not render table when items are empty', () => {
	const wrapper = mount(BaseDataTable, {
		props: {
			columns: [{ key: 'name', label: 'Name', sortable: true }],
			items: [],
		},
	});

	expect(wrapper.find('table').exists()).toBe(false);
});

test('renders sortable column header with correct label', () => {
	const wrapper = mount(BaseDataTable, {
		props: {
			columns: [{ key: 'name', label: 'Name', sortable: true }],
			items: [{ name: 'Item 1' }],
		},
	});

	expect(wrapper.find('th').text()).toContain('Name');
});

test('renders non-sortable column header without sort icon', () => {
	const wrapper = mount(BaseDataTable, {
		props: {
			columns: [{ key: 'name', label: 'Name', sortable: false }],
			items: [{ name: 'Item 1' }],
		},
	});

	expect(wrapper.find('th svg').exists()).toBe(false);
});

test('renders sortable column header with sort icon', () => {
	const wrapper = mount(BaseDataTable, {
		props: {
			columns: [{ key: 'name', label: 'Name', sortable: true }],
			items: [{ name: 'Item 1' }],
		},
	});

	expect(wrapper.find('th svg').exists()).toBe(true);
});

test('renders correct number of rows', () => {
	const wrapper = mount(BaseDataTable, {
		props: {
			columns: [{ key: 'name', label: 'Name', sortable: true }],
			items: [{ name: 'Item 1' }, { name: 'Item 2' }],
		},
	});

	expect(wrapper.findAll('tbody tr').length).toBe(2);
});

test('renders correct data in cells', () => {
	const wrapper = mount(BaseDataTable, {
		props: {
			columns: [{ key: 'name', label: 'Name', sortable: true }],
			items: [{ name: 'Item 1' }],
		},
	});

	expect(wrapper.find('tbody td').text()).toContain('Item 1');
});

test('renders no data message when items are empty', () => {
	const wrapper = mount(BaseDataTable, {
		props: {
			columns: [{ key: 'name', label: 'Name', sortable: true }],
			items: [],
		},
	});

	expect(wrapper.text()).toContain('No data available');
});
