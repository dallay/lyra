<template>
	<th class="Column">
		<slot></slot>
	</th>
</template>

<style lang="scss" scoped>
.Column {
	@apply h-10 px-4 py-0 text-zinc-600 dark:text-zinc-400 bg-gray-200 dark:bg-gray-700 border border-zinc-100 dark:border-zinc-600 border-l-0 border-r-0 align-middle whitespace-nowrap font-semibold text-left;

	> :deep(div) {
		@apply inline-flex items-center h-full;
	}
}
</style>
