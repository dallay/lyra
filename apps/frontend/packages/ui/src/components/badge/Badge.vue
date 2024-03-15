<script lang="ts" setup>
defineOptions({
	inheritAttrs: false,
});

defineProps<{
	color?: 'primary' | 'secondary' | 'success' | 'danger' | 'warning' | 'info';
	count?: string;
}>();
</script>

<template>
	<span class="relative">
		<slot></slot>
		<span
			v-bind="$attrs"
			class="bg-primary-500 absolute right-0 top-0 flex -translate-y-1/2 translate-x-1/2 items-center justify-center rounded-full text-sm text-white"
			:class="[count ? 'h-5 min-w-5 px-1.5' : 'h-3 w-3']"
			>{{ count }}</span
		>
	</span>
</template>
