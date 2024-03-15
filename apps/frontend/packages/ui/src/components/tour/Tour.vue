<script lang="ts" setup>
import { nextTick, ref, watch, onUnmounted } from 'vue';

import Button from '../button/Button.vue';

interface Step {
	title?: string;
	content?: string;
	target?: any;
}

const openModel = defineModel<boolean>('open');
const stepModel = defineModel<number>('step', { default: 0 });

const props = defineProps<{
	steps?: Step[];
}>();

function onPrevious() {
	stepModel.value -= 1;
}

function onNext() {
	stepModel.value += 1;
}

function onFinish() {
	openModel.value = false;
}

const tour = ref();
const guide = ref();

watch(
	() => openModel.value,
	async (open) => {
		document.body.style.overflow = open ? 'hidden' : 'auto';

		if (open) {
			await nextTick();

			const currentTarget = props.steps?.[stepModel.value].target.value;
			currentTarget.scrollIntoView({ block: 'center' });
			const rect = currentTarget.getBoundingClientRect();

			tour.value.style.width = `${rect.width}px`;
			tour.value.style.height = `${rect.height}px`;
			tour.value.style.left = `${rect.x}px`;
			tour.value.style.top = `${rect.y}px`;

			guide.value.style.left = `${rect.x}px`;
			guide.value.style.top = `${rect.y + rect.height + 16}px`;
		} else {
			stepModel.value = 0;
		}
	}
);

watch(
	() => stepModel.value,
	async (step) => {
		if (!openModel.value) return;

		await nextTick();

		const currentTarget = props.steps?.[step].target.value;
		currentTarget.scrollIntoView({ block: 'center' });
		const rect = currentTarget.getBoundingClientRect();

		tour.value.style.width = `${rect.width}px`;
		tour.value.style.height = `${rect.height}px`;
		tour.value.style.left = `${rect.x}px`;
		tour.value.style.top = `${rect.y}px`;

		guide.value.style.left = `${rect.x}px`;
		guide.value.style.top = `${rect.y + rect.height + 16}px`;
	}
);

onUnmounted(() => {
	document.body.style.overflow = 'auto';
});
</script>

<template>
	<div
		v-if="openModel"
		ref="tour"
		class="z-102 border-primary-600 pointer-events-none fixed rounded border-2 transition-all"
		style="box-shadow: rgba(100, 116, 139, 0.5) 0 0 0 3333px"
	></div>

	<div
		v-if="openModel"
		ref="guide"
		class="z-102 fixed max-w-sm rounded-lg bg-white p-4 transition-all dark:bg-slate-800"
	>
		<div class="mb-2 text-lg font-bold">{{ steps?.[stepModel].title }}</div>
		<div>{{ steps?.[stepModel].content }}</div>

		<div class="mt-6 flex items-center">
			<div class="text-sm">{{ stepModel + 1 }}/{{ steps?.length }}</div>

			<div class="ml-8 flex w-full justify-end gap-2">
				<Button v-if="stepModel > 0" color="secondary" @click="onPrevious">Previous</Button>
				<Button v-if="stepModel + 1 < Number(steps?.length)" @click="onNext">Next</Button>
				<Button v-if="stepModel + 1 === Number(steps?.length)" @click="onFinish">Finish</Button>
			</div>
		</div>

		<div
			class="absolute left-10 top-0 h-4 w-4 -translate-x-1/2 -translate-y-1/2 rotate-45 transform rounded-tl border-l border-t bg-white dark:border-slate-800 dark:bg-slate-800"
		></div>

		<!-- top -->
		<!-- <div
        class="absolute top-0 left-1/2 transform -translate-x-1/2 -translate-y-1/2 rotate-45 w-4 h-4 bg-white border-t border-l rounded-tl"
      ></div> -->

		<!-- right -->
		<!-- <div
        class="absolute right-0 top-1/2 transform translate-x-1/2 -translate-y-1/2 rotate-45 w-4 h-4 bg-white border-t border-r rounded-tr"
      ></div> -->

		<!-- bottom -->
		<!-- <div
        class="absolute bottom-0 left-1/2 transform -translate-x-1/2 translate-y-1/2 rotate-45 w-4 h-4 bg-white border-r border-b rounded-rb"
      ></div> -->

		<!-- left -->
		<!-- <div
        class="absolute left-0 top-1/2 transform -translate-x-1/2 -translate-y-1/2 rotate-45 w-4 h-4 bg-white border-l border-b rounded-lb"
      ></div> -->
	</div>
</template>
