import TableControl from '../TableControl.vue';
import { mount, VueWrapper } from '@vue/test-utils';
import { afterEach, test } from 'vitest';
import Select from '../../select/Select.vue';
import { createLocaler } from 'vue-localer';

const localer = createLocaler({
	fallbackLocale: 'en-US',
	messages: {
		'en-US': {
			table: {
				rowsPerPage: '{0} rows per page',
				of: 'of',
			},
		},
	},
});
let wrapper: VueWrapper<Record<string, any>>;

afterEach(() => {
	wrapper.unmount();
});
test('TableControl emits previousPage event when previousPage method is called', async () => {
	wrapper = mount(TableControl, {
		props: {
			currentPage: 2,
			rowsPerPage: 10,
			rowsPerPageOptions: [{ label: '10', value: 10 }],
			loading: false,
			paginationInfo: '1-10 of 100',
		},
		global: {
			plugins: [localer],
		},
	});

	await wrapper.vm.previousPage();
	expect(wrapper.emitted()).toHaveProperty('previousPage');
});

test('TableControl emits nextPage event when nextPage method is called', async () => {
	wrapper = mount(TableControl, {
		props: {
			currentPage: 1,
			rowsPerPage: 10,
			rowsPerPageOptions: [{ label: '10', value: 10 }],
			loading: false,
			paginationInfo: '1-10 of 100',
		},
		global: {
			plugins: [localer],
		},
	});

	await wrapper.vm.nextPage();
	expect(wrapper.emitted()).toHaveProperty('nextPage');
});

test('TableControl emits updateRowsPerPage event when rowsPerPage is changed', async () => {
	wrapper = mount(TableControl, {
		props: {
			currentPage: 1,
			rowsPerPage: 10,
			rowsPerPageOptions: [{ label: '10', value: 10 }],
			loading: false,
			paginationInfo: '1-10 of 100',
		},
		global: {
			plugins: [localer],
		},
	});

	await wrapper.findComponent(Select).vm.$emit('change', 20);
	expect(wrapper.emitted()).toHaveProperty('updateRowsPerPage');
	expect(wrapper.emitted().updateRowsPerPage[0]).toEqual([20]);
});

test('TableControl does not emit previousPage event when on first page', async () => {
	wrapper = mount(TableControl, {
		props: {
			currentPage: 1,
			rowsPerPage: 10,
			rowsPerPageOptions: [{ label: '10', value: 10 }],
			loading: false,
			paginationInfo: '1-10 of 100',
		},
		global: {
			plugins: [localer],
		},
	});

	await wrapper.vm.previousPage();
	expect(wrapper.emitted()).not.toHaveProperty('previousPage');
});
