<script setup lang="ts">
import { ref } from 'vue';
import { useInfiniteScroll } from '@vueuse/core';

import leetcode from './leetcode';

const target = ref<HTMLElement>();
const page = ref(1);
const data = ref([...leetcode.page1]);
const canLoadMore = ref(true);

useInfiniteScroll(
	target,
	async () => {
		page.value += 1;
		const items = await loadMore(page.value);

		if (items.length) {
			data.value.push(...items);
		} else {
			canLoadMore.value = false;
		}
	},
	{
		distance: 10,
		canLoadMore: () => canLoadMore.value,
	}
);

const isLoading = ref(false);

function delay(ms = 1000) {
	return new Promise((resolve) => setTimeout(resolve, ms));
}

async function loadMore(currentPage = 1, rowsPerPage = 20) {
	isLoading.value = true;
	await delay();
	isLoading.value = false;

	if (currentPage === 1 && rowsPerPage === 20) return leetcode.page1;
	if (currentPage === 2 && rowsPerPage === 20) return leetcode.page2;
	if (currentPage === 3 && rowsPerPage === 20) return leetcode.page3;
	if (currentPage === 4 && rowsPerPage === 20) return leetcode.page4;
	if (currentPage === 5 && rowsPerPage === 20) return leetcode.page5;
	return [];
}
</script>

<template>
	<div
		ref="target"
		class="h-80 w-full overflow-auto whitespace-nowrap rounded-lg border bg-white p-2 dark:border-slate-600 dark:bg-slate-800"
	>
		<div class="!w-min min-w-full">
			<div
				v-for="item in data"
				:key="item.title"
				class="hover:text-primary-500 dark:hover:text-primary-100 hover:bg-primary-100 dark:hover:bg-primary-600 flex items-center px-4 py-2 hover:rounded"
			>
				{{ item.title }}
			</div>

			<div v-if="isLoading" class="flex items-center px-4 py-2 font-bold" style="height: 40px">
				Loading...
			</div>
		</div>
	</div>
</template>
