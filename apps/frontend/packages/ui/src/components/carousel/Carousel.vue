<script lang="ts" setup>
import type { UseSwipeDirection } from '@vueuse/core';
import { ref, useSlots, toRef } from 'vue';
import { useSwipe, usePointerSwipe } from '@vueuse/core';

import Button from '../button/Button.vue';

const props = defineProps<{
	move?: number;
}>();

const moveRef = toRef(props, 'move', 100);

const slots = useSlots();
const defaultSlot = slots.default?.();
const length = Number(defaultSlot?.[0]?.children?.length);

const left = ref(0);
const target = ref<HTMLDivElement>();

function previous() {
	if (left.value === 0) return;
	left.value += moveRef.value;
}

function next() {
	if (left.value === -moveRef.value * (length - 1)) return;
	left.value -= moveRef.value;
}

function goTo(num: number) {
	left.value = -moveRef.value * (num - 1);
}

useSwipe(target, {
	onSwipeEnd(evt: TouchEvent, dir: UseSwipeDirection) {
		if (dir === 'left') next();
		if (dir === 'right') previous();
	},
});

usePointerSwipe(target, {
	onSwipeEnd(evt: PointerEvent, dir: UseSwipeDirection) {
		if (evt.pointerType === 'touch') return;

		if (dir === 'left') next();
		if (dir === 'right') previous();
	},
});
</script>

<template>
	<div ref="target" class="relative overflow-hidden rounded-lg">
		<div class="relative h-full w-full transition-all duration-500" :style="{ left: `${left}%` }">
			<slot></slot>
		</div>

		<div class="text-primary-500 absolute bottom-3 left-1/2 z-10 flex -translate-x-1/2">
			<template v-for="num in length" :key="num">
				<div
					class="h-5 w-5 cursor-pointer"
					:class="[
						left === -moveRef * (num - 1)
							? 'i-material-symbols-fiber-manual-record'
							: 'i-material-symbols-fiber-manual-record-outline',
					]"
					@click="goTo(num)"
				></div>
			</template>
		</div>

		<Button
			icon="i-material-symbols-chevron-left-rounded"
			class="absolute left-3 top-1/2 z-10 -translate-y-1/2"
			:disabled="left === 0"
			@click="previous"
		/>
		<Button
			icon="i-material-symbols-chevron-right-rounded"
			class="absolute right-3 top-1/2 z-10 -translate-y-1/2"
			:disabled="left === -moveRef * (length - 1)"
			@click="next"
		/>
	</div>
</template>
