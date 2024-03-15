<script lang="ts" setup>
import { ref } from 'vue';
import { XBreadcrumb, XCard, XMonthPicker, XMonthRangePicker, XSwitch } from '@lyra/ui';
import { subMonths, addMonths, format } from 'date-fns';

const month = ref('');

const startMonth = ref('');
const endMonth = ref('');

const startMonth2 = ref('');
const endMonth2 = ref('');
const minMonth = ref(true);
const maxMonth = ref(true);
</script>

<template>
	<XBreadcrumb
		:items="[
			{ text: 'Library' },
			{ text: 'Data Entry' },
			{ text: 'Pickers' },
			{ text: 'MonthPicker' },
		]"
	/>

	<h1 class="my-4 text-4xl font-extrabold">MonthPicker</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<XMonthPicker v-model:value="month" />
			<div class="mt-1">{{ month }}</div>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Range</h2>

		<XCard>
			<XMonthRangePicker v-model:startValue="startMonth" v-model:endValue="endMonth" />

			<div class="mt-1">
				<div>Start Month: {{ startMonth }}</div>
				<div>End Month: {{ endMonth }}</div>
			</div>
		</XCard>

		<section class="my-4">
			<h3 class="my-4 pt-2 text-2xl font-semibold">Min/Max Month</h3>

			<XCard>
				<XMonthRangePicker
					v-model:startValue="startMonth2"
					v-model:endValue="endMonth2"
					:minMonth="minMonth ? subMonths(new Date(), 2) : undefined"
					:maxMonth="maxMonth ? addMonths(new Date(), 2) : undefined"
				/>

				<div class="mt-1 space-y-1">
					<XSwitch v-model:value="minMonth">
						Min Month: {{ format(subMonths(new Date(), 2), 'yyyy-MM') }}
					</XSwitch>
					<XSwitch v-model:value="maxMonth">
						Max Month: {{ format(addMonths(new Date(), 2), 'yyyy-MM') }}
					</XSwitch>
				</div>
			</XCard>
		</section>
	</section>
</template>
