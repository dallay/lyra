<script setup lang="ts">
import { computed, ref } from 'vue';
import BaseSelectInput from '@/components/BaseSelectInput.vue';
export interface OffsetPaginationProps {
	total: number;
	perPage: number;
	page: number;
	totalPages: number;
	perPageOptions: number[];
}
const props = withDefaults(defineProps<OffsetPaginationProps>(), {
	perPage: 10,
	perPageOptions: () => [10, 20, 50, 100],
});

const emits = defineEmits<{
	(evt: 'updatePage', val: { page: number; perPage: number }): void;
}>();

const pageNumbers = computed(() => Array.from({ length: props.totalPages }, (_, i) => i + 1));
const currentPage = computed(() => props.page + 1);

const updatePage = (newPage: number, perPage: number) => {
	emits('updatePage', { page: newPage, perPage: perPage });
};
const hasNextPage = computed(() => props.page < props.totalPages - 1);
const nextPage = () => {
	if (hasNextPage.value) {
		updatePage(props.page + 1, perPageValue.value);
	}
};
const hasPreviousPage = computed(() => props.page > 0);
const previousPage = () => {
	if (hasPreviousPage.value) {
		updatePage(props.page - 1, perPageValue.value);
	}
};

const goToPage = (pageNumber: number) => {
	updatePage(pageNumber - 1, perPageValue.value);
};

const perPageValue = ref(props.perPage);
</script>

<template>
	<nav
		class="flex-column flex flex-wrap items-center justify-between pt-4 md:flex-row"
		aria-label="Table navigation"
	>
		<span
			class="text-tertiary-500 dark:text-tertiary-400 mb-4 block w-full text-sm font-normal md:mb-0 md:inline md:w-auto"
			>Showing
			<span class="text-tertiary-900 font-semibold dark:text-white"
				>{{ currentPage }}-{{ perPage }}</span
			>
			of <span class="text-tertiary-900 font-semibold dark:text-white">{{ total }}</span>
		</span>
		<ul class="inline-flex h-8 -space-x-px text-sm rtl:space-x-reverse">
			<li class="mx-2">
				<BaseSelectInput
					v-model="perPageValue"
					:options="perPageOptions"
					size="small"
					@update:model-value="updatePage(0, $event)"
				/>
			</li>
			<li>
				<button
					type="button"
					:disabled="!hasPreviousPage"
					class="border-tertiary-300 text-tertiary-500 dark:border-tertiary-700 dark:bg-tertiary-800 dark:text-tertiary-400 ms-0 flex h-8 items-center justify-center rounded-s-lg border bg-white px-3 leading-tight"
					:class="{
						'cursor-not-allowed': !hasPreviousPage,
						'hover:bg-tertiary-100 hover:text-tertiary-700 dark:hover:bg-tertiary-700 dark:hover:text-white':
							hasPreviousPage,
					}"
					@click.prevent="previousPage"
				>
					Previous
				</button>
			</li>
			<li v-for="number in pageNumbers" :key="number">
				<button
					type="button"
					:class="
						number === currentPage
							? 'bg-tertiary-100 text-tertiary-700 dark:bg-tertiary-700 border-tertiary-200 dark:border-tertiary-800 dark:text-white'
							: 'border-tertiary-300 text-tertiary-500 dark:bg-tertiary-800 dark:text-tertiary-400 bg-white'
					"
					class="hover:bg-tertiary-100 hover:text-tertiary-700 dark:border-tertiary-700 dark:hover:bg-tertiary-700 flex h-8 items-center justify-center border px-3 leading-tight dark:hover:text-white"
					@click.prevent="goToPage(number)"
				>
					{{ number }}
				</button>
			</li>
			<li>
				<button
					type="button"
					:disabled="!hasNextPage"
					class="border-tertiary-300 text-tertiary-500 dark:border-tertiary-700 dark:bg-tertiary-800 dark:text-tertiary-400 flex h-8 items-center justify-center rounded-e-lg border bg-white px-3 leading-tight"
					:class="{
						'cursor-not-allowed': !hasNextPage,
						'hover:bg-tertiary-100 hover:text-tertiary-700 dark:hover:bg-tertiary-700 dark:hover:text-white':
							hasNextPage,
					}"
					@click.prevent="nextPage"
				>
					Next
				</button>
			</li>
		</ul>
	</nav>
</template>

<style scoped></style>
