<script setup lang="ts">
import { ref } from 'vue';

const props = defineProps({
	nextPageCursor: {
		type: String,
		required: true,
	},
	perPage: {
		type: Number,
		required: true,
		default: 10,
	},
});

const emits = defineEmits(['updatePage']);
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
			class="flex h-8 items-center justify-center rounded-lg border border-tertiary-300 bg-white px-3 leading-tight text-tertiary-500 dark:border-tertiary-700 dark:bg-tertiary-800 dark:text-tertiary-400"
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
