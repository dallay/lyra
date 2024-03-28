<script setup lang="ts">
import { reactive } from 'vue';
import Select from '../select/Select.vue';
import Button from '../button/Button.vue';
import { useLocale } from 'vue-localer';
const props = defineProps<TableControlProps>();
const emit = defineEmits<{
	(event: 'previousPage'): void;
	(event: 'nextPage'): void;
	(event: 'updateRowsPerPage', value: number): void;
}>();
const locale = useLocale();
export interface TableControlProps {
	currentPage: number;
	rowsPerPage: number;
	rowsPerPageOptions: { label: string; value: number }[];
	loading: boolean;
	paginationInfo: string;
}
const flux = reactive({
	rowsPerPage: props.rowsPerPage,
});

function previousPage() {
	if (props.currentPage > 1) {
		emit('previousPage');
	}
}

function nextPage() {
	emit('nextPage');
}
function updateRowsPerPage(val?: string | number) {
	if (typeof val === 'number') {
		emit('updateRowsPerPage', val);
	}
}
</script>

<template>
	<div class="flex flex-col items-center justify-end gap-4 p-4 text-sm md:flex-row">
		<div class="Table-RowsPerPage">
			{{ locale?.rowsPerPage || 'Rows per page:' }}
			<div class="ml-2 w-auto">
				<Select
					v-model:value="flux.rowsPerPage"
					:options="rowsPerPageOptions"
					:disabled="loading"
					class="!border-transparent"
					@change="updateRowsPerPage"
				/>
			</div>
		</div>

		<div class="flex items-center">{{ paginationInfo }}</div>

		<div class="flex gap-4">
			<Button
				prepend="i-material-symbols-chevron-left-rounded"
				:label="locale.previousPage || 'Previous'"
				variant="text"
				color="secondary"
				:disabled="loading"
				@click="previousPage"
			/>
			<Button
				:label="locale.nextPage || 'Next'"
				append="i-material-symbols-chevron-right-rounded"
				variant="text"
				color="secondary"
				:disabled="loading"
				@click="nextPage"
			/>
		</div>
	</div>
</template>

<style scoped lang="scss">
.Table-RowsPerPage {
	@apply flex items-center;
}
</style>
