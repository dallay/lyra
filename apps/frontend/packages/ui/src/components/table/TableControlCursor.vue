<script setup lang="ts">
import { reactive } from 'vue';
import { useLocale } from 'vue-localer';
import Button from '../button/Button.vue';

const props = defineProps<TableControlCursorProps>();
const emit = defineEmits<{
	(event: 'loadMore', value: { cursor: string; limit: number }): void;
}>();
const locale = useLocale();
export interface TableControlCursorProps {
	cursor: string;
	limit: number;
	loading: boolean;
}
const flux = reactive({
	cursor: props.cursor,
	limit: props.limit,
});

function loadMore() {
	if (!flux.cursor) return;
	emit('loadMore', { cursor: flux.cursor, limit: flux.limit });
}
</script>

<template>
	<div class="flex justify-center">
		<Button
			v-if="!loading && cursor"
			:label="locale.loadMore || 'Load more'"
			variant="text"
			color="secondary"
			:disabled="loading"
			:loading="loading"
			@click="loadMore"
		/>
	</div>
</template>

<style scoped lang="scss"></style>
