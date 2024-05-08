<script setup lang="ts">
import { XButton, XCodeBlock, XDrawer } from '@lyra/ui';
import { computed, onMounted, reactive, watch } from 'vue';
import { FormResponse } from '@lyra/api-services';

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

const iFrame = computed(() => {
	return `<iframe src="${url}/${state.form.id}" data-test-id="lyra-embed" width="100%" height="250" frameborder="0" scrolling="no" style="border-radius: 4px; border: 2px solid #e5e7eb; margin: 0; background-color: transparent;"></iframe>`;
});
const iFrameFixed = computed(() => {
	return `<iframe :src="${url}/${state.form.id}"data-test-id="lyra-embed" width="350" height="250" frameborder="0" scrolling="no" style="border-radius: 4px; border: 2px solid #e5e7eb; margin: 0; background-color: transparent;"></iframe>`;
});
const iFrameSlim = computed(() => {
	return `<iframe :src="${url}/${state.form.id}?slim=true" data-test-id="lyra-embed" width="100%" height="48" frameborder="0" scrolling="no" style="margin: 0; border-radius: 0px !important; background-color: transparent;"></iframe>`;
});

const deleteForm = () => {
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

			<div class="relative inline-flex w-full items-center justify-center">
				<hr class="my-8 h-px w-full border-0 bg-gray-200 dark:bg-gray-700" />
				<span class="absolute left-1/2 -translate-x-1/2 bg-white px-3 font-medium dark:bg-gray-800"
					>Full Width</span
				>
			</div>
			<p class="text-wrap">
				Embed this code directly in-line within your website, and it will expand to the full width
				of its surrounding container.
			</p>

			<XCodeBlock :code="iFrame" language="html" />

			<div class="border border-gray-200 dark:border-gray-700 my-6">
				<!-- The embed example  -->
				<iframe
					:src="`${url}/${state.form.id}`"
					data-test-id="lyra-embed"
					width="100%"
					height="250"
					frameborder="0"
					scrolling="no"
					style="
						border-radius: 4px;
						border: 2px solid #e5e7eb;
						margin: 0;
						background-color: transparent;
					"
				></iframe>
			</div>

			<div class="relative inline-flex w-full items-center justify-center">
				<hr class="my-8 h-px w-full border-0 bg-gray-200 dark:bg-gray-700" />
				<span class="absolute left-1/2 -translate-x-1/2 bg-white px-3 font-medium dark:bg-gray-800"
					>Fixed Width</span
				>
			</div>
			<p class="text-wrap">
				Embed this code directly in-line with your website, and it will expand to the specified
				maximum width.
			</p>

			<XCodeBlock :code="iFrameFixed" language="html" />

			<div class="border border-gray-200 dark:border-gray-700 my-6">
				<iframe
					:src="`${url}/${state.form.id}`"
					data-test-id="lyra-embed"
					width="350"
					height="250"
					frameborder="0"
					scrolling="no"
					style="
						border-radius: 4px;
						border: 2px solid #e5e7eb;
						margin: 0;
						background-color: transparent;
					"
				></iframe>
			</div>

			<div class="relative inline-flex w-full items-center justify-center">
				<hr class="my-8 h-px w-full border-0 bg-gray-200 dark:bg-gray-700" />
				<span class="absolute left-1/2 -translate-x-1/2 bg-white px-3 font-medium dark:bg-gray-800"
					>Slim Embed</span
				>
			</div>
			<p class="text-wrap">
				This offers the most control with placement without providing a background, header, or
				description.
			</p>

			<XCodeBlock :code="iFrameSlim" language="html" />

			<div class="border border-gray-200 dark:border-gray-700 my-6">
				<iframe
					:src="`${url}/${state.form.id}?slim=true`"
					data-test-id="lyra-embed"
					width="100%"
					height="48"
					frameborder="0"
					scrolling="no"
					style="margin: 0; border-radius: 0px !important; background-color: transparent"
				></iframe>
			</div>

			<!-- Delete Button -->
			<div class="mt-6 flex justify-end">
				<XButton variant="text" color="danger" @click="deleteForm">Delete Form</XButton>
			</div>
		</form>
	</XDrawer>
</template>
