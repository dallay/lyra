<script lang="ts" setup>
import type { ComponentProps } from 'vue-component-type-helpers';
import { reactive } from 'vue';
import { XCard, XButton, XTable } from '@lyra/ui';

import leetcode from './leetcode';

type TableProps = ComponentProps<typeof XTable>;

const state = reactive({
	loading: false,
	rows: [] as any[],
	control: {
		paginationType: 'offset',
		sort: { field: 'id', direction: 'asc' },
		offset: { rows: 10, page: 1 },
	} as TableProps['control'],
	count: 0,
});

const body = reactive({
	title: '',
	difficulty: '',
});

async function search() {
	state.loading = true;
	state.control = {
		paginationType: 'offset',
		sort: { field: 'id', direction: 'asc' },
		offset: { rows: 10, page: 1 },
	} as TableProps['control'];
	await new Promise((resolve) => setTimeout(resolve, 3000));
	const response = await leetcode({ ...body, control: state.control });
	state.loading = false;
	state.rows = response.result;
	state.count = response.count;
}
async function change(params: TableProps['control']) {
	state.control = params;
	const response = await leetcode({ ...body, control: params });
	state.rows = response.result;
}
</script>

<template>
	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Loading</h2>

		<XCard>
			<XButton
				prepend="i-material-symbols-search-rounded"
				label="Search"
				:loading="state.loading"
				class="mb-4"
				@click="search"
			/>
			<XTable
				v-model:control="state.control"
				:columns="[
					{ key: 'title', name: 'Title' },
					{ key: 'difficulty', name: 'Difficulty' },
				]"
				:loading="state.loading"
				:rows="state.rows"
				:count="state.count"
				@change="change"
			/>
		</XCard>
	</section>
</template>
