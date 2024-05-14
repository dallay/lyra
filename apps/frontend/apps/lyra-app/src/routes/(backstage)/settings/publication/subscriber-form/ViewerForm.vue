<script setup lang="ts">
import { FormResponse } from '@lyra/api-services';
import { XButton, XDrawer } from '@lyra/ui';
import { computed, onMounted, onUnmounted, reactive, watch } from 'vue';
import IframeSection from './IframeSection.vue';

const viewerDrawer = defineModel<boolean>('viewerDrawer', {
	default: false,
});

const form = defineModel<FormResponse>('form', {
	required: true,
});
const emit = defineEmits<{
	(evt: 'delete', id: string): void;
}>();
const url = `http://localhost:9876`;
const state = reactive({
	form: form.value,
});
const _embedOptions = computed(() => [
	{
		title: 'Full Width',
		description:
			'Embed this code directly in-line within your website, and it will expand to the full width of its surrounding container.',
		iframeSrc: `${url}/${state.form.id}`,
		iframeWidth: '100%',
		iframeHeight: '210',
		iframeStyle:
			'border-radius: 4px; border: 2px solid #e5e7eb; margin: 0; background-color: transparent;',
	},
	{
		title: 'Fixed Width',
		description:
			'Embed this code directly in-line with your website, and it will expand to the specified maximum width.',
		iframeSrc: `${url}/${state.form.id}`,
		iframeWidth: '325',
		iframeHeight: '205',
		iframeStyle: 'margin: 0; border-radius: 0px !important; background-color: transparent',
	},
	{
		title: 'Slim Embed',
		description:
			'This offers the most control with placement without providing a background, header, or description.',
		iframeSrc: `${url}/${state.form.id}?slim=true`,
		iframeWidth: '100%',
		iframeHeight: '48',
		iframeStyle: 'margin: 0; border-radius: 0px !important; background-color: transparent',
	},
]);

const _deleteForm = async () => {
	emit('delete', state.form.id);
	viewerDrawer.value = false;
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

onUnmounted(() => {
	state.form = {} as FormResponse;
});
</script>

<template>
	<XDrawer v-model="viewerDrawer" placement="right" class="p-4 !w-md">
		<h5
			class="inline-flex items-center mb-6 text-base font-semibold text-gray-500 uppercase dark:text-gray-400"
		>
			<i class="i-material-symbols-light:dynamic-form-rounded me-2.5"></i>
			{{ state.form.name }}
		</h5>
		<XButton
			icon="i-material-symbols:close"
			size="small"
			variant="text"
			color="secondary"
			@click="viewerDrawer = false"
			class="absolute top-2.5 end-2.5"
		/>
		<form class="mb-6 flex flex-col">
			<div class="relative inline-flex w-full items-center justify-center">
				<hr class="my-8 h-px w-full border-0 bg-gray-200 dark:bg-gray-700" />
				<span class="absolute left-1/2 -translate-x-1/2 bg-white px-3 font-medium dark:bg-gray-800"
					>Embed Options</span
				>
			</div>
			<p class="text-wrap">
				Below are various ways you can embed your subscribe form that offer various levels of
				control. Make sure to choose the best one for your use case.
			</p>

      <IframeSection v-for="option in embedOptions" :key="option.title" v-bind="option" />

			<!-- Delete Button -->
			<div class="mt-6 flex justify-end">
				<XButton variant="text" color="danger" @click="deleteForm">Delete Form</XButton>
			</div>
		</form>
	</XDrawer>
</template>
