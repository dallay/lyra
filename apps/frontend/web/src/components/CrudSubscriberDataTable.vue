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
const isLoaded = ref(false);

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
	isLoaded.value = false;
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
	isLoaded.value = true;
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
			<div class="mb-1 w-full">
				<div class="mb-4">
					<slot name="header" />
					<h1 class="text-xl font-semibold text-gray-900 sm:text-2xl dark:text-white">
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
		<template #loader>
			<div
				v-if="!isLoaded"
				role="status"
				class="absolute left-1/2 top-2/4 -translate-x-1/2 -translate-y-1/2"
			>
				<svg
					aria-hidden="true"
					class="fill-primary-600 h-12 w-12 animate-spin text-gray-200 dark:text-gray-600"
					viewBox="0 0 100 101"
					fill="none"
					xmlns="http://www.w3.org/2000/svg"
				>
					<path
						d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
						fill="currentColor"
					/>
					<path
						d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
						fill="currentFill"
					/>
				</svg>
				<span class="sr-only">Loading...</span>
			</div>
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
