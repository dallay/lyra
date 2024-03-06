<template>
	<tr class="Row">
		<slot></slot>
	</tr>
</template>

<style lang="scss" scoped>
.Row {
	@apply border-b last:border-b-0 hover:bg-slate-100 dark:border-slate-600 dark:hover:bg-slate-600;
}
</style>
