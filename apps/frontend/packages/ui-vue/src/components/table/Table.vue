<script lang="ts" setup generic="T extends Record<string, unknown>">
import type { VNode } from 'vue';
import { computed, reactive, watch, toRef } from 'vue';
import type staticTable from './static-table/staticTable';
import Spinner from '../spinner/Spinner.vue';
import Button from '../button/Button.vue';
import Select from '../select/Select.vue';
import Checkbox from '../checkbox/Checkbox.vue';

import type { ColumnItem, Control } from './types';
import Column from './Column.vue';
import Row from './Row.vue';
import Cell from './Cell.vue';

const props = defineProps<{
	value?: T[];

	columns?: ColumnItem[];
	rows?: T[];

	static?: typeof staticTable;

	count?: number;
	control?: Control;

	selectable?: boolean;
	selected?: T[];

	stickyHeader?: boolean;
	loading?: boolean;
}>();

const emit = defineEmits<{
	(evt: 'update:value', val: T[]): void;
	(evt: 'update:selected', val: T[]): void;
	(evt: 'change', val: { rows?: number; page?: number; field?: string; direction?: string }): void;
	(evt: 'clickRow', val: T): void;
	(
		evt: 'update:control',
		val: { rows?: number; page?: number; field?: string; direction?: string }
	): void;
	(evt: 'selecteAll', val: boolean, arr: T[]): void;
}>();

defineSlots<{
	thead(props: {}): VNode;
	tbody(props: {}): VNode;
	[colKey: string]: (props: { row: T }) => VNode;
	collapsible(props: { row: T }): VNode;
	spanable(props: {}): VNode;
}>();

const valueModel = computed({
	get: () => props.value || [],
	set: (val) => emit('update:value', val),
});

const countRef = toRef(props, 'count', undefined);

const controlModel = computed({
	get: () => {
		if (
			!props.control ||
			(typeof props.control === 'object' && Object.keys(props.control).length === 0)
		) {
			return { rows: 10, page: 1, field: 'createdAt', direction: 'desc' };
		}

		return props.control;
	},
	set: (val) => emit('update:control', val),
});

const flux = reactive({
	rows: [] as any[],

	indeterminate: false,
	selectedAll: false,

	rowsPerPage: 10,
	rowsPerPageOptions: [
		{ label: '10', value: 10 },
		{ label: '20', value: 20 },
		{ label: '30', value: 30 },
		{ label: '50', value: 50 },
	],
	currentPage: 1,
	previousPage() {
		if (flux.currentPage === 1) return;
		flux.currentPage -= 1;
		flux._updateChange();
	},
	nextPage() {
		if (flux.currentPage === Math.ceil(countRef.value / flux.rowsPerPage)) return;
		flux.currentPage += 1;
		flux._updateChange();
	},

	sortField: 'createdAt' as string | undefined,
	sortDirection: 'desc' as string | undefined,
	onSort(col: any) {
		if (props.loading) return;

		if (flux.sortDirection === 'asc') {
			flux.sortField = col.key;
			flux.sortDirection = 'desc';
		} else if (flux.sortDirection === 'desc') {
			flux.sortField = col.key;
			flux.sortDirection = 'asc';
		}

		flux._updateChange();
	},

	_updateChange() {
		emit('change', {
			rows: flux.rowsPerPage,
			page: flux.currentPage,
			field: flux.sortField,
			direction: flux.sortDirection,
		});

		controlModel.value = {
			rows: flux.rowsPerPage,
			page: flux.currentPage,
			field: flux.sortField,
			direction: flux.sortDirection,
		};
	},

	clickRow(row: T) {
		emit('clickRow', row);
	},
});

const paginationInfo = computed(() => {
	return `${flux.currentPage * flux.rowsPerPage - flux.rowsPerPage + 1}-${
		flux.currentPage * flux.rowsPerPage > countRef.value
			? countRef.value
			: flux.currentPage * flux.rowsPerPage
	} of ${countRef.value}`;
});

