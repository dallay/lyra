<script setup lang="ts" generic="T extends Property<string | number | Date>">
import BasicDropdown from '@/components/dropdown/BasicDropdown.vue';
import BaseInput from '@/components/BaseInput.vue';
import SvgIcon from '../media/SvgIcon.vue';
import type { DropdownPlacement } from '@/components/dropdown/types';
import { type Property } from '@/components/filter/Property';
import { type FilterType } from '@/components/filter/Filter';

export interface FilterRuleProps {
	placement?: DropdownPlacement;
}
const property = defineModel<T>({
	required: true,
});

withDefaults(defineProps<FilterRuleProps>(), {
	placement: 'bottom',
});

const emit = defineEmits<{
	(evt: 'removeFilterRule', val: T): void;
	(evt: 'applyFilters'): void;
	(evt: 'clearInputFilter'): void;
}>();
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

const removeFilterRule = (property: T) => {
	emit('removeFilterRule', property);
};
const clearInput = () => {
	property.value.value = '';
	emit('clearInputFilter');
};

const applyFilters = () => {
	emit('applyFilters');
};
</script>

<template>
	<BasicDropdown :text="property.label" :placement="placement" @on-hide="applyFilters">
		<template #trigger>
			<span
				class="bg-secondary-100 text-secondary-800 dark:bg-tertiary-700 dark:text-secondary-400 border-secondary-400/50 mx-1 inline-flex items-center rounded-full border px-2.5 py-0.5 text-xs font-medium"
			>
				<SvgIcon :name="getPropertyIcon(property.type)" class="me-2 h-4 w-4" />
				{{ property.label }}={{ property.operator }}:{{ property.value }}
				<button
					type="button"
					class="text-secondary-400 hover:bg-secondary-200 hover:text-secondary-900 dark:hover:bg-secondary-800 dark:hover:text-secondary-300 ms-2 inline-flex items-center rounded-sm bg-transparent p-1 text-sm"
					@click="removeFilterRule(property)"
				>
					<svg
						class="h-2 w-2"
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
			class="border-tertiary-200 dark:bg-tertiary-800 dark:border-tertiary-700 min-w-80 rounded-lg border bg-white p-1 shadow"
		>
			<header class="mb-2 flex w-full items-center justify-around">
				<div class="flex items-center justify-start">
					<label :for="property.id" class="text-tertiary-900 text-sm font-medium dark:text-white">
						{{ property.label }}
					</label>
					<select
						:key="property.operator"
						v-model="property.operator"
						class="bg-tertiary-50 border-tertiary-300 text-tertiary-900 focus:ring-secondary-500 focus:border-secondary-500 dark:bg-tertiary-700 dark:border-tertiary-600 dark:placeholder-tertiary-400 dark:focus:ring-secondary-500 dark:focus:border-secondary-500 mx-1 rounded border text-sm dark:text-white"
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
							class="text-secondary-400 ms-2 inline-flex items-center rounded-sm bg-transparent p-1 text-sm"
						>
							<SvgIcon name="dots" class="text-tertiary-400 dark:text-tertiary-300 h-5 w-5" />
							<span class="sr-only">More options</span>
						</button>
					</template>
					<div
						class="border-tertiary-200 dark:bg-tertiary-800 dark:border-tertiary-700 min-w-max rounded-lg border bg-white shadow"
					>
						<ul class="text-tertiary-700 dark:text-tertiary-200 w-full text-sm">
							<li class="flex w-full">
								<button
									type="button"
									class="hover:bg-tertiary-100 dark:hover:bg-tertiary-800 flex w-full rounded px-4 py-2 text-left"
									@click="removeFilterRule(property)"
								>
									<SvgIcon name="trash" class="me-2 h-4 w-4" />
									Delete filter
								</button>
							</li>
						</ul>
					</div>
				</BasicDropdown>
			</header>
			<div class="relative">
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
					class="bg-tertiary-50 border-tertiary-300 text-tertiary-900 focus:ring-secondary-500 focus:border-secondary-500 dark:bg-tertiary-700 dark:border-tertiary-600 dark:placeholder-tertiary-400 dark:focus:ring-secondary-500 dark:focus:border-secondary-500 block w-full rounded border p-2.5 text-sm dark:text-white"
				>
					<option v-for="(option, index) in property.options" :key="index" :value="option">
						{{ option }}
					</option>
				</select>
			</div>
		</div>
	</BasicDropdown>
</template>
