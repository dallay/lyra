<script setup lang="ts">
import { ref } from 'vue';
export interface CursorPaginationProps {
	nextPageCursor: string;
	perPage: number;
}
const props = withDefaults(defineProps<CursorPaginationProps>(), {
	nextPageCursor: '',
	perPage: 10,
});

const emits = defineEmits<{
	(evt: 'updatePage', val: { cursor: string; perPage: number }): void;
}>();
const cursor = ref(props.nextPageCursor);

const nextPage = () => {
	if (!props.nextPageCursor) return;
	cursor.value = props.nextPageCursor;
	emits('updatePage', { cursor: cursor.value, perPage: props.perPage });
};
</script>

<template>
	<nav
		class="flex-column flex flex-wrap items-center justify-center pt-4 md:flex-row"
		aria-label="Table navigation"
	>
		<button
			v-if="props.nextPageCursor"
			type="button"
			:disabled="!props.nextPageCursor"
			class="border-tertiary-300 text-tertiary-500 dark:border-tertiary-700 dark:bg-tertiary-800 dark:text-tertiary-400 flex h-8 items-center justify-center rounded-lg border bg-white px-3 leading-tight"
			:class="{
				'cursor-not-allowed': !props.nextPageCursor,
				'hover:bg-tertiary-100 hover:text-tertiary-700 dark:hover:bg-tertiary-700 dark:hover:text-white':
					props.nextPageCursor,
			}"
			data-test="load-more-button"
			@click.prevent="nextPage"
		>
			Load More
		</button>
	</nav>
</template>

<style scoped></style>
