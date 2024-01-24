<script setup lang="ts">
import { computed, defineEmits, defineProps } from 'vue';

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

const updatePage = (newPage: number) => {
	emits('updatePage', newPage);
};

const nextPage = () => {
	if (props.page < props.totalPages - 1) {
		updatePage(props.page + 1);
	}
};

const previousPage = () => {
	if (props.page > 0) {
		updatePage(props.page - 1);
	}
};

const goToPage = (pageNumber: number) => {
	updatePage(pageNumber - 1);
};
</script>

<template>
	<nav
		class="flex-column flex flex-wrap items-center justify-between pt-4 md:flex-row"
		aria-label="Table navigation"
	>
		<span
			class="mb-4 block w-full text-sm font-normal text-gray-500 md:mb-0 md:inline md:w-auto dark:text-gray-400"
			>Showing
			<span class="font-semibold text-gray-900 dark:text-white"
				>{{ currentPage }}-{{ perPage }}</span
			>
			of <span class="font-semibold text-gray-900 dark:text-white">{{ total }}</span>
		</span>
		<ul class="inline-flex h-8 -space-x-px text-sm rtl:space-x-reverse">
			<li>
				<button
					@click.prevent="previousPage"
					class="ms-0 flex h-8 items-center justify-center rounded-s-lg border border-gray-300 bg-white px-3 leading-tight text-gray-500 hover:bg-gray-100 hover:text-gray-700 dark:border-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
				>
					Previous
				</button>
			</li>
			<li v-for="number in pageNumbers" :key="number">
				<button
					@click.prevent="goToPage(number)"
					:class="
						number === currentPage
							? 'bg-primary-300 border-primary-300 dark:bg-primary-700 dark:border-primary-700 text-white'
							: ''
					"
					class="flex h-8 items-center justify-center border border-gray-300 bg-white px-3 leading-tight text-gray-500 hover:bg-gray-100 hover:text-gray-700 dark:border-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
				>
					{{ number }}
				</button>
			</li>
			<li>
				<button
					@click.prevent="nextPage"
					class="flex h-8 items-center justify-center rounded-e-lg border border-gray-300 bg-white px-3 leading-tight text-gray-500 hover:bg-gray-100 hover:text-gray-700 dark:border-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
				>
					Next
				</button>
			</li>
		</ul>
	</nav>
</template>

<style scoped></style>
