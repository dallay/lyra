<script setup lang="ts" generic="T">
import SvgIcon from '@/components/media/SvgIcon.vue';
import FilterRule from '@/components/filter/FilterRule.vue';
import { defineProps, type PropType, Ref, ref } from 'vue';
import {
	BasicFilter,
	convertFieldPropertyToProperty,
	convertPropertyToFieldProperty,
	FieldProperty,
	Filter,
	FilterType,
	Property,
} from '@lyra/vm-core';
import BasicDropdown from '@/components/dropdown/BasicDropdown.vue';

const emit = defineEmits(['applyFilters', 'removeFilterRule', 'clearInputFilter']);

const props = defineProps({
	fields: {
		type: Array as PropType<FieldProperty<T>[]>,
		required: true,
	},
});

const showDropdown = ref(false);
const filter: Ref<Filter<T>> = ref(new BasicFilter<T>('poc', []));
const availableFieldProperties: Ref<FieldProperty<T>[]> = ref(props.fields);

const removeFilterRule = (property: Property<T>) => {
	filter.value.removeProperty(property.id);
	availableFieldProperties.value.push(convertPropertyToFieldProperty(property));
	applyQueryFilter();
	emit('removeFilterRule', property);
};

function applyQueryFilter() {
	const query = filter.value.toQueryString();
	emit('applyFilters', query);
}

const addFilterProperty = (property: FieldProperty<T>) => {
	filter.value.addProperty(convertFieldPropertyToProperty(property));
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
		<BasicDropdown v-model="showDropdown" text="Filters" close-inside @on-hide="applyQueryFilter">
			<template #trigger>
				<button class="flex items-center justify-center">
					<SvgIcon v-if="filter.properties.length === 0" name="filter" class="w-8 h-8" />
					<SvgIcon v-else name="add" class="w-5 h-5" />
					<span
						:class="{
							'sr-only': filter.properties.length === 0,
						}"
						>Add filter</span
					>
				</button>
			</template>
			<div
				id="generalFilter"
				class="z-10 min-w-max bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700"
			>
				<ul class="py-2 text-sm text-gray-700 dark:text-gray-200">
					<li v-for="fieldProperty in availableFieldProperties" :key="fieldProperty.name">
						<button
							class="flex w-full px-4 py-2 text-left rounded hover:bg-gray-100 dark:hover:bg-gray-800"
							@click="addFilterProperty(fieldProperty)"
						>
							<SvgIcon :name="getPropertyIcon(fieldProperty.type)" class="w-4 h-4 me-2" />
							{{ fieldProperty.label }}
						</button>
					</li>
				</ul>
			</div>
		</BasicDropdown>
	</div>
</template>

<style scoped></style>
