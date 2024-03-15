<script lang="ts" setup>
import type { EChartsOption } from 'echarts';
import { ref, computed } from 'vue';
import { XBreadcrumb, XCard, XChart } from '@lyra/ui';
import { use } from 'echarts/core';
import { LineChart } from 'echarts/charts';

use([LineChart]);

const data2021 = ref([40, 68, 86, 74, 56, 60, 87]);
const data2022 = ref([65, 78, 66, 44, 56, 67, 75]);

const lineChartOption = computed<EChartsOption>(() => {
	return {
		legend: {
			bottom: '2.5%',
			data: ['2021', '2022'],
		},
		grid: {
			top: '10%',
		},
		xAxis: {
			type: 'category',
			data: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
		},
		yAxis: {
			type: 'value',
		},
		tooltip: {
			trigger: 'axis',
		},
		series: [
			{
				type: 'line',
				name: '2021',
				itemStyle: { color: '#6366f1' },
				smooth: true,
				data: data2021.value,
			},
			{
				type: 'line',
				name: '2022',
				itemStyle: { color: '#ec4899' },
				smooth: true,
				data: data2022.value,
			},
		],
	};
});

const areaChartOption = computed<EChartsOption>(() => {
	return {
		legend: {
			bottom: '2.5%',
			data: ['2021', '2022'],
		},
		grid: {
			top: '10%',
		},
		xAxis: {
			type: 'category',
			boundaryGap: false,
			data: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
		},
		yAxis: {
			type: 'value',
		},
		tooltip: {
			trigger: 'axis',
		},
		series: [
			{
				type: 'line',
				name: '2021',
				itemStyle: { color: '#6366f1' },
				areaStyle: {},
				smooth: true,
				data: data2021.value,
			},
			{
				type: 'line',
				name: '2022',
				itemStyle: { color: '#ec4899' },
				areaStyle: {},
				smooth: true,
				data: data2022.value,
			},
		],
	};
});
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Charts' }, { text: 'Line & Area' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Line & Area</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Line</h2>

		<XCard>
			<XChart :option="lineChartOption" class="h-100 w-full" />
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Area</h2>

		<XCard>
			<XChart :option="areaChartOption" class="h-100 w-full" />
		</XCard>
	</section>
</template>
