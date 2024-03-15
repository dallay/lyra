<template>
	<th class="Column">
		<slot></slot>
	</th>
</template>

<style lang="scss" scoped>
.Column {
	@apply h-38px;
	@apply bg-gray-200 px-4 py-0 text-zinc-600 dark:bg-gray-700 dark:text-zinc-400;
	@apply border border-l-0 border-r-0 border-zinc-100 dark:border-zinc-600;
	@apply whitespace-nowrap text-left align-middle font-semibold;

	> :deep(div) {
		@apply inline-flex h-full items-center;
	}
}
</style>
