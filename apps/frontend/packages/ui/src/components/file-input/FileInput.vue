<script lang="ts" setup>
import { reactive, toRef } from 'vue';

import FormControl from '../form-control/FormControl.vue';
import Chip from '../chip/Chip.vue';

const props = defineProps<{
	label?: string;
	required?: boolean;
	placeholder?: string;
	disabled?: boolean;
	invalid?: boolean | string;
	help?: string;
}>();

const emit = defineEmits<{
	(evt: 'change', val: Event): void;
}>();

const placeholderRef = toRef(props, 'placeholder', 'Choose a file');

const flux = reactive({
	fileNames: [] as string[],
	onChange(event: Event) {
		const el = event.target as HTMLInputElement;
		const files = Array.from(el.files || []);
		flux.fileNames = files.map((file) => file.name);
		emit('change', event);
	},
});
</script>

<template>
	<FormControl v-slot="{ uid }" :label="label" :required="required" :invalid="invalid" :help="help">
		<label
			:for="uid"
			class="FileInput-Input"
			:class="{
				'!py-1': flux.fileNames.length > 1,
				disabled,
			}"
		>
			<div v-if="flux.fileNames.length > 1" class="flex flex-wrap gap-1">
				<Chip v-for="item in flux.fileNames" :key="item">
					{{ item }}
				</Chip>
			</div>
			<div v-else-if="flux.fileNames.length === 1">{{ flux.fileNames[0] }}</div>
			<div v-else class="text-slate-400 dark:text-slate-500">{{ placeholderRef }}</div>

			<div class="ms-1">
				<div class="i-material-symbols-upload-rounded h-5 w-5"></div>
			</div>
		</label>

		<input
			:id="uid"
			ref="input"
			v-bind="$attrs"
			type="file"
			:disabled="disabled"
			class="hidden"
			@change="flux.onChange"
			@click="($refs.input as HTMLInputElement).value = ''"
		/>
	</FormControl>
</template>

<style lang="scss" scoped>
.FileInput-Input {
	@apply flex w-full items-center justify-between rounded border px-3 py-2 leading-tight;
	@apply border-slate-500 bg-white dark:border-slate-400 dark:bg-slate-800;
	@apply placeholder:text-slate-400 dark:placeholder:text-slate-500;
	@apply focus:ring-primary-500/50 focus:border-primary-400 focus:outline-none focus:ring-2;

	&.invalid {
		@apply border-red-500 dark:border-red-500;
		@apply focus:border-red-500 focus:ring-red-500/50;
	}

	&.disabled {
		@apply cursor-not-allowed opacity-60;
	}
}
</style>
