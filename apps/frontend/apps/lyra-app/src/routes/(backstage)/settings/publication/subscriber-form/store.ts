import { reactive, readonly } from 'vue';
import { CriteriaParam } from '@/types/types';
import { defineStore } from 'pinia';
import { QuerySort, useNotification } from '@lyra/ui';
import dependenciesContainer from '@/plugins/container';
import {
	CreateFormRequest,
	FORM_CONTROLLER_PROVIDER,
	FormController,
	FormResponse,
	UpdateFormRequest,
} from '@lyra/api-services';

// const formController: FormController =
// 	dependenciesContainer.get<FormController>(FORM_CONTROLLER_PROVIDER);

export type FormState = {
  readonly _formController: FormController;
  forms: FormResponse[];
  cursor: string;
  loading: boolean;
};
export default defineStore('/forms', () => {
  const notification = useNotification();
	const state = reactive<FormState>({
    _formController: dependenciesContainer.get<FormController>(FORM_CONTROLLER_PROVIDER),
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
      const pageResponse = await state._formController.findAll(criteria, sort, size, cursor);
			state.forms = pageResponse.data;
			state.cursor = pageResponse.nextPageCursor || '';
			state.loading = false;
		},
		async find(id: string): Promise<FormResponse> {
      return state._formController.find(id);
		},
		async update(id: string, request: UpdateFormRequest): Promise<void> {
      try {
        await state._formController.update(id, request);
        notification.add({
          message: 'Form updated successfully!',
          color: 'success',
        });
      } catch (error) {
        notification.add({
          message: 'Error updating form!',
          color: 'danger',
        });
      }
		},
		async delete(id: string): Promise<void> {
      try {
        await state._formController.delete(id);
        notification.add({
          message: 'Form deleted successfully!',
          color: 'success',
        });
      } catch (error) {
        notification.add({
          message: 'Error deleting form!',
          color: 'danger',
        });
      }
		},
		async create(request: CreateFormRequest): Promise<void> {
      try {
        await state._formController.create(request);
        notification.add({
          message: 'Form created successfully!',
          color: 'success',
        });
      } catch (error) {
        notification.add({
          message: 'Error creating form!',
          color: 'danger',
        });
      }
		},
	});

	function $reset() {
		state.forms = [];
		state.loading = false;
		state.cursor = '';
	}

	return { state, getters, actions, $reset };
});
