import { expect, test } from 'vitest';
import { mount } from '@vue/test-utils';
import { BasePagination } from '../../../src';

test('emits updatePage event with correct page and perPage when nextPage is called', async () => {
	const wrapper = mount(BasePagination, {
		props: {
			total: 100,
			perPage: 10,
			page: 0,
			totalPages: 10,
		},
	});

	await wrapper.vm.nextPage();

	expect(wrapper.emitted().updatePage).toBeTruthy();
	expect(wrapper.emitted().updatePage[0]).toEqual([{ page: 1, perPage: 10 }]);
});

test('does not emit updatePage event when nextPage is called and current page is last page', async () => {
	const wrapper = mount(BasePagination, {
		props: {
			total: 100,
			perPage: 10,
			page: 9,
			totalPages: 10,
		},
	});

	await wrapper.vm.nextPage();

	expect(wrapper.emitted().updatePage).toBeFalsy();
});

test('emits updatePage event with correct page and perPage when previousPage is called', async () => {
	const wrapper = mount(BasePagination, {
		props: {
			total: 100,
			perPage: 10,
			page: 1,
			totalPages: 10,
		},
	});

	await wrapper.vm.previousPage();

	expect(wrapper.emitted().updatePage).toBeTruthy();
	expect(wrapper.emitted().updatePage[0]).toEqual([{ page: 0, perPage: 10 }]);
});

test('does not emit updatePage event when previousPage is called and current page is first page', async () => {
	const wrapper = mount(BasePagination, {
		props: {
			total: 100,
			perPage: 10,
			page: 0,
			totalPages: 10,
		},
	});

	await wrapper.vm.previousPage();

	expect(wrapper.emitted().updatePage).toBeFalsy();
});

test('emits updatePage event with correct page and perPage when goToPage is called', async () => {
	const wrapper = mount(BasePagination, {
		props: {
			total: 100,
			perPage: 10,
			page: 0,
			totalPages: 10,
		},
	});

	await wrapper.vm.goToPage(5);

	expect(wrapper.emitted().updatePage).toBeTruthy();
	expect(wrapper.emitted().updatePage[0]).toEqual([{ page: 4, perPage: 10 }]);
});
