<template>
	<li class="ListboxItem">
		<slot></slot>
	</li>
</template>

<style lang="scss" scoped>
.ListboxItem {
	@apply cursor-pointer px-3 py-1;
	@apply hover:text-primary-500 dark:hover:text-primary-100 hover:bg-primary-100 dark:hover:bg-primary-600 hover:rounded-md;
}
</style>
