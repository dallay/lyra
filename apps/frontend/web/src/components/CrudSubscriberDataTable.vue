<script setup lang="ts">
import { onMounted, reactive, ref, type Ref } from 'vue';
import {
	Table,
	BaseDataTable,
	BaseInput,
	type ColumnInfo,
	CursorPagination,
	FilterOperator,
	textOperators,
	GeneralFilter,
	type ColumnItem,
	type Property,
} from '@lyra/ui-vue';
import type { Sort, Subscriber } from '@lyra/vm-core';
import SubscriberService from '@/services/subscriber.service.ts';

const baseSubscriberUrl = '/app/audience/subscribers';
const subscribers = ref<Subscriber[]>([]);
const perPage = ref(10);
const cursor = ref('');
//
// const columns: Ref<ColumnItem[]> = ref([
// 	{
// 		key: 'email',
// 		label: 'Email',
// 		sortable: true,
// 	},
// 	{
// 		key: 'name',
// 		label: 'Name',
// 		sortable: true,
// 	},
// 	{
// 		key: 'status',
// 		label: 'Status',
// 		sortable: false,
// 	},
// ]);
const allProperties: Property<string | number>[] = [
	{
		id: 'email',
		name: 'email',
		label: 'Email',
		type: 'email',
		operator: FilterOperator.EQUAL,
		value: '',
		availableOperators: textOperators,
	},
	{
		id: 'firstname',
		name: 'firstname',
		label: 'First Name',
		type: 'text',
		operator: FilterOperator.EQUAL,
		value: '',
		availableOperators: textOperators,
	},
	{
		id: 'lastname',
		name: 'lastname',
		label: 'Last Name',
		type: 'text',
		operator: FilterOperator.EQUAL,
		value: '',
		availableOperators: textOperators,
	},
	{
		id: 'status',
		name: 'status',
		label: 'Status',
		type: 'select',
		operator: FilterOperator.EQUAL,
		value: 'ENABLED',
		availableOperators: textOperators,
		options: ['ENABLED', 'DISABLED', 'BLOCKLISTED'],
	},
];
const isLoaded = ref(false);

// const SubscriberDataTable = useGenericDataTable<Subscriber>();

const filterQuery = ref('');
const searchQuery = ref('');

const loadMore = async (
	pagination: { cursor: string; perPage: number } = {
		cursor: cursor.value,
		perPage: perPage.value,
	}
) => {
	await refreshData(pagination, undefined, true);
};

async function refreshData(
	pagination: { cursor: string; perPage: number } = {
		cursor: cursor.value,
		perPage: perPage.value,
	},
	sort?: Sort,
	loadMore: boolean = false
) {
	isLoaded.value = false;
	const criteria = { search: searchQuery.value, filter: filterQuery.value };
	const pageResponse = await SubscriberService.getInstance().getSubscribers(
		criteria,
		sort,
		pagination.perPage,
		pagination.cursor
	);
	perPage.value = pagination.perPage;
	cursor.value = pageResponse.nextPageCursor || '';
	if (loadMore) {
		const newSubscribers = pageResponse.data.filter(
			(newSubscriber) =>
				!subscribers.value.some((existingSubscriber) => existingSubscriber.id === newSubscriber.id)
		);

		subscribers.value.push(...newSubscribers);
	} else {
		subscribers.value = pageResponse.data;
	}
	isLoaded.value = true;
}

const applyFilters = (query: string) => {
	filterQuery.value = query;
	refreshData({ cursor: '', perPage: perPage.value });
};

onMounted(async () => {
	await refreshData();
});

const clearFilters = () => {
	filterQuery.value = '';
	refreshData({ cursor: '', perPage: perPage.value });
};
const clearSearch = () => {
	searchQuery.value = '';
	refreshData({ cursor: '', perPage: perPage.value });
};

