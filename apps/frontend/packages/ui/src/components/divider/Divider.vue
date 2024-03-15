<script lang="ts" setup>
withDefaults(
	defineProps<{
		orientation?: 'horizontal' | 'vertical';
	}>(),
	{
		orientation: 'horizontal',
	}
);
</script>

<template>
	<div
		class="Divider"
		:class="{
			horizontal: orientation === 'horizontal',
			vertical: orientation === 'vertical',
		}"
	></div>
</template>

<style lang="scss" scoped>
.Divider {
	@apply bg-gray-200 dark:bg-gray-700;

	&.horizontal {
		@apply h-1px my-4 w-full;
	}

	&.vertical {
		@apply w-1px mx-4 min-h-full;
	}
}
</style>
