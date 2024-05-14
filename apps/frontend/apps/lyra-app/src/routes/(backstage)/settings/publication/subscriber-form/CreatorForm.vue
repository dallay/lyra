<script setup lang="ts">
import { CreateFormRequest, FormResponse } from '@lyra/api-services';
import { useValdnLocale, XButton, XDrawer, XTextField } from '@lyra/ui';
import { minLength, nullish, object, string } from 'valibot';
import { computed, onUnmounted, reactive, toRef } from 'vue';
import { useValibotSchema } from 'vue-formor';
import useStore from './store';

const creatorDrawer = defineModel<boolean>('creatorDrawer', {
	default: false,
});
const { actions } = useStore();

const valdnLocale = useValdnLocale();

const state = reactive({
	form: {
		id: crypto.randomUUID(),
		name: '',
		header: "Lyra's Newsletter",
		description: '',
		inputPlaceholder: 'Enter your email',
		buttonText: 'Subscribe',
		buttonColor: '#C02CE5',
		backgroundColor: '#F9FAFB',
		textColor: '#030712',
		buttonTextColor: '#FFFFFF',
	} as FormResponse,
	valdn: {} as Record<keyof FormResponse, string>,
});

const schema = useValibotSchema(
	computed(() =>
		object({
			name: nullish(string([minLength(1, valdnLocale.value.required)]), state.form.name),
			header: nullish(string([minLength(1, valdnLocale.value.required)]), state.form.header),
			description: nullish(
				string([minLength(1, valdnLocale.value.required)]),
				state.form.description
			),
			inputPlaceholder: nullish(
				string([minLength(1, valdnLocale.value.required)]),
				state.form.inputPlaceholder
			),
			buttonText: nullish(
				string([minLength(1, valdnLocale.value.required)]),
				state.form.buttonText
			),
			buttonColor: nullish(
				string([minLength(1, valdnLocale.value.required)]),
				state.form.buttonColor
			),
			backgroundColor: nullish(
				string([minLength(1, valdnLocale.value.required)]),
				state.form.backgroundColor
			),
			textColor: nullish(string([minLength(1, valdnLocale.value.required)]), state.form.textColor),
			buttonTextColor: nullish(
				string([minLength(1, valdnLocale.value.required)]),
				state.form.buttonTextColor
			),
		})
	),
	toRef(state, 'form'),
	toRef(state, 'valdn')
);

const $reset = async () => {
	state.form = {
		id: crypto.randomUUID(),
		name: '',
		header: "Lyra's Newsletter",
		description: '',
		inputPlaceholder: 'Enter your email',
		buttonText: 'Subscribe',
		buttonColor: '#C02CE5',
		backgroundColor: '#F9FAFB',
		textColor: '#030712',
		buttonTextColor: '#FFFFFF',
	} as FormResponse;

	state.valdn = {} as Record<keyof FormResponse, string>;
	schema.stop();
};

const submit = async () => {
	if (schema.validate()) {
		const form = state.form;
		const request: CreateFormRequest = {
			id: form.id,
			name: form.name,
			header: form.header,
			description: form.description,
			inputPlaceholder: form.inputPlaceholder,
			buttonText: form.buttonText,
			buttonColor: form.buttonColor,
			backgroundColor: form.backgroundColor,
			textColor: form.textColor,
			buttonTextColor: form.buttonTextColor,
		};
		await actions.create(request);
		await $reset();
		creatorDrawer.value = false;
	}
};

onUnmounted(() => {
	$reset();
});
</script>

<template>
	<XDrawer v-model="creatorDrawer" placement="right" class="p-4 !w-md">
		<h5
			class="inline-flex items-center mb-6 text-base font-semibold text-gray-500 uppercase dark:text-gray-400"
		>
			<i class="i-material-symbols-light:dynamic-form-rounded me-2.5"></i>
      Form Creator
		</h5>
		<XButton
			icon="i-material-symbols:close"
			size="small"
			variant="text"
			color="secondary"
			@click="creatorDrawer = false"
			class="absolute top-2.5 end-2.5"
		/>
    <form class="mb-6" @submit.prevent="submit">
			<div class="mb-6">
				<XTextField
					v-model:value="state.form.name"
					label="Name"
					required
					:invalid="state.valdn.name"
          data-testid="name"
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
          data-testid="header"
				/>
			</div>
			<div class="relative mb-6">
				<XTextField
					v-model:value="state.form.description"
					label="Description"
					required
					:invalid="state.valdn.description"
          data-testid="description"
				/>
			</div>
			<div class="mb-6">
				<XTextField
					v-model:value="state.form.inputPlaceholder"
					label="Input Placeholder"
					required
					:invalid="state.valdn.inputPlaceholder"
          data-testid="inputPlaceholder"
				/>
			</div>
			<div class="mb-6">
				<XTextField
					v-model:value="state.form.buttonText"
					label="Button Text"
					required
					:invalid="state.valdn.buttonText"
          data-testid="buttonText"
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
          data-testid="buttonColor"
        />
        <XTextField
          v-model:value="state.form.buttonTextColor"
          label="Button Text Color"
          type="color"
          class="p-1 h-10 w-14 block bg-white border border-gray-200 cursor-pointer rounded-lg disabled:opacity-50 disabled:pointer-events-none dark:bg-neutral-900 dark:border-neutral-700"
          title="Choose your color"
          required
          :invalid="state.valdn.buttonTextColor"
          data-testid="buttonTextColor"
				/>
				<XTextField
					v-model:value="state.form.backgroundColor"
					label="Background Color"
					type="color"
					class="p-1 h-10 w-14 block bg-white border border-gray-200 cursor-pointer rounded-lg disabled:opacity-50 disabled:pointer-events-none dark:bg-neutral-900 dark:border-neutral-700"
					title="Choose your color"
					required
					:invalid="state.valdn.backgroundColor"
          data-testid="backgroundColor"
				/>
				<XTextField
					v-model:value="state.form.textColor"
					label="Text Color"
					type="color"
					class="p-1 h-10 w-14 block bg-white border border-gray-200 cursor-pointer rounded-lg disabled:opacity-50 disabled:pointer-events-none dark:bg-neutral-900 dark:border-neutral-700"
					title="Choose your color"
					required
					:invalid="state.valdn.textColor"
          data-testid="textColor"
				/>
			</div>
			<div class="flex justify-end">
				<XButton
					prepend="i-material-symbols:add"
					type="submit"
					color="primary"
					label="Create Form"
          data-testid="createForm"
				/>
			</div>
		</form>
	</XDrawer>
</template>
