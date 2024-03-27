<script setup lang="ts">
import { onMounted, onUnmounted, reactive } from 'vue';
import useStore from './store';
import { type Control, XBreadcrumb, XButton, XDivider, XTable, XTextField } from '@lyra/ui';
import GeneralRule from '@/components/filter/GeneralRule.vue';
import FilterRangeDate from '@/components/filter/FilterRangeDate.vue';
import { ColumnProperty } from '@/components/filter/Filter';
import { FilterOperator, textOperators } from '@/components/filter/FilterOperator';

const { state, actions, $reset } = useStore();

const defaultControlCursor: Control = {
	paginationType: 'cursor',
	sort: { field: 'createdAt', direction: 'asc' },
	cursor: { cursor: state.cursor, limit: 10 },
};

const defaultColumnFilters: ColumnProperty[] = [
	{
		key: 'email',
		name: 'Email',
		operator: FilterOperator.EQUAL,
		availableOperators: textOperators,
		icon: 'i-material-symbols:alternate-email-rounded',
		value: '',
		inputType: 'email',
	},
	{
		key: 'firstname',
		name: 'Firstname',
		operator: FilterOperator.EQUAL,
		availableOperators: textOperators,
		icon: 'i-material-symbols:person',
		value: '',
		inputType: 'text',
	},
	{
		key: 'lastname',
		name: 'Lastname',
		operator: FilterOperator.EQUAL,
		availableOperators: textOperators,
		icon: 'i-material-symbols:person',
		value: '',
		inputType: 'text',
	},
	{
		key: 'status',
		name: 'Status',
		operator: FilterOperator.EQUAL,
		availableOperators: textOperators,
		icon: 'i-f7:status',
		value: '',
		inputType: 'select',
		options: [
			{ label: 'ENABLED', value: 'ENABLED' },
			{ label: 'DISABLED', value: 'DISABLED' },
			{ label: 'BLOCKLISTED', value: 'BLOCKLISTED' },
		],
	},
];
const flux = reactive({
	columns: [
		{ key: 'email', name: 'Email', sortable: true },
		{ key: 'name', name: 'Name', sortable: true },
		{ key: 'status', name: 'Status', sortable: true },
		{ key: 'createdAt', name: 'Subscribe On', sortable: true },
	],
	filter: {
		columnFilters: defaultColumnFilters,
		criteria: {
			searchQuery: '',
			filterQuery: '',
		},
		startDate: '',
		endDate: '',
		showFilterOptions: false,
	},

	data: state.subscribers,

	control: defaultControlCursor as Control,
	loading: state.loading,
});

function mergeFilterQuery() {
	let filterQuery = '';
	if (flux.filter.criteria.filterQuery) {
		filterQuery = flux.filter.criteria.filterQuery;
	}
	if (flux.filter.startDate && flux.filter.endDate) {
		const startDate = new Date(flux.filter.startDate);
		const endDate = new Date(flux.filter.endDate);
		if (filterQuery) {
			filterQuery += `&createdAt=lte:${endDate.toISOString()}&createdAt=gte:${startDate.toISOString()} `;
		} else {
			filterQuery = `createdAt=lte:${endDate.toISOString()}&createdAt=gte:${startDate.toISOString()} `;
		}
	}
	return filterQuery;
}

const search = async () => {
	let filterQuery = mergeFilterQuery();
	const criteria = {
		search: flux.filter.criteria.searchQuery,
		filter: filterQuery,
	};
	await actions.getSubscribers(criteria);
	flux.data = state.subscribers;
	flux.loading = state.loading;
	flux.control = {
		...flux.control,
		cursor: { cursor: state.cursor, limit: 10 },
	};
};

async function change(params: Control) {
	flux.control = params;
	await search();
}

async function applyFilters(val: string) {
	flux.filter.criteria.filterQuery = val;
	await search();
}

// eslint-disable-next-line @typescript-eslint/no-unused-vars
async function removeFilterProperty(_: ColumnProperty) {
	await search();
}

async function filter() {
	// clear all filters and show filter options if it is hidden
	if (flux.filter.showFilterOptions) {
		flux.filter.columnFilters = defaultColumnFilters;
		flux.filter.criteria.filterQuery = '';
		flux.filter.startDate = '';
		flux.filter.endDate = '';
	}
	flux.filter.showFilterOptions = !flux.filter.showFilterOptions;
	await search();
}

onMounted(async () => {
	await search();
});
onUnmounted(() => {
	$reset();
});
</script>

<template>
	<XBreadcrumb
		:items="[
			{ text: 'Audience', icon: 'i-ph:users-thin' },
			{ text: 'Subscribers', icon: 'i-ph:users-three-thin' },
		]"
		class="mb-2.5"
	/>
	<div class="my-4 text-3xl font-bold">Subscribers</div>

	<div class="space-y-8 rounded-lg bg-white p-8 dark:bg-slate-800">
		<div class="flex w-full flex-col gap-4">
			<div class="flex justify-between">
				<div class="flex justify-start gap-1">
					<XTextField
						v-model:value="flux.filter.criteria.searchQuery"
						placeholder="Search ..."
						clearable
						@keydown.enter="search"
						@clear="search"
					/>
					<XButton
						data-testid="search-subscribers"
						prepend="i-material-symbols-search-rounded"
						label="Search"
						@click="search"
					/>
				</div>
				<XButton
					data-testid="show-filters"
					icon="i-ion:md-options"
					variant="outlined"
					size="small"
					@click="filter"
				/>
			</div>
			<XDivider />
			<div v-if="flux.filter.showFilterOptions">
				<GeneralRule
					:columns="flux.filter.columnFilters"
					@apply-filters="applyFilters"
					@remove-filter-property="removeFilterProperty"
				>
					<FilterRangeDate
						v-model:startDate="flux.filter.startDate"
						v-model:endDate="flux.filter.endDate"
					/>
				</GeneralRule>
			</div>
		</div>
		<XTable
			v-model:control="flux.control"
			stickyHeader
			:columns="flux.columns"
			:rows="state.subscribers"
			@change="change"
		>
			<template #email="{ row }">
				<a
					class="hover:text-primary-500 dark:hover:text-primary-400"
					:href="'/audience/subscribers/' + row.id"
					>{{ row.email }}</a
				>
			</template>
			<template #status="{ row }">
				<span
					:class="{
						'dark:bg-tertiary-700 border-green-400 bg-green-100 text-green-800 dark:text-green-400':
							row.status === 'ENABLED',
						'dark:bg-tertiary-700 border-red-400 bg-red-100 text-red-800 dark:text-red-400':
							row.status === 'DISABLED',
						'dark:bg-tertiary-700 border-yellow-400 bg-yellow-100 text-yellow-800 dark:text-yellow-400':
							row.status === 'BLOCKLISTED',
					}"
					class="me-2 rounded px-2.5 py-0.5 text-xs font-medium"
				>
					{{ row.status }}
				</span>
			</template>
			<template #createdAt="{ row }">{{ new Date(row.createdAt).toLocaleString() }}</template>
		</XTable>
	</div>
</template>

<style scoped lang="scss"></style>
