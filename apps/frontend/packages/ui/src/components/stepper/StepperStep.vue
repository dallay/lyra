<script lang="ts" setup>
import type { ComputedRef } from 'vue';
import { inject } from 'vue';
import { useMediaQuery } from '@vueuse/core';

defineProps<{
	step: number;
}>();

const stepper = inject('Stepper') as { modelValue: ComputedRef<number> };

const md = useMediaQuery('(min-width: 768px)');
</script>

<template>
	<li
		v-show="md ? true : Number(step) === stepper.modelValue.value"
		class="StepperStep"
		:class="{
			'text-success-500 dark:text-success-400': Number(step) < stepper.modelValue.value,
			'text-primary-500 dark:text-primary-400': Number(step) === stepper.modelValue.value,
			'text-slate-500 dark:text-slate-400': Number(step) > stepper.modelValue.value,
		}"
	>
		<div class="StepperStep-Icon">
			<div
				v-if="Number(step) < stepper.modelValue.value"
				class="i-mdi-checkbox-marked-circle-outline h-6 w-6"
			></div>
			<div
				v-if="Number(step) === stepper.modelValue.value"
				class="i-mdi-dots-horizontal-circle-outline h-6 w-6"
			></div>
			<div
				v-if="Number(step) > stepper.modelValue.value"
				class="i-mdi-checkbox-blank-circle-outline h-6 w-6"
			></div>
		</div>

		<span><slot></slot></span>
	</li>
</template>

<style lang="scss" scoped>
.StepperStep {
	@apply relative flex flex-1 flex-col items-center p-3;

	&:not(:last-of-type)::after {
		@apply z-1 absolute hidden w-full border border-slate-300 content-[''] md:block dark:border-slate-500;

		transform: translate(50%, 11px);
	}
}

.StepperStep-Icon {
	@apply hidden md:flex;
	@apply z-2 mb-4 h-6 w-12 items-center justify-center bg-white dark:bg-slate-700;
}
</style>
