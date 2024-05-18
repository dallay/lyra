import { type QuerySort, request } from '@lyra/ui';
import { reactive, readonly } from 'vue';
import type { Subscriber, Subscribers } from './types';
import { buildParams, CriteriaParam, PageResponse } from '@/types/types';
import { defineStore } from 'pinia';

export default defineStore('/newsletter/subscribers', () => {
	const state = reactive({
		subscribers: [] as Subscriber[],
		cursor: '',
		loading: false,
	});

	const getters = readonly({
		subscribers: (state: Subscribers) => state.subscribers,
	});

	const actions = readonly({
		async getSubscribers(
			criteria?: CriteriaParam,
			sort?: QuerySort,
			size: number = 10,
			cursor: string = state.cursor
		) {
			state.loading = true;
			const params = buildParams(criteria, sort, size, cursor);
			const response = await request<PageResponse<Subscriber>>('/newsletter/subscribers', {
				method: 'GET',
				headers: {
					Accept: 'application/vnd.api.v1+json',
				},
				params: params,
			});
			if (!response.ok) {
				throw new Error('Error fetching subscribers');
			}
			state.subscribers = response._data?.data || [];
			state.cursor = response._data?.nextPageCursor || '';
			state.loading = false;
		},
	});

	function $reset() {
		state.subscribers = [];
		state.loading = false;
		state.cursor = '';
	}

	return { state, getters, actions, $reset };
});
