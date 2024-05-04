import { reactive, readonly } from 'vue';
import { CriteriaParam } from '@/types/types';
import { defineStore } from 'pinia';
import { Form } from './types';
import { QuerySort } from '@lyra/ui';
import dependenciesContainer from '@/plugins/container';
import { FORM_CONTROLLER_PROVIDER, FormController, FormResponse } from '@lyra/api-services';

const formController: FormController =
	dependenciesContainer.get<FormController>(FORM_CONTROLLER_PROVIDER);

const responseToForm = (formResponse: FormResponse): Form => {
	return {
		id: formResponse.id,
		name: formResponse.name,
		header: formResponse.header,
		description: formResponse.description,
		inputPlaceholder: formResponse.inputPlaceholder,
		buttonText: formResponse.buttonText,
		buttonColor: formResponse.buttonColor,
		backgroundColor: formResponse.backgroundColor,
		textColor: formResponse.textColor,
		buttonTextColor: formResponse.buttonTextColor,
		createdAt: formResponse.createdAt,
		updatedAt: formResponse.updatedAt,
	} as Form;
};

export default defineStore('/forms', () => {
	const state = reactive({
		forms: [] as Form[],
		cursor: '',
		loading: false,
	});

	const getters = readonly({});

	const actions = readonly({
		async getForms(
			criteria?: CriteriaParam,
			sort?: QuerySort,
			size: number = 10,
			cursor: string = state.cursor
		) {
			state.loading = true;
			const pageResponse = await formController.getForms(criteria, sort, size, cursor);
			const formResponses: FormResponse[] = pageResponse.data;
			state.forms = formResponses.map((form) => {
				return responseToForm(form);
			});
			state.cursor = pageResponse.nextPageCursor || '';
			state.loading = false;
		},
		async findForm(id: string): Promise<Form> {
			const formResponse: FormResponse = await formController.findForm(id);
			return responseToForm(formResponse);
		},
		async updateForm(form: Form): Promise<void> {
			await formController.updateForm(form.id, {
				name: form.name,
				header: form.header,
				description: form.description,
				inputPlaceholder: form.inputPlaceholder,
				buttonText: form.buttonText,
				buttonColor: form.buttonColor,
				backgroundColor: form.backgroundColor,
				textColor: form.textColor,
				buttonTextColor: form.buttonTextColor,
			});
		},
		async deleteForm(id: string): Promise<void> {
			await formController.deleteForm(id);
		},
	});

	function $reset() {
		state.forms = [];
		state.loading = false;
		state.cursor = '';
	}

	return { state, getters, actions, $reset };
});
