import {useNuxtApp} from "#app";
import {ref} from "vue";
import {defineStore} from "pinia";
import {type CreateFormRequest,type UpdateFormRequest, Form, FormId} from "@lyra/domain";

export const useFormStore = defineStore("forms", () => {
	const { $api } = useNuxtApp();
	const formList = ref<Form[]>([]);

	const fetchFormList = async () => {
		try {
			$api.form
				.fetchAll()
				.then((response) => {
          formList.value = response.data.map(Form.fromPrimitives);
				})
				.catch((error) => {
					console.error(error);
				});
		} catch (error) {
			console.error(`fetchFormList error:${error}`);
		}
	};

const createForm = async (request: CreateFormRequest) => {
  try {
    const formId = FormId.random()
    await $api.form.createForm(formId,request);
    await fetchFormList();
  } catch (error) {
    console.error(`createForm error:${error}`);
  }
};

const updateForm = async (formId: FormId, request: UpdateFormRequest) => {
  try {
    await $api.form.updateForm(formId,request);
    await fetchFormList();
  } catch (error) {
    console.error(`updateForm error:${error}`);
  }
};

const deleteForm = async (formId: FormId) => {
  try {
    await $api.form.deleteForm(formId);
    await fetchFormList();
  } catch (error) {
    console.error(`deleteForm error:${error}`);
  }
};

	return { formList, fetchFormList, createForm , updateForm, deleteForm};
});
