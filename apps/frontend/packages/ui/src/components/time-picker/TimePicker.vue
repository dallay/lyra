<script lang="ts" setup>
import { nextTick, ref, computed, reactive } from 'vue';
import { onClickOutside } from '@vueuse/core';

import useScrollParent from '../../composables/scroll-parent/useScrollParent';

import TextField from '../text-field/TextField.vue';
import Fade from '../fade/Fade.vue';

const props = defineProps<{
	value?: string;
	clearable?: boolean;
}>();

const emit = defineEmits<{
	(evt: 'update:value', val?: string): void;
}>();

const valueModel = computed({
	get: () => props.value,
	set: (val) => emit('update:value', val),
});

const flux = reactive({
	show: false,

	hour: '01',
	minute: '00',
	meridiem: 'AM' as 'AM' | 'PM',

	open() {
		flux.show = true;

		if (!valueModel.value) {
			const now = new Date();

			if (now.getHours() > 12) {
				flux.hour = String(now.getHours() - 12).padStart(2, '0');
				flux.meridiem = 'PM';
			} else {
				flux.hour = String(now.getHours()).padStart(2, '0');
			}

			flux.minute = String(now.getMinutes()).padStart(2, '0');
		}

		nextTick(() => {
			resizePanel();
		});
	},
	display(val: typeof valueModel.value) {
		if (val) {
			const [hour, minute] = val.split(':');

			if (Number(hour) > 12) {
				return `${String(Number(hour) - 12).padStart(2, '0')}:${minute.padStart(2, '0')} PM`;
			}

			return `${val} AM`;
		}

		return '';
	},
});

const target = ref();
const input = ref();
const picker = ref();

const direction = ref<'down' | 'up'>('down');

function resizePanel() {
	const rect = input.value.$el.querySelector('.TextField-Input').getBoundingClientRect();

	picker.value.style.left = `${rect.left}px`;

	const center = window.innerHeight / 2;

	if (rect.top > center) {
		picker.value.style.top = `${rect.top}px`;
		direction.value = 'up';
	} else {
		picker.value.style.top = `${rect.bottom}px`;
		direction.value = 'down';
	}
}

function onHour(val: 'up' | 'down') {
	let hour = Number(flux.hour);

	if (val === 'up') {
		if (hour >= 12) {
			hour = 1;
		} else {
			hour += 1;
		}
	}

	if (val === 'down') {
		if (hour <= 1) {
			hour = 12;
		} else {
			hour -= 1;
		}
	}

	flux.hour = String(hour).padStart(2, '0');
	setValueModel();
}

function onMinute(val: 'up' | 'down') {
	let minute = Number(flux.minute);

	if (val === 'up') {
		if (minute >= 59) {
			minute = 0;
		} else {
			minute += 1;
		}
	}

	if (val === 'down') {
		if (minute <= 0) {
			minute = 59;
		} else {
			minute -= 1;
		}
	}

	flux.minute = String(minute).padStart(2, '0');
	setValueModel();
}

function onMeridiem() {
	if (flux.meridiem === 'AM') {
		flux.meridiem = 'PM';
	} else if (flux.meridiem === 'PM') {
		flux.meridiem = 'AM';
	}

	setValueModel();
}

function setValueModel() {
	const hour = flux.meridiem === 'PM' ? String(Number(flux.hour) + 12) : flux.hour;
	const minute = String(flux.minute).padStart(2, '0');
	valueModel.value = `${String(hour).padStart(2, '0')}:${minute}`;
}

onClickOutside(target, () => {
	flux.show = false;
});

useScrollParent(
	computed(() => picker.value),
	() => {
		if (flux.show) resizePanel();
	}
);
</script>

<template>
	<div ref="target" class="w-full">
		<TextField
			ref="input"
			v-bind="$attrs"
			:value="flux.display(valueModel)"
			append="i-material-symbols-nest-clock-farsight-analog-outline-rounded"
			readonly
			:clearable="clearable"
			@focus="flux.open"
			@append="flux.open"
			@clear="clearable && (valueModel = '')"
		>
			<slot></slot>
		</TextField>

		<Fade>
			<div
				v-if="flux.show"
				ref="picker"
				class="z-101 fixed rounded border border-gray-200 bg-white p-2 shadow-lg dark:border-gray-700 dark:bg-slate-800"
				:class="{
					'TimePicker-PlacementBottom': direction === 'down',
					'TimePicker-PlacementTop': direction === 'up',
				}"
			>
				<div class="flex w-auto items-center gap-2">
					<div class="flex w-8 flex-col items-center gap-1">
						<div
							class="cursor-pointer rounded-full p-1 hover:bg-slate-200 dark:hover:bg-slate-600"
							@click="onHour('up')"
						>
							<div class="i-material-symbols-keyboard-arrow-up-rounded h-6 w-6"></div>
						</div>

						<div class="text-center">{{ flux.hour }}</div>

						<div
							class="cursor-pointer rounded-full p-1 hover:bg-slate-200 dark:hover:bg-slate-600"
							@click="onHour('down')"
						>
							<div class="i-material-symbols-keyboard-arrow-down-rounded h-6 w-6"></div>
						</div>
					</div>

					<div>:</div>

					<div class="flex w-8 flex-col items-center gap-1">
						<div
							class="cursor-pointer rounded-full p-1 hover:bg-slate-200 dark:hover:bg-slate-600"
							@click="onMinute('up')"
						>
							<div class="i-material-symbols-keyboard-arrow-up-rounded h-6 w-6"></div>
						</div>

						<div class="text-center">{{ flux.minute }}</div>

						<div
							class="cursor-pointer rounded-full p-1 hover:bg-slate-200 dark:hover:bg-slate-600"
							@click="onMinute('down')"
						>
							<div class="i-material-symbols-keyboard-arrow-down-rounded h-6 w-6"></div>
						</div>
					</div>

					<div></div>

					<div class="flex w-8 flex-col items-center gap-1">
						<div
							class="cursor-pointer rounded-full p-1 hover:bg-slate-200 dark:hover:bg-slate-600"
							@click="onMeridiem"
						>
							<div class="i-material-symbols-keyboard-arrow-up-rounded h-6 w-6"></div>
						</div>

						<div class="text-center">{{ flux.meridiem }}</div>

						<div
							class="cursor-pointer rounded-full p-1 hover:bg-slate-200 dark:hover:bg-slate-600"
							@click="onMeridiem"
						>
							<div class="i-material-symbols-keyboard-arrow-down-rounded h-6 w-6"></div>
						</div>
					</div>
				</div>
			</div>
		</Fade>
	</div>
</template>

<style lang="scss" scoped>
.TimePicker-PlacementBottom {
	transform: translateY(0.5rem);
}

.TimePicker-PlacementTop {
	transform: translateY(-0.5rem) translateY(-100%);
}
</style>
