<template>
	<div class="flex flex-col">
		<label
			v-if="label"
			:for="label"
			class="mb-2 block text-sm font-medium text-gray-900 dark:text-white"
			>{{ label }}</label
		>
		<select
			:id="label"
			:value="modelValue"
			class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500 dark:border-gray-600 dark:bg-gray-700 dark:text-white dark:placeholder-gray-400 dark:focus:border-blue-500 dark:focus:ring-blue-500"
		>
			<option
				v-for="option in options"
				:key="keyProp ? option[keyProp] : option"
				:value="keyProp ? option[keyProp] : option"
				:selected="isOptionSelected(option)"
				@click="onOptionSelected(option)"
			>
				<slot name="option" :option="option">
					{{ keyProp ? option[keyProp] : option }}
				</slot>
			</option>
		</select>
	</div>
</template>

<script setup lang="ts">
import { defineEmits, defineProps, PropType, ref } from 'vue';

const props = defineProps({
	modelValue: {
		type: null as PropType<unknown>,
	},
	keyProp: {
		type: String,
		default: '',
	},
	label: {
		type: String,
		default: '',
	},
	options: {
		type: Array as PropType<unknown[]>,
		required: true,
	},
});

const emit = defineEmits<{
	(e: 'update:modelValue', value: unknown): void;
}>();

function isOptionSelected(option: unknown) {
	if (!props.modelValue) {
		return false;
	}

	if (props.keyProp) {
		return option[props.keyProp] === props.modelValue?.[props.keyProp];
	}

	return option === props.modelValue;
}

function onOptionSelected(option: unknown) {
	emit('update:modelValue', isOptionSelected(option) ? undefined : option);
}
</script>
