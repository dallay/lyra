<template>
	<div class="flex flex-col">
		<label
			v-if="label"
			:for="label"
			class="mb-2 block text-sm font-medium text-tertiary-900 dark:text-white"
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
			class="block w-full rounded-lg border border-tertiary-300 bg-tertiary-50 text-tertiary-900 focus:border-blue-500 focus:ring-blue-500 dark:border-tertiary-600 dark:bg-tertiary-700 dark:text-white dark:placeholder-tertiary-400 dark:focus:border-blue-500 dark:focus:ring-blue-500"
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

<script setup lang="ts" generic="T extends OptionProps">
import { type PropType } from 'vue';

export type OptionProps = { [key: string]: string | number | boolean } | string | number | boolean;

const model = defineModel({
	type: {} as PropType<T>,
});
const props = defineProps({
	keyProp: {
		type: String,
		default: '',
	},
	label: {
		type: String,
		default: '',
	},
	options: {
		type: Array as PropType<T[]>,
		required: true,
	},
	size: {
		type: String as PropType<'small' | 'medium' | 'large'>,
		default: 'medium',
	},
});

function isOptionSelected(option: T) {
	if (!model.value) {
		return false;
	}

	if (props.keyProp) {
    if(typeof option === 'object' && typeof model.value === 'object' && option !== null) {
      return option[props.keyProp] === model.value?.[props.keyProp];
    }
    return option === model.value;
	}

	return option === model.value;
}

const optionValue = (option: T) => {
  if (props.keyProp) {
    if(typeof option === 'object' && option !== null) {
      return option[props.keyProp];
    }
    return option;
  }

  return option;
};
</script>
