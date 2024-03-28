<script lang="ts" setup>
import { nextTick, ref, computed, reactive } from 'vue';
import { onClickOutside } from '@vueuse/core';
import { add, sub, getYear } from 'date-fns';
import { range } from '@lyra/utilities';

import useScrollParent from '../../composables/scroll-parent/useScrollParent';

import TextField from '../text-field/TextField.vue';
import Fade from '../fade/Fade.vue';

const valueModel = defineModel<number | string>('value');

defineProps<{
	disabled?: boolean;
}>();

const target = ref();
const input = ref();
const picker = ref();

const flux = reactive({
	showDatePicker: false,
	direction: '' as 'down' | 'up' | '',
	resizePanel() {
		const rect = input.value.$el.querySelector('.TextField-Input').getBoundingClientRect();

		picker.value.style.left = `${rect.left}px`;

		const center = window.innerHeight / 2;

		if (rect.top > center) {
			picker.value.style.top = `${rect.top}px`;
			flux.direction = 'up';
		} else {
			picker.value.style.top = `${rect.bottom}px`;
			flux.direction = 'down';
		}
	},
	openPicker() {
		flux.showDatePicker = true;

		flux.showYears = true;

		if (valueModel.value) {
			flux.currentMoment = new Date(Number(valueModel.value), 0);
		} else {
			flux.currentMoment = new Date();
		}

		const currentYear = getYear(flux.currentMoment);
		flux.yearRange = range(currentYear - 5, currentYear + 11);

		nextTick(() => {
			flux.resizePanel();
		});
	},

	showYears: true,

	now: new Date(),
	currentMoment: new Date(),
	currentPeriodDates: [] as any[],

	yearRange: [] as number[],
	year: null as null | number,

	decrement() {
		flux.currentMoment = sub(flux.currentMoment, { years: 16 });
		const currentYear = getYear(flux.currentMoment);
		flux.yearRange = range(currentYear - 5, currentYear + 11);
	},
	increment() {
		flux.currentMoment = add(flux.currentMoment, { years: 16 });
		const currentYear = getYear(flux.currentMoment);
		flux.yearRange = range(currentYear - 5, currentYear + 11);
	},
	selectYear(val: number) {
		flux.showDatePicker = false;
		valueModel.value = val;
	},
});

onClickOutside(target, () => {
	flux.showDatePicker = false;
});

useScrollParent(
	computed(() => picker.value),
	() => {
		if (flux.showDatePicker) flux.resizePanel();
	}
);
</script>

<template>
	<div ref="target" class="w-full">
		<TextField
			ref="input"
			v-bind="$attrs"
			:value="valueModel ? String(valueModel) : ''"
			:disabled="disabled"
			append="i-fa-calendar-o"
			readonly
			@focus="flux.openPicker"
			@append="flux.openPicker"
		>
			<slot></slot>
		</TextField>

		<Fade>
			<div
				v-if="flux.showDatePicker"
				ref="picker"
				class="z-101 fixed rounded border border-gray-200 bg-white p-2 shadow-lg dark:border-gray-700 dark:bg-slate-800"
				:class="{
					'DatePicker-DatePane-PlacementBottom': flux.direction === 'down',
					'DatePicker-DatePane-PlacementTop': flux.direction === 'up',
				}"
			>
				<div class="mb-1 flex items-center justify-between">
					<div
						class="cursor-pointer rounded-full p-2 hover:bg-slate-200 dark:hover:bg-slate-600"
						@click="flux.decrement"
					>
						<div class="i-fa-chevron-left h-3 w-3"></div>
					</div>

					<div v-if="flux.showYears">{{ flux.yearRange[0] }} ~ {{ flux.yearRange[15] }}</div>

					<div
						class="cursor-pointer rounded-full p-2 hover:bg-slate-200 dark:hover:bg-slate-600"
						@click="flux.increment"
					>
						<div class="i-fa-chevron-right h-3 w-3"></div>
					</div>
				</div>

				<div v-show="flux.showYears" class="grid w-48 grid-cols-4 gap-1 text-center">
					<div
						v-for="year in flux.yearRange"
						:key="year"
						:value="year"
						class="flex cursor-pointer items-center justify-center rounded text-sm hover:bg-slate-200 dark:hover:bg-slate-600"
						:class="{
							'important:hover:bg-blue-500 bg-blue-400 text-white': year === getYear(flux.now),
							'bg-primary-600 important:hover:bg-primary-700 text-white':
								valueModel &&
								year === getYear(new Date(Number(valueModel), 0)) &&
								getYear(flux.currentMoment) === getYear(new Date(Number(valueModel), 0)),
						}"
						@click="flux.selectYear(year)"
					>
						{{ year }}
					</div>
				</div>
			</div>
		</Fade>
	</div>
</template>

<style lang="scss" scoped>
.DatePicker-DatePane-PlacementBottom {
	transform: translateY(0.5rem);
}

.DatePicker-DatePane-PlacementTop {
	transform: translateY(-0.5rem) translateY(-100%);
}
</style>
