<template>
	<td class="Cell">
		<slot></slot>
	</td>
</template>

<style lang="scss" scoped>
.Cell {
	@apply h-38px whitespace-nowrap px-4 py-0 align-middle;

	> :deep(div) {
		@apply inline-flex h-full items-center;
	}
}
</style>