watch(
	() => controlModel.value,
	(val) => {
		if (Object.keys(val)?.length) {
			flux.rowsPerPage = val.rows || 10;
			flux.currentPage = val.page || 1;
			flux.sortField = val.field || 'createdAt';
			flux.sortDirection = val.direction || 'desc';

			if (props.static && props.rows?.length) {
				flux.rows = props.static(props.rows, controlModel.value);
			}
		}
	},
	{ immediate: true }
);

watch(
	() => flux.rowsPerPage,
	() => {
		flux.currentPage = 1;
		flux._updateChange();
	}
);

watch(
	() => flux.selectedAll,
	(val) => {
		if (props.static) {
			const arr = props.rows?.map((item) => ({ ...item, checked: val })) || [];
			flux.rows = props.static(arr, controlModel.value);

			arr.forEach((row: any) => {
				const found: any = valueModel.value.find((item: any) =>
					item._id ? item._id === row._id : item.id === row.id
				);
				if (found) found.checked = val;
			});
		} else {
			flux.rows = props.rows?.map((item) => ({ ...item, checked: val })) || [];
		}
	}
);

watch(
	() => props.rows,
	(val) => {
		if (props.static && props.rows?.length) {
			flux.rows = props.static(props.rows, controlModel.value);

			const checked = props.rows.every((item: any) => item.checked);
			const unchecked = props.rows.every((item: any) => !item.checked);
			flux.indeterminate = !(checked || unchecked);
			if (checked) flux.selectedAll = true;
			if (unchecked) flux.selectedAll = false;
		} else {
			flux.rows = val || [];
		}
	},
	{ deep: true, immediate: true }
);

watch(
	() => flux.rows,
	(val) => {
		if (props.static && val?.length) {
			val.forEach((row) => {
				const found: any = valueModel.value.find((item: any) =>
					item._id ? item._id === row._id : item.id === row.id
				);
				if (found) found.checked = row.checked;
			});
		}

		if (!props.static) {
			const checked = val.every((item) => item.checked);
			const unchecked = val.every((item) => !item.checked);

			flux.indeterminate = !(checked || unchecked);

			if (checked) flux.selectedAll = true;
			if (unchecked) flux.selectedAll = false;

			emit(
				'update:selected',
				val.filter((item) => item.checked)
			);
		}
	},
	{ deep: true }
);
</script>

