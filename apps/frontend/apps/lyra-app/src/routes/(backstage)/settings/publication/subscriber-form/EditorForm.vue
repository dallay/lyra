<script setup lang="ts">
import { minLength, nullish, object, string } from 'valibot';
import { useValdnLocale, XButton, XDrawer, XTextField } from '@lyra/ui';
import { computed, onMounted, reactive, toRef, watch } from 'vue';
import { useValibotSchema } from 'vue-formor';
import useStore from './store';
import { FormResponse, UpdateFormRequest } from '@lyra/api-services';

const editorDrawer = defineModel<boolean>('editorDrawer', {
	default: false,
});
const { actions } = useStore();

const form = defineModel<FormResponse>('form', {
	required: true,
});

const valdnLocale = useValdnLocale();

const state = reactive({
	form: form.value,
	valdn: {} as Record<keyof FormResponse, string>,
});

const schema = useValibotSchema(
	computed(() =>
		object({
			name: nullish(string([minLength(1, valdnLocale.value.required)]), form.value.name),
			header: nullish(string([minLength(1, valdnLocale.value.required)]), form.value.header),
			description: nullish(
				string([minLength(1, valdnLocale.value.required)]),
				form.value.description
			),
			inputPlaceholder: nullish(
				string([minLength(1, valdnLocale.value.required)]),
				form.value.inputPlaceholder
			),
			buttonText: nullish(
				string([minLength(1, valdnLocale.value.required)]),
				form.value.buttonText
			),
			buttonColor: nullish(
				string([minLength(1, valdnLocale.value.required)]),
				form.value.buttonColor
			),
			backgroundColor: nullish(
				string([minLength(1, valdnLocale.value.required)]),
				form.value.backgroundColor
			),
			textColor: nullish(string([minLength(1, valdnLocale.value.required)]), form.value.textColor),
			buttonTextColor: nullish(
				string([minLength(1, valdnLocale.value.required)]),
				form.value.buttonTextColor
			),
		})
	),
	toRef(state, 'form'),
	toRef(state, 'valdn')
);

const submit = () => {
	if (schema.validate()) {
		form.value = state.form;
    const request: UpdateFormRequest = {
      name: state.form.name,
      header: state.form.header,
      description: state.form.description,
      inputPlaceholder: state.form.inputPlaceholder,
      buttonText: state.form.buttonText,
      buttonColor: state.form.buttonColor,
      backgroundColor: state.form.backgroundColor,
      textColor: state.form.textColor,
      buttonTextColor: state.form.buttonTextColor,

    }
		actions.update(form.value.id, request);
	}
};
watch(
	() => form.value,
	(value) => {
		state.form = value;
	}
);
onMounted(() => {
	state.form = form.value;
});
</script>

<template>
	<XDrawer v-model="editorDrawer" placement="right" class="p-4 !w-md">
		<h5
			class="inline-flex items-center mb-6 text-base font-semibold text-gray-500 uppercase dark:text-gray-400"
		>
			<i class="i-material-symbols-light:dynamic-form-rounded me-2.5"></i>
			Form Editor
		</h5>
		<XButton
			icon="i-material-symbols:close"
			size="small"
			variant="text"
			color="secondary"
			@click="editorDrawer = false"
			class="absolute top-2.5 end-2.5"
		/>
		<form class="mb-6">
			<div class="mb-6">
				<XTextField
					v-model:value="state.form.name"
					label="Name"
					required
					:invalid="state.valdn.name"
				/>
			</div>
			<div class="relative inline-flex w-full items-center justify-center">
				<hr class="my-8 h-px w-full border-0 bg-gray-200 dark:bg-gray-700" />
				<span class="absolute left-1/2 -translate-x-1/2 bg-white px-3 font-medium dark:bg-gray-800"
					>Content</span
				>
			</div>

			<div class="mb-6">
				<XTextField
					v-model:value="state.form.header"
					label="Header"
					required
					:invalid="state.valdn.header"
				/>
			</div>
			<div class="relative mb-6">
				<XTextField
					v-model:value="state.form.description"
					label="Description"
					required
					:invalid="state.valdn.description"
				/>
			</div>
			<div class="mb-6">
				<XTextField
					v-model:value="state.form.inputPlaceholder"
					label="Input Placeholder"
					required
					:invalid="state.valdn.inputPlaceholder"
				/>
			</div>
			<div class="mb-6">
				<XTextField
					v-model:value="state.form.buttonText"
					label="Button Text"
					required
					:invalid="state.valdn.buttonText"
				/>
			</div>
			<div class="relative inline-flex w-full items-center justify-center">
				<hr class="my-8 h-px w-full border-0 bg-gray-200 dark:bg-gray-700" />
				<span class="absolute left-1/2 -translate-x-1/2 bg-white px-3 font-medium dark:bg-gray-800"
					>Color Palette</span
				>
			</div>
			<div class="mb-6 grid grid-cols-2 gap-2">
				<XTextField
					v-model:value="state.form.buttonColor"
					label="Button Color"
					type="color"
					class="p-1 h-10 w-14 block bg-white border border-gray-200 cursor-pointer rounded-lg disabled:opacity-50 disabled:pointer-events-none dark:bg-neutral-900 dark:border-neutral-700"
					title="Choose your color"
					required
					:invalid="state.valdn.buttonColor"
				/>
				<XTextField
					v-model:value="state.form.backgroundColor"
					label="Background Color"
					type="color"
					class="p-1 h-10 w-14 block bg-white border border-gray-200 cursor-pointer rounded-lg disabled:opacity-50 disabled:pointer-events-none dark:bg-neutral-900 dark:border-neutral-700"
					title="Choose your color"
					required
					:invalid="state.valdn.backgroundColor"
				/>
				<XTextField
					v-model:value="state.form.textColor"
					label="Text Color"
					type="color"
					class="p-1 h-10 w-14 block bg-white border border-gray-200 cursor-pointer rounded-lg disabled:opacity-50 disabled:pointer-events-none dark:bg-neutral-900 dark:border-neutral-700"
					title="Choose your color"
					required
					:invalid="state.valdn.textColor"
				/>
				<XTextField
					v-model:value="state.form.buttonTextColor"
					label="Button Text Color"
					type="color"
					class="p-1 h-10 w-14 block bg-white border border-gray-200 cursor-pointer rounded-lg disabled:opacity-50 disabled:pointer-events-none dark:bg-neutral-900 dark:border-neutral-700"
					title="Choose your color"
					required
					:invalid="state.valdn.buttonTextColor"
				/>
			</div>
			<div class="flex justify-end">
				<XButton
					@click="submit"
					prepend="i-carbon:save-series"
					type="submit"
					color="primary"
					label="Save"
				/>
			</div>
		</form>
	</XDrawer>
</template>
