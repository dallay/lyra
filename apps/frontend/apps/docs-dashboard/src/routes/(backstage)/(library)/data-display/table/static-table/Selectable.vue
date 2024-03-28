<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { XTextField, XSelect, XButton, XTable, Control } from '@lyra/ui';
import { staticTable } from '@lyra/ui';

import leetcode from './leetcode';

const title = ref('');
const difficulty = ref('');

const response = ref<typeof leetcode>([]);
const rows = ref<typeof leetcode>([]);
const defaultOffsetControl: Control = {
	paginationType: 'offset',
	sort: { field: 'id', direction: 'asc' },
	offset: { rows: 10, page: 1 },
};
const control = ref(defaultOffsetControl);

onMounted(() => {
	response.value = structuredClone(leetcode);
	rows.value = [...response.value];
});

function reset() {
	title.value = '';
	difficulty.value = '';
	search();
}

function search() {
	let data = [...response.value];

	if (title.value) {
		data = data.filter((item) => item.title.toUpperCase().includes(title.value.toUpperCase()));
	}

	if (difficulty.value) {
		data = data.filter((item) => item.difficulty.includes(difficulty.value));
	}

	rows.value = data;
	control.value = defaultOffsetControl;
}
</script>

<template>
	<div>
		<div class="my-4 text-3xl font-bold">Selectable</div>

		<div class="space-y-8 rounded-lg bg-white p-8 dark:bg-slate-800">
			<div class="grid grid-cols-1 gap-4 md:grid-cols-3">
				<XTextField v-model:value="title" />

				<XSelect
					v-model:value="difficulty"
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
				v-model:value="response"
				v-model:control="control"
				selectable
				:static="staticTable"
				:columns="[
					{ key: 'title', name: 'Title' },
					{ key: 'difficulty', name: 'Difficulty' },
				]"
				:rows="rows"
				:count="rows?.length"
			/>

			<div>{{ response.filter((item: any) => item.checked)?.map((item) => item.id) }}</div>
		</div>
	</div>
</template>
