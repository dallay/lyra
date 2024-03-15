<script lang="ts" setup>
defineProps<{
	title?: string;
	date?: string;
}>();
</script>

<template>
	<li class="TimelineItem">
		<div class="TimelineItem-Line"></div>
		<time class="TimelineItem-Date">{{ date }}</time>
		<h3 class="TimelineItem-Title">{{ title }}</h3>
		<slot></slot>
	</li>
</template>

<style lang="scss" scoped>
.TimelineItem {
	@apply mb-10 ml-4;

	&:last-child {
		@apply mb-0;
	}
}

.TimelineItem-Line {
	@apply absolute -left-1.5 mt-1.5 h-3 w-3 rounded-full border border-white bg-gray-200 dark:border-gray-900 dark:bg-gray-700;
}

.TimelineItem-Date {
	@apply mb-1 text-sm font-normal leading-none text-gray-400 dark:text-gray-500;
}

.TimelineItem-Title {
	@apply text-lg font-semibold text-gray-900 dark:text-white;
}
</style>
