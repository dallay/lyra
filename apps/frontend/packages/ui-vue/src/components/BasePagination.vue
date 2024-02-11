<script setup lang="ts">
import { computed, ref } from 'vue';
import { useGenericSelectInput } from './genericSelect';

const props = defineProps({
	total: {
		type: Number,
		required: true,
	},
	perPage: {
		type: Number,
		required: true,
	},
	page: {
		type: Number,
		required: true,
	},
	totalPages: {
		type: Number,
		required: true,
	},
});

const emits = defineEmits(['updatePage']);

const pageNumbers = computed(() => Array.from({ length: props.totalPages }, (_, i) => i + 1));
const currentPage = computed(() => props.page + 1);

const updatePage = (newPage: number, perPage: number) => {
	emits('updatePage', { page: newPage, perPage: perPage });
};

const nextPage = () => {
	if (props.page < props.totalPages - 1) {
		updatePage(props.page + 1, perPageValue.value);
	}
};

const previousPage = () => {
	if (props.page > 0) {
		updatePage(props.page - 1, perPageValue.value);
	}
};

const goToPage = (pageNumber: number) => {
	updatePage(pageNumber - 1, perPageValue.value);
};

const PerPageSelectInput = useGenericSelectInput<number>();

const perPageOptions = computed(() => [10, 25, 50, 100]);
const perPageValue = ref(props.perPage);
</script>

<template>
	<nav
		class="flex-column flex flex-wrap items-center justify-between pt-4 md:flex-row"
		aria-label="Table navigation"
	>
		<span
			class="mb-4 block w-full text-sm font-normal text-tertiary-500 md:mb-0 md:inline md:w-auto dark:text-tertiary-400"
			>Showing
			<span class="font-semibold text-tertiary-900 dark:text-white"
				>{{ currentPage }}-{{ perPage }}</span
			>
			of <span class="font-semibold text-tertiary-900 dark:text-white">{{ total }}</span>
		</span>
		<ul class="inline-flex h-8 -space-x-px text-sm rtl:space-x-reverse">
			<li class="mx-2">
				<PerPageSelectInput
					v-model="perPageValue"
					:options="perPageOptions"
					size="small"
					@update:model-value="updatePage(0, $event)"
				/>
			</li>
			<li>
				<button
					class="ms-0 flex h-8 items-center justify-center rounded-s-lg border border-tertiary-300 bg-white px-3 leading-tight text-tertiary-500 hover:bg-tertiary-100 hover:text-tertiary-700 dark:border-tertiary-700 dark:bg-tertiary-800 dark:text-tertiary-400 dark:hover:bg-tertiary-700 dark:hover:text-white"
					@click.prevent="previousPage"
				>
					Previous
				</button>
			</li>
			<li v-for="number in pageNumbers" :key="number">
				<button
					:class="
						number === currentPage
							? 'bg-tertiary-100 text-tertiary-700 dark:bg-tertiary-700 dark:text-white border-tertiary-200 dark:border-tertiary-800'
							: 'border-tertiary-300 bg-white text-tertiary-500 dark:bg-tertiary-800 dark:text-tertiary-400'
					"
					class="flex h-8 items-center justify-center border px-3 leading-tight hover:bg-tertiary-100 hover:text-tertiary-700 dark:border-tertiary-700 dark:hover:bg-tertiary-700 dark:hover:text-white"
					@click.prevent="goToPage(number)"
				>
					{{ number }}
				</button>
			</li>
			<li>
				<button
					class="flex h-8 items-center justify-center rounded-e-lg border border-tertiary-300 bg-white px-3 leading-tight text-tertiary-500 hover:bg-tertiary-100 hover:text-tertiary-700 dark:border-tertiary-700 dark:bg-tertiary-800 dark:text-tertiary-400 dark:hover:bg-tertiary-700 dark:hover:text-white"
					@click.prevent="nextPage"
				>
					Next
				</button>
			</li>
		</ul>
	</nav>
</template>

<style scoped></style>
