<script lang="ts" setup>
import { ref } from 'vue';

const startValueModel = defineModel<string>('startValue', { default: '0' });
const endValueModel = defineModel<string>('endValue', { default: '100' });

withDefaults(
	defineProps<{
		min?: string;
		max?: string;
		step?: string;
		steppable?: boolean;
		disabled?: boolean;
	}>(),
	{
		min: '0',
		max: '100',
		step: '1',
		steppable: false,
		disabled: false,
	}
);

const target = ref();

function onInput(evt: Event) {
	const slides = target.value.querySelectorAll('input');
	const min = parseFloat(slides[0].min);
	const max = parseFloat(slides[0].max);

	let slide1 = parseFloat(slides[0].value);
	let slide2 = parseFloat(slides[1].value);

	const percentageMin = (slide1 / (max - min)) * 100;
	const percentageMax = (slide2 / (max - min)) * 100;

	target.value.style.setProperty('--range-slider-value-low', percentageMin);
	target.value.style.setProperty('--range-slider-value-high', percentageMax);

	if (slide1 >= slide2) {
		const tmp = slide2;
		slide2 = slide1;
		slide1 = tmp;

		if (evt?.currentTarget === slides[0]) {
			slides[0].insertAdjacentElement('beforebegin', slides[1]);
		} else {
			slides[1].insertAdjacentElement('afterend', slides[0]);
		}
	}

	target.value.querySelector('.RangeSlider-Output').setAttribute('data-low', slide1);
	target.value.querySelector('.RangeSlider-Output').setAttribute('data-high', slide2);
	startValueModel.value = String(slide1);
	endValueModel.value = String(slide2);
}
</script>

<template>
	<div
		class="flex w-full justify-center"
		:class="[{ 'pb-5': steppable }, disabled ? 'opacity-60' : '']"
	>
		<div ref="target" class="RangeSlider">
			<input
				value="0"
				type="range"
				:min="min"
				:max="max"
				:step="step"
				:disabled="disabled"
				class="RangeSlider-Input"
				@focus="onInput"
				@input="onInput"
			/>
			<input
				value="100"
				type="range"
				:min="min"
				:max="max"
				:step="step"
				:disabled="disabled"
				class="RangeSlider-Input"
				@focus="onInput"
				@input="onInput"
			/>

			<output class="RangeSlider-Output"></output>

			<div v-if="steppable" class="RangeSlider-Steps">
				<div class="RangeSlider-Step">
					<div class="RangeSlider-StepNum">{{ min }}</div>
				</div>

				<div
					v-for="num in (Number(max) - Number(min)) / Number(step)"
					:key="num"
					class="RangeSlider-Step"
				>
					<div class="RangeSlider-StepNum">{{ num * Number(step) + Number(min) }}</div>
				</div>
			</div>
		</div>
	</div>
</template>

<style lang="scss" scoped>
.RangeSlider {
	$range-slider-color: #6366f1;

	--range-slider-value-low-fallback: calc(var(--range-slider-value-low, 0) * 1%);
	--range-slider-value-high-fallback: calc(var(--range-slider-value-high, 100) * 1%);

	@apply min-h-38px relative flex items-center;

	width: calc(100% - 1rem);

	&::before {
		@apply absolute left-0 top-1/2 -mt-1 w-full content-[''];
		@apply h-2 rounded bg-slate-400/60;

		background-image: linear-gradient(
			to right,
			transparent var(--range-slider-value-low-fallback),
			$range-slider-color var(--range-slider-value-low-fallback),
			$range-slider-color var(--range-slider-value-high-fallback),
			transparent var(--range-slider-value-high-fallback)
		);
	}
}

.RangeSlider-Input {
	@apply pointer-events-none;
	@apply appearance-none outline-none;
	@apply absolute top-1/2 -translate-y-1/2 bg-transparent;

	width: calc(100% + 1rem);
	left: -0.46875rem;

	&:focus {
		&::-webkit-slider-thumb {
			@apply ring-primary-500/50 ring-2;
		}

		&::-moz-range-thumb {
			@apply ring-primary-500/50 ring-2;
		}

		& ~ .RangeSlider-Output {
			@apply block;
		}
	}

	&::-webkit-slider-thumb {
		@apply pointer-events-auto;
		@apply cursor-ew-resize appearance-none;
		@apply bg-primary-600 h-4 w-4 rounded-full shadow transition;

		&:hover {
			@apply ring-5 ring-primary-500/40;
		}

		&:active {
			@apply ring-7 ring-primary-500/40;
		}
	}

	&::-moz-range-thumb {
		@apply pointer-events-auto;
		@apply cursor-ew-resize appearance-none border-0;
		@apply bg-primary-600 h-4 w-4 rounded-full shadow transition;

		&:hover {
			@apply ring-5 ring-primary-500/40;
		}

		&:active {
			@apply ring-7 ring-primary-500/40;
		}
	}
}

.RangeSlider-Output {
	@apply hidden;

	&::before,
	&::after {
		@apply absolute -top-8 z-10 -translate-x-1/2;
		@apply bg-primary-500 rounded px-2 py-1 text-white;
	}

	&::before {
		--pos: var(--range-slider-value-low);

		content: attr(data-low);
		left: var(--range-slider-value-low-fallback);
	}

	&::after {
		--pos: var(--range-slider-value-high);

		@apply top-full;

		content: attr(data-high);
		left: var(--range-slider-value-high-fallback);
	}
}

.RangeSlider-Steps {
	@apply flex w-full justify-between;
}

.RangeSlider-Step {
	@apply relative;

	&::before {
		@apply w-1px absolute left-0 top-2 h-2 bg-slate-400 content-[''];
	}
}

.RangeSlider-StepNum {
	@apply left-0.5px absolute top-3 -translate-x-1/2;
}
</style>
