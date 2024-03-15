<script lang="ts" setup>
import { RouterLink } from 'vue-router';

defineProps<{
	to?: string;
	href?: string;
}>();
</script>

<template>
	<RouterLink v-if="to" v-bind="$attrs" :to="to" class="Link"><slot></slot></RouterLink>
	<a v-else-if="href" v-bind="$attrs" :href="href" class="Link"><slot></slot></a>
	<span v-else class="Link"><slot></slot></span>
</template>

<style lang="scss" scoped>
.Link {
	@apply text-primary-500 cursor-pointer text-sm font-bold;
	@apply hover:text-primary-600 dark:hover:text-primary-400 hover:underline;
	@apply focus:text-primary-600 dark:focus:text-primary-400 focus:underline focus:outline-none;
}
</style>