const state = reactive({
	columns: [
		{
			key: 'email',
			name: 'Email',
			sortable: true,
		},
		{
			key: 'name',
			name: 'Name',
			sortable: true,
		},
		{
			key: 'status',
			name: 'Status',
			sortable: false,
		},
	],
	rows: subscribers,
	control: { rows: 10, page: 1, field: 'id', direction: 'asc' },
	count: 0,
	loading: false,
});
</script>

<template>
	<Table
		stickyHeader
		v-model:control="state.control"
		:columns="state.columns"
		:rows="state.rows"
		:count="state.count"
		:loading="state.loading"
	/>
	<!--	<BaseDataTable :columns="columns" :items="subscribers">-->
	<!--		<template #top>-->
	<!--			<div class="mb-1 w-full">-->
	<!--				<div class="mb-4">-->
	<!--					<slot name="header" />-->
	<!--					<h1 class="text-xl font-semibold text-tertiary-900 sm:text-2xl dark:text-white">-->
	<!--						All subscribers-->
	<!--					</h1>-->
	<!--					<div class="block items-center justify-between sm:flex">-->
	<!--						<BaseInput-->
	<!--							id="search-subscribers-input"-->
	<!--							v-model="searchQuery"-->
	<!--							type="text"-->
	<!--							placeholder="Search subscribers"-->
	<!--							class="w-1/3"-->
	<!--							@clear-input="clearSearch"-->
	<!--						/>-->
	<!--						<button-->
	<!--							type="button"-->
	<!--							class="crud-buttons"-->
	<!--							@click="refreshData({ cursor: '', perPage: perPage })"-->
	<!--						>-->
	<!--							Search-->
	<!--						</button>-->
	<!--						<div class="ml-auto flex items-center space-x-2 sm:space-x-3">-->
	<!--							<GeneralFilter-->
	<!--								class="mx-2"-->
	<!--								:properties="allProperties"-->
	<!--								@apply-filters="applyFilters"-->
	<!--								@clear-filters="clearFilters"-->
	<!--							/>-->
	<!--							&lt;!&ndash;              <button type="button" class="crud-buttons" @click="refreshData()">&ndash;&gt;-->
	<!--							&lt;!&ndash;                <svg&ndash;&gt;-->
	<!--							&lt;!&ndash;                  class="-ml-1 mr-2 h-5 w-5"&ndash;&gt;-->
	<!--							&lt;!&ndash;                  fill="none"&ndash;&gt;-->
	<!--							&lt;!&ndash;                  stroke="currentColor"&ndash;&gt;-->
	<!--							&lt;!&ndash;                  stroke-width="1.5"&ndash;&gt;-->
	<!--							&lt;!&ndash;                  viewBox="0 0 24 24"&ndash;&gt;-->
	<!--							&lt;!&ndash;                  xmlns="http://www.w3.org/2000/svg"&ndash;&gt;-->
	<!--							&lt;!&ndash;                  aria-hidden="true"&ndash;&gt;-->
	<!--							&lt;!&ndash;                >&ndash;&gt;-->
	<!--							&lt;!&ndash;                  <path&ndash;&gt;-->
	<!--							&lt;!&ndash;                    stroke-linecap="round"&ndash;&gt;-->
	<!--							&lt;!&ndash;                    stroke-linejoin="round"&ndash;&gt;-->
	<!--							&lt;!&ndash;                    d="M16.023 9.348h4.992v-.001M2.985 19.644v-4.992m0 0h4.992m-4.993 0l3.181 3.183a8.25 8.25 0 0013.803-3.7M4.031 9.865a8.25 8.25 0 0113.803-3.7l3.181 3.182m0-4.991v4.99"&ndash;&gt;-->
	<!--							&lt;!&ndash;                  />&ndash;&gt;-->
	<!--							&lt;!&ndash;                </svg>&ndash;&gt;-->
	<!--							&lt;!&ndash;                Refresh&ndash;&gt;-->
	<!--							&lt;!&ndash;              </button>&ndash;&gt;-->
	<!--							<a-->
	<!--								href="/app/settings/publication/subscribers/import"-->
	<!--								class="crud-buttons"-->
	<!--								type="button"-->
	<!--							>-->
	<!--								<svg-->
	<!--									class="-ml-1 mr-2 h-5 w-5"-->
	<!--									fill="currentColor"-->
	<!--									viewBox="0 0 20 20"-->
	<!--									xmlns="http://www.w3.org/2000/svg"-->
	<!--								>-->
	<!--									<path-->
	<!--										fill-rule="evenodd"-->
	<!--										d="M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z"-->
	<!--										clip-rule="evenodd"-->
	<!--									/>-->
	<!--								</svg>-->
	<!--								Import subscribers-->
	<!--							</a>-->
	<!--						</div>-->
	<!--					</div>-->
	<!--				</div>-->
	<!--			</div>-->
	<!--		</template>-->
	<!--		<template #cell:email="{ item }">-->
	<!--			<a-->
	<!--				:href="`${baseSubscriberUrl}/${item.id}`"-->
	<!--				class="hover:text-primary-600 dark:hover:text-primary-500 font-medium hover:underline"-->
	<!--			>-->
	<!--				{{ item.email }}-->
	<!--			</a>-->
	<!--		</template>-->
	<!--		<template #status="{ item }">-->
	<!--			<span-->
	<!--				:class="{-->
	<!--					'border-green-400 bg-green-100 text-green-800 dark:bg-tertiary-700 dark:text-green-400':-->
	<!--						item.status === 'ENABLED',-->
	<!--					'border-red-400 bg-red-100 text-red-800 dark:bg-tertiary-700 dark:text-red-400':-->
	<!--						item.status === 'DISABLED',-->
	<!--					'border-yellow-400 bg-yellow-100 text-yellow-800 dark:bg-tertiary-700 dark:text-yellow-400':-->
	<!--						item.status === 'BLOCKLISTED',-->
	<!--				}"-->
	<!--				class="me-2 rounded px-2.5 py-0.5 text-xs font-medium"-->
	<!--			>-->
	<!--				{{ item.status }}-->
	<!--			</span>-->
	<!--		</template>-->
	<!--		<template #loader>-->
	<!--			<div-->
	<!--				v-if="!isLoaded"-->
	<!--				role="status"-->
	<!--				class="absolute left-1/2 top-2/4 -translate-x-1/2 -translate-y-1/2"-->
	<!--			>-->
	<!--				<svg-->
	<!--					aria-hidden="true"-->
	<!--					class="fill-primary-600 h-12 w-12 animate-spin text-tertiary-200 dark:text-tertiary-600"-->
	<!--					viewBox="0 0 100 101"-->
	<!--					fill="none"-->
	<!--					xmlns="http://www.w3.org/2000/svg"-->
	<!--				>-->
	<!--					<path-->
	<!--						d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"-->
	<!--						fill="currentColor"-->
	<!--					/>-->
	<!--					<path-->
	<!--						d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"-->
	<!--						fill="currentFill"-->
	<!--					/>-->
	<!--				</svg>-->
	<!--				<span class="sr-only">Loading...</span>-->
	<!--			</div>-->
	<!--		</template>-->
	<!--		<template #footer>-->
	<!--			<CursorPagination :next-page-cursor="cursor" :per-page="perPage" @update-page="loadMore" />-->
	<!--		</template>-->
	<!--	</BaseDataTable>-->
</template>

<style scoped>
.crud-buttons {
	@apply text-tertiary-50 bg-tertiary-800 dark:bg-tertiary-800 border-tertiary-300 dark:border-tertiary-700 hover:bg-tertiary-700 dark:hover:bg-tertiary-700 hover:border-tertiary-700
  dark:hover:border-tertiary-700 flex items-center space-x-2 rounded px-5 py-2.5 text-center
  font-sans font-semibold uppercase sm:space-x-3;
}
</style>
