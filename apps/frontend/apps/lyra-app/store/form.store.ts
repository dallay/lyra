import {useNuxtApp} from "#app";
import {ref} from "vue";
import {defineStore} from "pinia";
import {
  type CreateFormRequest,
  type UpdateFormRequest,
  Form,
  FormId,
  OrganizationId
} from "@lyra/domain";
import {useWorkspaceStore} from "./workspace.store";

export const useFormStore = defineStore("forms", () => {
	const { $api } = useNuxtApp();
  const workspaceStore = useWorkspaceStore();
	const formList = ref<Form[]>([]);

	const fetchFormList = async () => {
		try {
      const currentWorkspace = workspaceStore.getCurrentWorkspace();
      if (!currentWorkspace) {
        return;
      }
      const organizationId = OrganizationId.create(currentWorkspace.organizationId);
			$api.form
				.fetchAll(organizationId)
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
    const currentWorkspace = workspaceStore.getCurrentWorkspace();
    if (!currentWorkspace) {
      return;
    }
    const organizationId = OrganizationId.create(currentWorkspace.organizationId);
    const formId = FormId.random()
    await $api.form.createForm(organizationId,formId,request);
    await fetchFormList();
  } catch (error) {
    console.error(`createForm error:${error}`);
  }
};

const updateForm = async (formId: FormId, request: UpdateFormRequest) => {
  try {
    const currentWorkspace = workspaceStore.getCurrentWorkspace();
    if (!currentWorkspace) {
      return;
    }
    const organizationId = OrganizationId.create(currentWorkspace.organizationId);
    await $api.form.updateForm(organizationId,formId,request);
    await fetchFormList();
  } catch (error) {
    console.error(`updateForm error:${error}`);
  }
};

const deleteForm = async (formId: FormId) => {
  try {
    const currentWorkspace = workspaceStore.getCurrentWorkspace();
    if (!currentWorkspace) {
      return;
    }
    const organizationId = OrganizationId.create(currentWorkspace.organizationId);
    await $api.form.deleteForm(organizationId,formId);
    await fetchFormList();
  } catch (error) {
    console.error(`deleteForm error:${error}`);
  }
};

const formDetail = (formId: FormId) => {
  try{
    return $api.form.fetchDetail(formId);
  } catch (error) {
    console.error(`formDetail error:${error}`);
  }
};

	return { formList, fetchFormList, createForm , updateForm, deleteForm, formDetail};
});
