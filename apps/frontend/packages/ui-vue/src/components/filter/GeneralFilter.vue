<script setup lang="ts" generic="T extends Property<string | number | Date>">
import SvgIcon from '@/components/media/SvgIcon.vue';
import FilterRule from '@/components/filter/FilterRule.vue';
import { unref, ref, computed } from 'vue';

import BasicDropdown from '@/components/dropdown/BasicDropdown.vue';
import type { DropdownPlacement } from '@/components/dropdown/types';
import { type Property } from '@/components/filter/Property';
import { BasicFilter, type Filter, type FilterType } from '@/components/filter/Filter';

export interface GeneralFilterProps<T> {
	properties: T[];
	placement: DropdownPlacement;
}
const props = withDefaults(defineProps<GeneralFilterProps<T>>(), {
	placement: () => 'bottom',
});

const emit = defineEmits<{
	(evt: 'applyFilters', val: string): void;
	(evt: 'removeFilterRule', val: T): void;
	(evt: 'clearInputFilter'): void;
}>();

const showDropdown = ref(false);
const filter = ref<Filter<T>>(new BasicFilter<T>('generalFilter', []));
const availableFieldProperties = computed({
	get: () => props.properties,
	set: (value: T[]) => {
		availableFieldProperties.value = value;
	},
});

const removeFilterRule = (property: T) => {
	const unwrappedProperty: T = unref(property);
	filter.value.removeProperty(unwrappedProperty.id);
	availableFieldProperties.value.push(unwrappedProperty);
	applyQueryFilter();
	emit('removeFilterRule', unwrappedProperty);
};

function applyQueryFilter() {
	const query = filter.value.toQueryString();
	emit('applyFilters', query);
}

const addFilterProperty = (property: T) => {
	filter.value.addProperty(property);
	availableFieldProperties.value = availableFieldProperties.value.filter(
		(fieldProperty) => fieldProperty.name !== property.name
	);
	applyQueryFilter();
};

const getPropertyIcon = (type: FilterType) => {
	switch (type) {
		case 'text':
			return 'text';
		case 'email':
			return 'email';
		case 'select':
			return 'status';
		case 'date':
			return 'calendar';
		default:
			return 'text';
	}
};
const clearInputFilter = () => {
	emit('clearInputFilter');
};
</script>

<template>
	<div class="flex items-center">
		<FilterRule
			v-for="(property, index) in filter.properties"
			:key="property.id"
			v-model="filter.properties[index]"
			@apply-filters="applyQueryFilter"
			@clear-input-filter="clearInputFilter"
			@remove-filter-rule="removeFilterRule"
		/>
		<BasicDropdown
			v-model="showDropdown"
			text="Filters"
			close-inside
			:placement="placement"
			@on-hide="applyQueryFilter"
		>
			<template #trigger>
				<button
					id="addFilter"
					type="button"
					:disabled="availableFieldProperties.length === 0"
					class="hover:bg-tertiary-100 dark:hover:bg-tertiary-800 flex items-center justify-center rounded-md p-2"
					:class="{
						hidden: availableFieldProperties.length === 0,
					}"
				>
					<SvgIcon v-if="filter.properties.length === 0" name="filter" class="h-8 w-8" />
					<SvgIcon v-else name="add" class="h-5 w-5" />
					<span
						:class="{
							'sr-only': filter.properties.length === 0,
						}"
					>
						Add filter
					</span>
				</button>
			</template>
			<div
				id="generalFilter"
				class="border-tertiary-200 dark:bg-tertiary-800 dark:border-tertiary-700 z-10 min-w-max rounded-lg border bg-white shadow"
			>
				<ul class="text-tertiary-700 dark:text-tertiary-200 py-2 text-sm">
					<li v-for="fieldProperty in availableFieldProperties" :key="fieldProperty.name">
						<button
							type="button"
							class="hover:bg-tertiary-100 dark:hover:bg-tertiary-800 flex w-full rounded px-4 py-2 text-left"
							@click="addFilterProperty(fieldProperty)"
						>
							<SvgIcon :name="getPropertyIcon(fieldProperty.type)" class="me-2 h-4 w-4" />
							{{ fieldProperty.label }}
						</button>
					</li>
				</ul>
			</div>
		</BasicDropdown>
	</div>
</template>

<style scoped></style>
