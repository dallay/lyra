<script lang="ts" setup>
import { ref } from 'vue';
import { Control, XCard, XTable } from '@lyra/ui';
import { staticTable } from '@lyra/ui';

const rows = ref([]);
const defaultOffsetControl: Control = {
	paginationType: 'offset',
	sort: { field: 'id', direction: 'asc' },
	offset: { rows: 10, page: 1 },
};
const control = ref(defaultOffsetControl);
</script>

<template>
	<section>
		<h2 class="my-4 text-3xl font-bold">No Data</h2>

		<XCard>
			<XTable
				v-model:control="control"
				:static="staticTable"
				:columns="[
					{ key: 'title', name: 'Title' },
					{ key: 'difficulty', name: 'Difficulty' },
				]"
				:rows="rows"
				:count="rows?.length"
			/>
		</XCard>
	</section>
</template>
