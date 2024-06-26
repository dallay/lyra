<script lang="ts" setup>
import { ref } from 'vue';

import FormControl from '../form-control/FormControl.vue';
import useNotification from '../../composables/notification/useNotification';

defineOptions({
	inheritAttrs: false,
});

const props = defineProps<{
	label?: string;
	required?: boolean;
	invalid?: boolean | string;
	help?: string;
	title?: string;
	limit?: (file: File) => string;
}>();

const emit = defineEmits<{
	(evt: 'upload', file: File, formData: FormData): void;
}>();

const notification = useNotification();

const dragover = ref(false);

function fileLimit(file: File) {
	let message = '';

	if (!file.type.includes('image')) {
		message = `Only graphic file formats can be uploaded`;
	} else if (file.size > 100 * 1024) {
		message = `Do not upload more than 100 kB`;
	}

	if (message) {
		notification.add({
			message,
			color: 'danger',
			icon: 'i-mdi-close-circle-outline',
		});
	}

	return message;
}

function onDrop(evt: DragEvent) {
	dragover.value = false;

	const file = evt.dataTransfer?.files?.[0];

	if (file) {
		if (typeof props.limit === 'function' ? props.limit(file) : fileLimit(file)) return;

		const formData = new FormData();
		formData.append('file', file);
		emit('upload', file, formData);
	}
}

function onChange(evt: Event) {
	const el = evt.target as HTMLInputElement;
	const file = el?.files?.[0];

	if (file) {
		if (typeof props.limit === 'function' ? props.limit(file) : fileLimit(file)) return;

		const formData = new FormData();
		formData.append('file', file);
		emit('upload', file, formData);
	}
}

function onDragOver() {
	dragover.value = true;
}

function onDragLeave() {
	dragover.value = false;
}
</script>

<template>
	<FormControl :label="label" :required="required" :invalid="invalid" :help="help">
		<label
			v-bind="$attrs"
			class="Dropzone"
			:class="{ '!dark:bg-slate-700/75 !bg-slate-200/75': dragover }"
			@dragover.prevent="onDragOver"
			@dragleave.prevent="onDragLeave"
			@drop.prevent="onDrop"
		>
			<input
				ref="dropzone"
				type="file"
				class="hidden"
				@change="onChange"
				@click="($refs.dropzone as HTMLInputElement).value = ''"
			/>
			<slot name="dropzone">
				<div class="i-mdi-tray-arrow-up h-24 w-24"></div>
				<div class="text-xl">{{ title || 'Click or drag to upload image' }}</div>
			</slot>
		</label>
	</FormControl>
</template>

<style lang="scss" scoped>
.Dropzone {
	@apply flex min-h-64 w-full cursor-pointer flex-col items-center justify-center;
	@apply rounded border-4 border-dashed border-slate-400/75 bg-white hover:border-slate-400 hover:bg-slate-200/75 dark:bg-slate-800 dark:hover:bg-slate-700/75;
}
</style>
