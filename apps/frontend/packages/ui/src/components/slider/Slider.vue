<script lang="ts" setup>
import { ref, computed, watch } from 'vue';

defineOptions({
	inheritAttrs: false,
});

const props = withDefaults(
	defineProps<{
		value?: string;
		min?: string;
		max?: string;
		step?: string;
		steppable?: boolean;
		disabled?: boolean;
	}>(),
	{
		value: '0',
		min: '0',
		max: '100',
		step: '1',
	}
);

const emit = defineEmits<{
	(evt: 'update:value', val: string): void;
	(evt: 'change', val: string): void;
}>();

const valueModel = computed({
	get: () => props.value || '0',
	set: (val) => emit('update:value', val),
});

const backgroundSize = ref('0 100%');
const left = ref('0');

watch(
	() => valueModel.value,
	(val) => {
		const _val = Number(val);
		const _min = Number(props.min);
		const _max = Number(props.max);
		const percentage = ((_val - _min) * 100) / (_max - _min);

		backgroundSize.value = `${percentage}% 100%`;
		left.value = `${percentage}%`;
	},
	{ immediate: true }
);
</script>

<template>
	<div
		class="flex w-full justify-center"
		:class="[{ 'pb-5': steppable }, disabled ? 'opacity-60' : '']"
	>
		<div class="Slider-Wrapper">
			<input
				v-model="valueModel"
				v-bind="$attrs"
				type="range"
				:min="min"
				:max="max"
				:step="step"
				:disabled="disabled"
				class="Slider-Input"
				:style="{ 'background-size': backgroundSize }"
			/>

			<output class="Slider-Output" :style="{ left }">{{ valueModel }}</output>

			<div v-if="steppable" class="Slider-Steps">
				<div class="Slider-Step">
					<div class="Slider-StepNum">{{ min }}</div>
				</div>

				<div
					v-for="num in (Number(max) - Number(min)) / Number(step)"
					:key="num"
					class="Slider-Step"
				>
					<div class="Slider-StepNum">{{ num * Number(step) + Number(min) }}</div>
				</div>
			</div>
		</div>
	</div>
</template>

<style lang="scss" scoped>
.Slider-Wrapper {
	@apply min-h-38px relative flex items-center;

	width: calc(100% - 1rem);
}

.Slider-Input {
	@apply appearance-none outline-none;
	@apply absolute h-2 cursor-pointer rounded bg-slate-400/60;
	@apply from-primary-400 to-primary-600 bg-gradient-to-r bg-no-repeat;

	width: calc(100% + 1rem);
	left: -0.46875rem;

	&:focus {
		&::-webkit-slider-thumb {
			@apply ring-primary-500/50 ring-2;
		}

		&::-moz-range-thumb {
			@apply ring-primary-500/50 ring-2;
		}

		& + .Slider-Output {
			@apply block;
		}
	}

	&::-webkit-slider-thumb {
		@apply cursor-ew-resize appearance-none;
		@apply bg-primary-600 h-4 w-4 rounded-full shadow transition;

		&:hover {
			@apply ring-5 ring-primary-500/50;
		}

		&:active {
			@apply ring-7 ring-primary-500/50;
		}
	}

	&::-moz-range-thumb {
		@apply cursor-ew-resize appearance-none border-0;
		@apply bg-primary-600 h-4 w-4 rounded-full shadow transition;

		&:hover {
			@apply ring-5 ring-primary-500/50;
		}

		&:active {
			@apply ring-7 ring-primary-500/50;
		}
	}
}

.Slider-Output {
	@apply hidden;
	@apply bg-primary-500 absolute -top-8 z-10 -translate-x-1/2;
	@apply rounded px-2 py-1 text-white;
}

.Slider-Steps {
	@apply flex w-full justify-between;
}

.Slider-Step {
	@apply relative;

	&::before {
		@apply w-1px absolute left-0 top-2 h-2 bg-slate-400 content-[''];
	}
}

.Slider-StepNum {
	@apply left-0.5px absolute top-3 -translate-x-1/2;
}
</style>
