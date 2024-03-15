<script lang="ts" setup>
import type { ComponentProps } from 'vue-component-type-helpers';
import { onMounted, reactive } from 'vue';
import { XButton, XSelect, XTable, XTextField } from '@lyra/ui';

import leetcode from './leetcode';

type TableProps = ComponentProps<typeof XTable>;

const defaultControlOffset = {
	paginationType: 'offset',
	sort: { field: 'id', direction: 'asc' },
	offset: { rows: 10, page: 1 },
};
const state = reactive({
	rows: [] as any[],
	control: defaultControlOffset as TableProps['control'],
	count: 0,
});

const body = reactive({
	title: '',
	difficulty: '',
});

onMounted(() => {
	search();
});

function reset() {
	body.title = '';
	body.difficulty = '';
	search();
}

async function search() {
	state.control = defaultControlOffset as TableProps['control'];
	const response = await leetcode({ ...body, control: state.control });
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
	<div>
		<div class="my-4 text-3xl font-bold">Basic</div>

		<div class="space-y-8 rounded-lg bg-white p-8 dark:bg-slate-800">
			<div class="grid grid-cols-1 gap-4 md:grid-cols-3">
				<XTextField v-model:value="body.title" />

				<XSelect
					v-model:value="body.difficulty"
					:options="[
						{ label: 'Easy', value: 'Easy' },
						{ label: 'Medium', value: 'Medium' },
						{ label: 'Hard', value: 'Hard' },
					]"
				/>

				<div class="flex gap-4">
					<XButton color="secondary" class="flex-1" @click="reset">Reset</XButton>
					<XButton class="flex-1" @click="search">Search</XButton>
				</div>
			</div>

			<XTable
				v-model:control="state.control"
				:columns="[
					{ key: 'title', name: 'Title' },
					{ key: 'difficulty', name: 'Difficulty' },
				]"
				:rows="state.rows"
				:count="state.count"
				@change="change"
			/>
		</div>
	</div>
</template>
