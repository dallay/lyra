import { expect, test } from 'vitest';
import { mount } from '@vue/test-utils';
import { CursorPagination } from '../../../../src';

test('emits updatePage event with next page cursor when next button is clicked', async () => {
	const wrapper = mount(CursorPagination, {
		props: {
			previousPageCursor: 'prevCursor',
			nextPageCursor: 'nextCursor',
			perPage: 10,
			perPageOptions: [5, 10, 25, 50, 100],
		},
	});

	await wrapper.find('[data-test="next-button"]').trigger('click');

	expect(wrapper.emitted()).toHaveProperty('updatePage');
	expect(wrapper.emitted().updatePage[0]).toEqual([{ cursor: 'nextCursor', perPage: 10 }]);
});

test('emits updatePage event with previous page cursor when previous button is clicked', async () => {
	const wrapper = mount(CursorPagination, {
		props: {
			previousPageCursor: 'prevCursor',
			nextPageCursor: 'nextCursor',
			perPage: 10,
			perPageOptions: [5, 10, 25, 50, 100],
		},
	});

	await wrapper.find('[data-test="previous-button"]').trigger('click');

	expect(wrapper.emitted()).toHaveProperty('updatePage');
	expect(wrapper.emitted().updatePage[0]).toEqual([{ cursor: 'prevCursor', perPage: 10 }]);
});

test('does not emit updatePage event when next button is clicked and nextPageCursor is null', async () => {
	const wrapper = mount(CursorPagination, {
		props: {
			previousPageCursor: 'prevCursor',
			nextPageCursor: null, // Set to null
			perPage: 10,
			perPageOptions: [5, 10, 25, 50, 100],
		},
	});

	await wrapper.find('[data-test="next-button"]').trigger('click');

	expect(wrapper.emitted()).not.toHaveProperty('updatePage');
});

test('does not emit updatePage event when previous button is clicked and previousPageCursor is null', async () => {
	const wrapper = mount(CursorPagination, {
		props: {
			previousPageCursor: null, // Set to null
			nextPageCursor: 'nextCursor',
			perPage: 10,
			perPageOptions: [5, 10, 25, 50, 100],
		},
	});

	await wrapper.find('[data-test="previous-button"]').trigger('click');

	expect(wrapper.emitted()).not.toHaveProperty('updatePage');
});

test('emits updatePage event with new perPage value when perPage select input is changed', async () => {
	const wrapper = mount(CursorPagination, {
		props: {
			previousPageCursor: 'prevCursor',
			nextPageCursor: 'nextCursor',
			perPage: 10,
			perPageOptions: [5, 10, 25, 50, 100],
		},
	});

	await wrapper.find('select').setValue(25);

	expect(wrapper.emitted()).toHaveProperty('updatePage');
	expect(wrapper.emitted().updatePage[0]).toEqual([{ cursor: 'nextCursor', perPage: 25 }]);
});