<template>
	<div class="relative">
		<div class="Table-Wrapper" :class="{ 'max-h-100': stickyHeader }">
			<table class="Table-Element">
				<thead class="bg-tertiary-100 dark:bg-tertiary-900">
					<slot name="thead"></slot>

					<tr :class="{ 'sticky top-0 z-10': stickyHeader }">
						<Column v-if="selectable" :class="{ 'border-0': stickyHeader }">
							<Checkbox v-model:value="flux.selectedAll" :indeterminate="flux.indeterminate" />
						</Column>

						<Column
							v-for="col in columns"
							:key="col.key"
							:class="[
								{
									'border-0': stickyHeader,
									'z-5 sticky left-0': col.sticky === 'left',
									'z-5 sticky right-0': col.sticky === 'right',
								},
								col.class,
								col.classColumn,
							]"
						>
							<div
								class="gap-1"
								:class="{
									'cursor-pointer': typeof col.sortable === 'boolean' ? col.sortable : true,
								}"
								@click="flux.onSort(col)"
							>
								<div>{{ col.name }}</div>

								<template v-if="typeof col.sortable === 'boolean' ? col.sortable : true">
									<div
										v-if="flux.sortField === col.key && flux.sortDirection === 'desc'"
										class="i-fa-sort-desc h-3.5 w-3.5"
									></div>

									<div
										v-else-if="flux.sortField === col.key && flux.sortDirection === 'asc'"
										class="i-fa-sort-asc h-3.5 w-3.5"
									></div>

									<div v-else class="i-fa-sort h-3.5 w-3.5"></div>
								</template>
							</div>
						</Column>
					</tr>
				</thead>

				<tbody v-if="loading">
					<tr>
						<td :colspan="selectable ? Number(columns?.length) + 1 : columns?.length">
							<div class="min-h-387px flex w-full flex-col items-center justify-center">
								<Spinner class="h-10 w-10" />
							</div>
						</td>
					</tr>
				</tbody>

				<slot v-else name="tbody">
					<tbody v-if="flux.rows?.length">
						<template v-for="row in flux.rows" :key="row._id || row.id">
							<Row
								class="sticky-tr"
								:class="{ 'bg-primary-300/25 !hover:bg-primary-400/25': selectable && row.checked }"
								@click="flux.clickRow(row)"
							>
								<Cell v-if="selectable">
									<Checkbox v-model:value="row.checked" />
								</Cell>

								<Cell
									v-for="col in columns"
									:key="col.key"
									:class="[
										{
											'sticky-col z-5 sticky bg-white !p-0 dark:bg-slate-800': col.sticky,
											'left-0': col.sticky === 'left',
											'right-0': col.sticky === 'right',
										},
										col.class,
										col.classCell,
									]"
								>
									<div
										v-if="col.spanable"
										class="flex-col justify-center gap-1"
										:class="{ 'py-2': row.details?.length > 1 }"
									>
										<div v-for="(sub, subIdx) in row.details" :key="subIdx">
											<slot :name="col.key" :row="row">
												{{ sub[col.key] }}
											</slot>
										</div>
									</div>

									<div
										v-else
										:class="{
											'w-full border-slate-400/50 px-4': col.sticky,
											'border-r-2': col.sticky === 'left',
											'border-l-2': col.sticky === 'right',
										}"
									>
										<slot :name="col.key" :row="row">
											{{ row[col.key] }}
										</slot>
									</div>
								</Cell>
							</Row>

							<slot name="collapsible" :row="row"></slot>
						</template>

						<slot name="spanable"></slot>
					</tbody>

					<tbody v-else>
						<Row class="bg-gray-200/20">
							<Cell :colspan="selectable ? Number(columns?.length) + 1 : columns?.length">
								<div class="!min-h-389px flex w-full flex-col items-center justify-center">
									<div class="text-xl font-medium text-stone-300">
										{{ 'No data to display' }}
									</div>
								</div>
							</Cell>
						</Row>
					</tbody>
				</slot>
			</table>
		</div>

		<div
			v-if="typeof countRef === 'number'"
			class="flex flex-col items-center justify-end gap-4 p-4 text-sm md:flex-row"
		>
			<div class="Table-RowsPerPage">
				{{ 'Rows per page:' }}
				<div class="ml-2 w-auto">
					<Select
						id="per-page-select"
						v-model:value="flux.rowsPerPage"
						:options="flux.rowsPerPageOptions"
						:disabled="loading"
						class="!border-transparent"
					/>
				</div>
			</div>

			<div class="flex items-center">{{ paginationInfo }}</div>

			<div class="flex gap-4">
				<Button
					prepend="i-material-symbols-chevron-left-rounded"
					:label="'Previous'"
					variant="text"
					color="secondary"
					:disabled="loading"
					@click="flux.previousPage"
				/>
				<Button
					:label="'Next'"
					append="i-material-symbols-chevron-right-rounded"
					variant="text"
					color="secondary"
					:disabled="loading"
					@click="flux.nextPage"
				/>
			</div>
		</div>
	</div>
</template>

<style lang="scss" scoped>
.Table-Wrapper {
	@apply w-full overflow-auto;
}

.Table-Element {
	@apply w-full border-collapse;
}

.Table-RowsPerPage {
	@apply flex items-center;
}

.sticky-tr:hover {
	.sticky-col {
		@apply bg-primary-300/25;
	}
}
</style>
