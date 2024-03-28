<script lang="ts" setup>
import { toRef, useSlots } from 'vue';

const props = defineProps<{
	total?: number;
}>();

const slots = useSlots();

const totalRef = toRef(props, 'total', 0);
const remaining = slots?.default?.()?.length || 0;
</script>

<template>
	<div class="AvatarGroup">
		<slot></slot>

		<div class="AvatarGroup-Total">+{{ totalRef - remaining }}</div>
	</div>
</template>

<style lang="scss" scoped>
.AvatarGroup {
	@apply flex -space-x-2;
}

.AvatarGroup-Total {
	@apply flex h-10 w-10 items-center justify-center rounded-full;
	@apply bg-gray-700 text-xs font-medium text-white hover:bg-gray-600;
}
</style>
