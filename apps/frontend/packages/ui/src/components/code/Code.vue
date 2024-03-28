<template>
	<code class="Code"><slot></slot></code>
</template>

<style lang="scss" scoped>
.Code {
	@apply inline-block h-fit whitespace-nowrap rounded bg-gray-300 px-1.5 py-0.5 font-mono text-sm font-normal text-gray-700;
}
</style>
