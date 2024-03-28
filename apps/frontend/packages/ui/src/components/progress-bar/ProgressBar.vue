<script lang="ts" setup>
defineProps<{
	percentage?: number;
}>();
</script>

<template>
	<div class="ProgressBar">
		<div
			class="ProgressBar-Value"
			:class="{
				indeterminate: typeof percentage !== 'number',
			}"
			:style="[
				typeof percentage === 'number'
					? `transform: translateX(0) scaleX(${Number(percentage) / 100})`
					: '',
			]"
		></div>
	</div>
</template>

<style lang="scss" scoped>
.ProgressBar {
	@apply bg-primary-200 h-1 w-full overflow-hidden;
}

.ProgressBar-Value {
	@apply bg-primary-500 h-full w-full;

	transform-origin: 0% 50%;

	&.indeterminate {
		animation: indeterminate 1s infinite linear;

		@keyframes indeterminate {
			0% {
				transform: translateX(0) scaleX(0);
			}

			40% {
				transform: translateX(0) scaleX(0.4);
			}

			100% {
				transform: translateX(100%) scaleX(0.5);
			}
		}
	}
}
</style>
