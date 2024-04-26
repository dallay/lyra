import { reactive, readonly } from 'vue';
import type { PageResponse } from '@/types/types';
import { defineStore } from 'pinia';
import { SearchParameters } from 'ofetch';
import { Form } from './types';

export default defineStore('/forms', () => {
	const state = reactive({
		forms: [] as Form[],
		cursor: '',
		loading: false,
	});

	const getters = readonly({
	});

	const actions = readonly({
		// async getForms(
		// 	criteria?: CriteriaParam,
		// 	sort?: QuerySort,
		// 	size: number = 10,
		// 	cursor: string = state.cursor
		// ) {
		// 	state.loading = true;
		// 	const params = buildParams(criteria, sort, size, cursor);
		// 	const response = await request<PageResponse<Subscriber>>('/newsletter/subscribers', {
		// 		method: 'GET',
		// 		headers: {
		// 			Accept: 'application/vnd.api.v1+json',
		// 		},
		// 		params: params,
		// 	});
		// 	if (!response.ok) {
		// 		throw new Error('Error fetching subscribers');
		// 	}
		// 	state.subscribers = response._data?.data || [];
		// 	state.cursor = response._data?.nextPageCursor || '';
		// 	state.loading = false;
		// },
	});

	function $reset() {
		state.forms = [];
		state.loading = false;
		state.cursor = '';
	}

	return { state, getters, actions, $reset };
});
