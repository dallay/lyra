<script lang="ts" setup>
import { ref, onMounted, nextTick } from 'vue';

const spinnerWrapper = ref();
const spinner = ref();

onMounted(() => {
	nextTick(() => {
		const size = spinnerWrapper.value.getBoundingClientRect().width / 8;
		spinner.value.style['border-width'] = `${size}px`;
	});
});
</script>

<template>
	<div ref="spinnerWrapper" v-bind="$attrs" :class="{ 'h-8 w-8': !$attrs.hasOwnProperty('class') }">
		<div ref="spinner" class="spinner">
			<span class="sr-only">Loading...</span>
		</div>
	</div>
</template>

<style lang="scss" scoped>
.spinner {
	@apply border-r-primary-500 inline-block h-full w-full animate-spin rounded-full border-gray-300;
}
</style>
