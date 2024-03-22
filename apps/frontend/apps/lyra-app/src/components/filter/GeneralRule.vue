<script setup lang="ts">
import { XButton, XListbox, XPopover } from '@lyra/ui';
import { computed, reactive, ref } from 'vue';
import { BasicFilter, ColumnProperty, Filter } from './Filter';
import FilterRule from '@/components/filter/FilterRule.vue';

interface GeneralFilterProps {
	columns: ColumnProperty[];
}

const props = defineProps<GeneralFilterProps>();

const emit = defineEmits<{
	(evt: 'applyFilters', val: string): void;
	(evt: 'removeFilterProperty', val: ColumnProperty): void;
	(evt: 'addFilterProperty', val: ColumnProperty): void;
	(evt: 'clearFilters'): void;
}>();

const filterStatus = ref(false);

const flux = reactive({
	filter: new BasicFilter([] as ColumnProperty[]) as Filter<ColumnProperty>,
	availableColumns: props.columns,
});

const availableColumns = computed(() =>
	[...flux.availableColumns].sort((a, b) => a.name.localeCompare(b.name))
);

const applyQueryFilters = () => {
	emit('applyFilters', flux.filter.toQueryString());
};
const addFilterProperty = (column: ColumnProperty) => {
	flux.filter.addProperty(column);
	flux.availableColumns = flux.availableColumns.filter((c) => c.key !== column.key);
	applyQueryFilters();
	emit('addFilterProperty', column);
	filterStatus.value = false;
};

const clearColumnValue = (column: ColumnProperty) => {
	if (typeof column.value === 'string') {
		column.value = '';
	} else if (typeof column.value === 'number') {
		column.value = 0;
	} else if (column.value instanceof Date) {
		column.value = new Date('1995-12-17T03:24:00');
	} else {
		column.value = '';
	}
};

const removeFilterProperty = (column: ColumnProperty) => {
	clearColumnValue(column);
	flux.filter.removeProperty(column.key);
	flux.availableColumns.push(column);
	applyQueryFilters();
	emit('removeFilterProperty', column);
};
</script>

<template>
	<div class="flex flex-col items-end justify-end">
		<div class="mb-2.5 flex w-full items-end justify-end gap-2.5">
			<slot></slot>
			<XPopover v-if="availableColumns.length > 0" v-model="filterStatus">
				<XButton
					size="small"
					variant="text"
					prepend="i-material-symbols:filter-alt"
					append="i-material-symbols:add"
					label="Add filter"
					@click="filterStatus = !filterStatus"
				/>
				<template #content>
					<XListbox>
						<XListbox.Item
							v-for="column in availableColumns"
							:key="column.key"
							@click="addFilterProperty(column)"
						>
							<div class="flex items-center gap-1">
								<div :class="`${column.icon} h-5 w-5`"></div>
								<div>{{ column.name }}</div>
							</div>
						</XListbox.Item>
					</XListbox>
				</template>
			</XPopover>
		</div>

		<div class="flex items-end justify-end">
			<div class="grid grid-cols-1 gap-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4">
				<FilterRule
					v-for="(columnProperty, index) in flux.filter.properties"
					:key="columnProperty.key"
					v-model="flux.filter.properties[index]"
					@applyFilter="applyQueryFilters"
					@removeFilterProperty="removeFilterProperty"
				/>
			</div>
		</div>
	</div>
</template>

<style scoped lang="scss"></style>
