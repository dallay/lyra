import { reactive, readonly } from 'vue';
import { CriteriaParam } from '@/types/types';
import { defineStore } from 'pinia';
import { QuerySort } from '@lyra/ui';
import dependenciesContainer from '@/plugins/container';
import {
	CreateFormRequest,
	FORM_CONTROLLER_PROVIDER,
	FormController,
	FormResponse,
	UpdateFormRequest,
} from '@lyra/api-services';

const formController: FormController =
	dependenciesContainer.get<FormController>(FORM_CONTROLLER_PROVIDER);

export type FormState = { forms: FormResponse[]; cursor: string; loading: boolean };
export default defineStore('/forms', () => {
	const state = reactive<FormState>({
		forms: [],
		cursor: '',
		loading: false,
	});

	const getters = readonly({});

	const actions = readonly({
		async findAll(
			criteria?: CriteriaParam,
			sort?: QuerySort,
			size: number = 10,
			cursor: string = state.cursor
		) {
			state.loading = true;
			const pageResponse = await formController.findAll(criteria, sort, size, cursor);
			state.forms = pageResponse.data;
			state.cursor = pageResponse.nextPageCursor || '';
			state.loading = false;
		},
		async find(id: string): Promise<FormResponse> {
			return formController.find(id);
		},
		async update(id: string, request: UpdateFormRequest): Promise<void> {
			await formController.update(id, request);
		},
		async delete(id: string): Promise<void> {
			await formController.delete(id);
		},
		async create(request: CreateFormRequest): Promise<void> {
			await formController.create(request);
		},
	});

	function $reset() {
		state.forms = [];
		state.loading = false;
		state.cursor = '';
	}

	return { state, getters, actions, $reset };
});
