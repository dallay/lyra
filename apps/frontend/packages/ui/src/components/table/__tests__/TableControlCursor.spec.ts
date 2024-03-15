import TableControlCursor from '../TableControlCursor.vue';
import { mount, VueWrapper } from '@vue/test-utils';
import { afterEach, test } from 'vitest';
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

describe('TableControlCursor.vue', () => {
	test('emits loadMore event with correct parameters when loadMore is called and cursor is not empty', async () => {
		wrapper = mount(TableControlCursor, {
			props: {
				cursor: '123',
				limit: 10,
				loading: false,
			},
			global: {
				plugins: [localer],
			},
		});

		await wrapper.vm.loadMore();

		expect(wrapper.emitted()).toHaveProperty('loadMore');
		expect(wrapper.emitted().loadMore[0]).toEqual([{ cursor: '123', limit: 10 }]);
	});

	test('does not emit loadMore event when loadMore is called and cursor is empty', async () => {
		wrapper = mount(TableControlCursor, {
			props: {
				cursor: '',
				limit: 10,
				loading: false,
			},
			global: {
				plugins: [localer],
			},
		});

		await wrapper.vm.loadMore();

		expect(wrapper.emitted().loadMore).toBeUndefined();
	});

	test('renders load more button when loading is false and cursor is not empty', () => {
		wrapper = mount(TableControlCursor, {
			props: {
				cursor: '123',
				limit: 10,
				loading: false,
			},
			global: {
				plugins: [localer],
			},
		});

		expect(wrapper.find('button').isVisible()).toBe(true);
	});

	test('does not render load more button when loading is true', () => {
		wrapper = mount(TableControlCursor, {
			props: {
				cursor: '123',
				limit: 10,
				loading: true,
			},
			global: {
				plugins: [localer],
			},
		});

		expect(wrapper.find('button').exists()).toBe(false);
	});

	test('does not render load more button when cursor is empty', () => {
		wrapper = mount(TableControlCursor, {
			props: {
				cursor: '',
				limit: 10,
				loading: false,
			},
			global: {
				plugins: [localer],
			},
		});
		expect(wrapper.find('button').exists()).toBe(false);
	});
});
