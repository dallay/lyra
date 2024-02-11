<script setup lang="ts" generic="T">
import BasicDropdown from '@/components/dropdown/BasicDropdown.vue';
import BaseInput from '@/components/BaseInput.vue';
import { computed, type PropType } from 'vue';
import type { FilterType, Property } from '@lyra/vm-core';
import SvgIcon from '../media/SvgIcon.vue';
import type { DropdownPlacement } from '@/components/dropdown/types';

const emit = defineEmits(['removeFilterRule', 'applyFilters', 'clearInputFilter']);
const property = defineModel({
	type: Object as PropType<Property<T>>,
	required: true,
});

defineProps({
	placement: {
		type: String as PropType<DropdownPlacement>,
		default: 'bottom',
	},
});
const getPropertyIcon = (type: FilterType) => {
	switch (type) {
		case 'text':
			return 'text';
		case 'email':
			return 'email';
		case 'select':
			return 'status';
		default:
			return 'text';
	}
};

const removeFilterRule = (property: Property<T>) => {
	emit('removeFilterRule', property);
};
const clearInput = () => {
	property.value.value = null;
	emit('clearInputFilter');
};

const applyFilters = () => {
	emit('applyFilters');
};

// const showClearButton = computed(() => {
// 	return (
// 		property.value.value !== null &&
// 		property.value.value !== '' &&
// 		property.value.value !== undefined &&
// 		property.value.type !== 'select' &&
// 		property.value.type !== 'date'
// 	);
// });
</script>

<template>
	<BasicDropdown :text="property.label" :placement="placement" @on-hide="applyFilters">
		<template #trigger>
			<span
				class="bg-secondary-100 text-secondary-800 text-xs font-medium inline-flex items-center px-2.5 py-0.5 rounded-full dark:bg-tertiary-700 dark:text-secondary-400 border border-secondary-400/50 mx-1"
			>
				<SvgIcon :name="getPropertyIcon(property.type)" class="w-4 h-4 me-2" />
				{{ property.label }}={{ property.operator }}:{{ property.value }}
				<button
					type="button"
					class="inline-flex items-center p-1 ms-2 text-sm text-secondary-400 bg-transparent rounded-sm hover:bg-secondary-200 hover:text-secondary-900 dark:hover:bg-secondary-800 dark:hover:text-secondary-300"
					@click="removeFilterRule(property)"
				>
					<svg
						class="w-2 h-2"
						aria-hidden="true"
						xmlns="http://www.w3.org/2000/svg"
						fill="none"
						viewBox="0 0 14 14"
					>
						<path
							stroke="currentColor"
							stroke-linecap="round"
							stroke-linejoin="round"
							stroke-width="2"
							d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
						/>
					</svg>
					<span class="sr-only">Remove filter rule</span>
				</button>
			</span>
		</template>

		<div
			class="min-w-80 p-1 bg-white border border-tertiary-200 rounded-lg shadow dark:bg-tertiary-800 dark:border-tertiary-700"
		>
			<header class="w-full flex items-center justify-around mb-2">
				<div class="flex justify-start items-center">
					<label :for="property.id" class="text-sm font-medium text-tertiary-900 dark:text-white">
						{{ property.label }}
					</label>
					<select
						:key="property.operator"
						v-model="property.operator"
						class="mx-1 rounded bg-tertiary-50 border border-tertiary-300 text-tertiary-900 text-sm focus:ring-secondary-500 focus:border-secondary-500 dark:bg-tertiary-700 dark:border-tertiary-600 dark:placeholder-tertiary-400 dark:text-white dark:focus:ring-secondary-500 dark:focus:border-secondary-500"
					>
						<option
							v-for="operator in property.availableOperators"
							:key="operator.label"
							:value="operator.value"
							:selected="operator.value == property.operator"
						>
							{{ operator.label }}
						</option>
					</select>
				</div>
				<BasicDropdown text="Options" close-inside :placement="placement">
					<template #trigger>
						<button
							type="button"
							class="inline-flex items-center p-1 ms-2 text-sm text-secondary-400 bg-transparent rounded-sm"
						>
							<SvgIcon name="dots" class="w-5 h-5 text-tertiary-400 dark:text-tertiary-300" />
							<span class="sr-only">More options</span>
						</button>
					</template>
					<div
						class="min-w-max bg-white border border-tertiary-200 rounded-lg shadow dark:bg-tertiary-800 dark:border-tertiary-700"
					>
						<ul class="w-full text-sm text-tertiary-700 dark:text-tertiary-200">
							<li class="w-full flex">
								<button
									class="flex w-full px-4 py-2 text-left rounded hover:bg-tertiary-100 dark:hover:bg-tertiary-800"
									@click="removeFilterRule(property)"
								>
									<SvgIcon name="trash" class="w-4 h-4 me-2" />
									Delete filter
								</button>
							</li>
						</ul>
					</div>
				</BasicDropdown>
			</header>
			<div class="relative">
<!--				<input-->
<!--					v-if="property.type != 'select'"-->
<!--					:id="property.name"-->
<!--					v-model="property.value"-->
<!--					:type="property.type"-->
<!--					:placeholder="property.label"-->
<!--					class="block rounded p-2.5 w-full z-20 text-sm text-tertiary-900 bg-tertiary-50 border-s-tertiary-50 border-s-2 border border-tertiary-300 focus:ring-secondary-500 focus:border-secondary-500 dark:bg-tertiary-700 dark:border-s-tertiary-700 dark:border-tertiary-600 dark:placeholder-tertiary-400 dark:text-white dark:focus:border-secondary-500"-->
<!--					required-->
<!--				/>-->
<!--				<button-->
<!--					v-if="showClearButton"-->
<!--					:id="`${property.name}-clear`"-->
<!--					type="button"-->
<!--					class="absolute end-2.5 top-2.5 bottom-2.5 inline-flex items-center p-1 ms-2 text-sm text-secondary-400 bg-transparent rounded-sm"-->
<!--					@click="clearInput"-->
<!--				>-->
<!--					<SvgIcon name="close" class="w-5 h-5 text-tertiary-400 dark:text-tertiary-300" />-->
<!--					<span class="sr-only">Clear input</span>-->
<!--				</button>-->
        <BaseInput
          v-if="property.type != 'select'"
          :id="property.name"
          v-model="property.value"
          :type="property.type"
          :placeholder="property.label"
          @clear-input="clearInput"
        />
				<select
					v-if="property.type == 'select'"
					:id="property.name"
					v-model="property.value"
					required
					class="w-full rounded p-2.5 bg-tertiary-50 border border-tertiary-300 text-tertiary-900 text-sm focus:ring-secondary-500 focus:border-secondary-500 block dark:bg-tertiary-700 dark:border-tertiary-600 dark:placeholder-tertiary-400 dark:text-white dark:focus:ring-secondary-500 dark:focus:border-secondary-500"
				>
					<option v-for="(option, index) in property.options" :key="index" :value="option">
						{{ option }}
					</option>
				</select>
			</div>
		</div>
	</BasicDropdown>
</template>
