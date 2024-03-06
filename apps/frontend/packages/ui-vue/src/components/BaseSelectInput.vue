<script setup lang="ts" generic="T extends OptionProps">
export type OptionProps = { [key: string]: string | number | boolean } | string | number | boolean;
export interface BaseSelectInputProps<T> {
	keyProp?: string;
	label?: string;
	options: T[];
	size: 'small' | 'medium' | 'large';
}
const model = defineModel<T>();

const props = withDefaults(defineProps<BaseSelectInputProps<T>>(), {
	keyProp: '',
	label: '',
	options: () => [],
	size: 'medium',
});

function isOptionSelected(option: T) {
	if (!model.value) {
		return false;
	}

	if (props.keyProp) {
		if (typeof option === 'object' && typeof model.value === 'object' && option !== null) {
			return option[props.keyProp] === model.value?.[props.keyProp];
		}
		return option === model.value;
	}

	return option === model.value;
}

const optionValue = (option: T) => {
	if (props.keyProp) {
		if (typeof option === 'object' && option !== null) {
			return option[props.keyProp];
		}
		return option;
	}

	return option;
};
</script>

<template>
	<div class="flex flex-col">
		<label
			v-if="label"
			:for="label"
			class="text-tertiary-900 mb-2 block text-sm font-medium dark:text-white"
			>{{ label }}</label
		>
		<select
			:id="label"
			v-model="model"
			:class="{
				'p-1 text-sm': size === 'small',
				'p-2.5 text-sm': size === 'medium',
				'px-4 py-3 text-base': size === 'large',
			}"
			class="border-tertiary-300 bg-tertiary-50 text-tertiary-900 dark:border-tertiary-600 dark:bg-tertiary-700 dark:placeholder-tertiary-400 block w-full rounded-lg border focus:border-blue-500 focus:ring-blue-500 dark:text-white dark:focus:border-blue-500 dark:focus:ring-blue-500"
		>
			<option
				v-for="option in options"
				:key="optionValue(option) as string"
				:value="optionValue(option)"
				:selected="isOptionSelected(option)"
			>
				<slot name="option" :option="option">
					{{ option }}
				</slot>
			</option>
		</select>
	</div>
</template>
