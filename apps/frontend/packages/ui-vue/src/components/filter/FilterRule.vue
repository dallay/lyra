<script setup lang="ts" generic="T">
import BasicDropdown from '@/components/dropdown/BasicDropdown.vue';
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

const showClearButton = computed(() => {
	return (
		property.value.value !== null &&
		property.value.value !== '' &&
		property.value.value !== undefined &&
		property.value.type !== 'select' &&
		property.value.type !== 'date'
	);
});
</script>

<template>
	<BasicDropdown :text="property.label" :placement="placement" @on-hide="applyFilters">
		<template #trigger>
			<span
				class="bg-blue-100 text-blue-800 text-xs font-medium inline-flex items-center px-2.5 py-0.5 rounded-full dark:bg-gray-700 dark:text-blue-400 border border-blue-400 mx-1"
			>
				<SvgIcon :name="getPropertyIcon(property.type)" class="w-4 h-4 me-2" />
				{{ property.label }}={{ property.operator }}:{{ property.value }}
				<button
					type="button"
					class="inline-flex items-center p-1 ms-2 text-sm text-blue-400 bg-transparent rounded-sm hover:bg-blue-200 hover:text-blue-900 dark:hover:bg-blue-800 dark:hover:text-blue-300"
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
			class="min-w-80 p-1 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700"
		>
			<header class="w-full flex items-center justify-around mb-2">
				<div class="flex justify-start items-center">
					<label :for="property.id" class="text-sm font-medium text-gray-900 dark:text-white">
						{{ property.label }}
					</label>
					<select
						:key="property.operator"
						v-model="property.operator"
						class="mx-1 rounded bg-gray-50 border border-gray-300 text-gray-900 text-sm focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
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
							class="inline-flex items-center p-1 ms-2 text-sm text-blue-400 bg-transparent rounded-sm"
						>
							<SvgIcon name="dots" class="w-5 h-5 text-gray-400 dark:text-gray-300" />
							<span class="sr-only">More options</span>
						</button>
					</template>
					<div
						class="min-w-max bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700"
					>
						<ul class="w-full text-sm text-gray-700 dark:text-gray-200">
							<li class="w-full flex">
								<button
									class="flex w-full px-4 py-2 text-left rounded hover:bg-gray-100 dark:hover:bg-gray-800"
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
				<input
					v-if="property.type != 'select'"
					:id="property.name"
					v-model="property.value"
					:type="property.type"
					:placeholder="property.label"
					class="block rounded p-2.5 w-full z-20 text-sm text-gray-900 bg-gray-50 border-s-gray-50 border-s-2 border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-s-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:border-blue-500"
					required
				/>
				<button
					v-if="showClearButton"
					:id="`${property.name}-clear`"
					type="button"
					class="absolute end-2.5 top-2.5 bottom-2.5 inline-flex items-center p-1 ms-2 text-sm text-blue-400 bg-transparent rounded-sm"
					@click="clearInput"
				>
					<SvgIcon name="close" class="w-5 h-5 text-gray-400 dark:text-gray-300" />
					<span class="sr-only">Clear input</span>
				</button>
				<select
					v-if="property.type == 'select'"
					:id="property.name"
					v-model="property.value"
					required
					class="w-full rounded p-2.5 bg-gray-50 border border-gray-300 text-gray-900 text-sm focus:ring-blue-500 focus:border-blue-500 block dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
				>
					<option v-for="(option, index) in property.options" :key="index" :value="option">
						{{ option }}
					</option>
				</select>
			</div>
		</div>
	</BasicDropdown>
</template>
