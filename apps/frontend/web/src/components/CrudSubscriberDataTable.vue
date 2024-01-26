<script setup lang="ts">
import { computed, onMounted, reactive, ref, type Ref } from 'vue';
import Pagination from '@/components/Pagination.vue';
import { type ColumnInfo, useGenericDataTable, useGenericSelectInput } from '@lyra/ui-vue';
import type { Subscriber, SubscriberFilter } from '@lyra/vm-core';
import SubscriberService from '@/services/subscriber.service.ts';

const baseSubscriberUrl = '/app/audience/subscribers';
const subscribers = ref<Subscriber[]>([]);
const perPage = ref(10);
const page = ref(0);
const totalPages = ref(0);
const total = ref(0);
const columns: Ref<ColumnInfo[]> = ref([
	{
		key: 'email',
		label: 'Email',
		sortable: true,
	},
	{
		key: 'name',
		label: 'Name',
		sortable: true,
	},
	{
		key: 'status',
		label: 'Status',
		sortable: false,
	},
]);

const filters: Ref<SubscriberFilter> = ref({
	email: [''],
	firstname: [''],
	lastname: [''],
	status: [''],
});

function updateFilters(searchValue: string) {
	if (searchValue) {
		if (form.column.key === 'email') {
			filters.value.email = [`eq:${searchValue}`];
		} else if (form.column.key === 'name') {
			const [firstname, lastname] = searchValue.split(' ');
			filters.value.firstname = [`eq:${firstname}`];
			if (lastname) filters.value.lastname = [`eq:${lastname}`];
		} else if (form.column.key === 'status') {
			filters.value.status = [`eq:${searchValue}`];
		}
	}
}

const SubscriberDataTable = useGenericDataTable<Subscriber>();
const ColumnSelectInput = useGenericSelectInput<ColumnInfo>();

async function refreshData(
	pagination: { page: number; perPage: number } = { page: page.value, perPage: perPage.value }
) {
	let offsetPage = await SubscriberService.getInstance().getSubscribers(
		filters.value,
		'',
		pagination.perPage,
		pagination.page
	);
	perPage.value = offsetPage.perPage;
	page.value = offsetPage.page ?? 0;
	totalPages.value = offsetPage.totalPages ?? 0;
	total.value = offsetPage.total ?? 0;
	subscribers.value = offsetPage.data;
}

async function submit() {
	await refreshData();
}

onMounted(async () => {
	await refreshData();
});
const form = reactive({
	column: columns.value[0],
});

const searchInputType = computed(() => {
	if (form.column.key === 'email') {
		return 'email';
	}
	return 'text';
});
</script>

<template>
	<SubscriberDataTable :columns="columns" :items="subscribers">
		<template #top>
			<div slot="top" class="mb-1 w-full">
				<div class="mb-4">
					<slot name="header" />
					<h1 slot="header" class="text-xl font-semibold text-gray-900 sm:text-2xl dark:text-white">
						All subscribers
					</h1>
					<div class="block items-center justify-between sm:flex">
						<div class="mb-4 flex items-center sm:mb-0">
							<form class="flex items-center justify-center sm:pr-3" @submit.prevent="submit">
								<ColumnSelectInput v-model="form.column" :options="columns" class="mr-2">
									<template #option="{ option }">
										{{ option.label }}
									</template>
								</ColumnSelectInput>

								<div class="relative w-48 sm:w-64 xl:w-96">
									<label for="subscribers-search" class="sr-only">Search</label>
									<input
										id="subscribers-search"
										:type="searchInputType"
										name="search"
										class="focus:ring-primary-500 focus:border-primary-500 dark:focus:ring-primary-500 dark:focus:border-primary-500 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2.5 text-gray-900 sm:text-sm dark:border-gray-600 dark:bg-gray-700 dark:text-white dark:placeholder-gray-400"
										placeholder="Search for subscribers"
										@input="updateFilters($event?.target?.value ?? '')"
									/>
								</div>
							</form>
						</div>

						<div class="ml-auto flex items-center space-x-2 sm:space-x-3">
							<button type="button" class="crud-buttons" @click="refreshData()">
								<svg
									class="-ml-1 mr-2 h-5 w-5"
									fill="none"
									stroke="currentColor"
									stroke-width="1.5"
									viewBox="0 0 24 24"
									xmlns="http://www.w3.org/2000/svg"
									aria-hidden="true"
								>
									<path
										stroke-linecap="round"
										stroke-linejoin="round"
										d="M16.023 9.348h4.992v-.001M2.985 19.644v-4.992m0 0h4.992m-4.993 0l3.181 3.183a8.25 8.25 0 0013.803-3.7M4.031 9.865a8.25 8.25 0 0113.803-3.7l3.181 3.182m0-4.991v4.99"
									/>
								</svg>
								Refresh
							</button>
							<a
								href="/app/settings/publication/subscribers/import"
								class="crud-buttons"
								type="button"
							>
								<svg
									class="-ml-1 mr-2 h-5 w-5"
									fill="currentColor"
									viewBox="0 0 20 20"
									xmlns="http://www.w3.org/2000/svg"
								>
									<path
										fill-rule="evenodd"
										d="M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z"
										clip-rule="evenodd"
									/>
								</svg>
								Import subscribers
							</a>
						</div>
					</div>
				</div>
			</div>
		</template>
		<template #email="{ item }">
			<a
				:href="`${baseSubscriberUrl}/${item.id}`"
				class="text-primary-600 dark:text-primary-500 font-medium hover:underline"
			>
				{{ item.email }}
			</a>
		</template>
		<template #status="{ item }">
			<span
				:class="{
					'border-green-400 bg-green-100 text-green-800 dark:bg-gray-700 dark:text-green-400':
						item.status === 'ENABLED',
					'border-red-400 bg-red-100 text-red-800 dark:bg-gray-700 dark:text-red-400':
						item.status === 'DISABLED',
					'border-yellow-400 bg-yellow-100 text-yellow-800 dark:bg-gray-700 dark:text-yellow-400':
						item.status === 'BLOCKLISTED',
				}"
				class="me-2 rounded px-2.5 py-0.5 text-xs font-medium"
			>
				{{ item.status }}
			</span>
		</template>
		<template #footer>
			<Pagination
				:total="total"
				:per-page="perPage"
				:page="page"
				:total-pages="totalPages"
				@update-page="refreshData"
			/>
		</template>
	</SubscriberDataTable>
</template>

<style scoped>
.crud-buttons {
	@apply text-tertiary-50 bg-tertiary-800 dark:bg-tertiary-800 border-tertiary-300 dark:border-tertiary-700 hover:bg-tertiary-700 dark:hover:bg-tertiary-700 hover:border-tertiary-700
  dark:hover:border-tertiary-700 flex items-center space-x-2 rounded px-5 py-2.5 text-center
  font-sans font-semibold uppercase sm:space-x-3;
}
</style>
