<script setup lang="ts">
import SvgIcon from '@/components/media/SvgIcon.vue';
import { computed } from 'vue';
import { type FilterType } from '@/components/filter/Filter';
export interface BaseInputProps {
	id: string;
	type: FilterType;
	placeholder: string;
}
const model = defineModel<string | number | Date>({
	default: '',
});

const props = withDefaults(defineProps<BaseInputProps>(), {
	id: crypto.randomUUID(),
	type: 'text',
	placeholder: '',
});

const emit = defineEmits<{
	(evt: 'clearInput'): void;
}>();
const clearInput = () => {
	model.value = '';
	emit('clearInput');
};
const showClearButton = computed(() => {
	return (
		model.value !== '' &&
		model.value !== null &&
		model.value !== undefined &&
		props.type !== 'select' &&
		props.type !== 'date'
	);
});
</script>

<template>
	<div class="relative">
		<input
			:id="id"
			v-model="model"
			:type="type"
			:placeholder="placeholder"
			class="text-tertiary-900 bg-tertiary-50 border-s-tertiary-50 border-tertiary-300 focus:ring-secondary-500 focus:border-secondary-500 dark:bg-tertiary-700 dark:border-s-tertiary-700 dark:border-tertiary-600 dark:placeholder-tertiary-400 dark:focus:border-secondary-500 z-20 block w-full rounded border border-s-2 p-2.5 text-sm dark:text-white"
			required
		/>
		<button
			v-if="showClearButton"
			:id="`${id}-clear`"
			type="button"
			class="text-secondary-400 absolute bottom-2.5 end-2.5 top-2.5 ms-2 inline-flex items-center rounded-sm bg-transparent p-1 text-sm"
			@click="clearInput"
		>
			<SvgIcon name="close" class="text-tertiary-400 dark:text-tertiary-300 h-5 w-5" />
			<span class="sr-only">Clear input</span>
		</button>
	</div>
</template>

<style scoped></style>
