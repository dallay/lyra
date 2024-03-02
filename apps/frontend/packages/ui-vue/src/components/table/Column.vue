<template>
	<th class="Column">
		<slot></slot>
	</th>
</template>

<style lang="scss" scoped>
.Column {
	@apply h-10 whitespace-nowrap border border-l-0 border-r-0 border-zinc-100 bg-gray-200 px-4 py-0 text-left align-middle font-semibold text-zinc-600 dark:border-zinc-600 dark:bg-gray-700 dark:text-zinc-400;

	> :deep(div) {
		@apply inline-flex h-full items-center;
	}
}
</style>
