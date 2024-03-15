<script lang="ts" setup>
import type { EChartsOption } from 'echarts';
import { computed } from 'vue';
import { XChart } from '@lyra/ui';
import { use } from 'echarts/core';
import { LineChart, BarChart } from 'echarts/charts';

use([LineChart, BarChart]);

const overviewOption = computed<EChartsOption>(() => {
	return {
		legend: {
			bottom: '2.5%',
			data: ['2021', '2022'],
		},
		grid: {
			top: '5%',
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
				data: [40, 68, 86, 74, 56, 60, 87],
			},
			{
				type: 'line',
				name: '2022',
				itemStyle: { color: '#ec4899' },
				smooth: true,
				data: [65, 78, 66, 44, 56, 67, 75],
			},
		],
	};
});

const performanceOption = computed<EChartsOption>(() => {
	return {
		legend: {
			bottom: '2.5%',
			data: ['2021', '2022'],
		},
		grid: {
			top: '5%',
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
			axisPointer: {
				type: 'shadow',
			},
		},
		series: [
			{
				type: 'bar',
				name: '2021',
				itemStyle: { color: '#6366f1' },
				data: [27, 68, 86, 74, 10, 4, 87],
			},
			{
				type: 'bar',
				name: '2022',
				itemStyle: { color: '#ec4899' },
				data: [30, 78, 56, 34, 100, 45, 13],
			},
		],
	};
});
</script>

<template>
	<h1 class="text-4xl font-extrabold">Dashboard</h1>

	<div class="my-8 grid grid-cols-4 gap-4 md:grid-cols-8">
		<div
			class="relative col-span-4 flex min-w-0 flex-col break-words rounded bg-white shadow-lg md:col-span-2 xl:mb-0 dark:bg-slate-800"
		>
			<div class="flex-auto p-4">
				<div class="flex flex-wrap">
					<div class="relative w-full max-w-full flex-1 flex-grow pr-4">
						<h5 class="text-xs font-bold uppercase text-zinc-400">TRAFFIC</h5>
						<span class="text-xl font-semibold text-zinc-700 dark:text-zinc-300">350,897</span>
					</div>
					<div class="relative w-auto flex-initial pl-4">
						<div
							class="inline-flex h-12 w-12 items-center justify-center rounded-full bg-red-500 p-3 text-center text-white shadow-lg"
						>
							<div class="i-fa-bar-chart"></div>
						</div>
					</div>
				</div>
				<p class="mt-4 text-sm text-zinc-400">
					<span class="mr-2 text-emerald-500">
						<span class="i-fa-arrow-up inline-flex align-middle"></span>
						3.48%
					</span>
					<span class="whitespace-nowrap">Since last month</span>
				</p>
			</div>
		</div>

		<div
			class="relative col-span-4 flex min-w-0 flex-col break-words rounded bg-white shadow-lg md:col-span-2 xl:mb-0 dark:bg-slate-800"
		>
			<div class="flex-auto p-4">
				<div class="flex flex-wrap">
					<div class="relative w-full max-w-full flex-1 flex-grow pr-4">
						<h5 class="text-xs font-bold uppercase text-zinc-400">NEW USERS</h5>
						<span class="text-xl font-semibold text-zinc-700 dark:text-zinc-300">2,356</span>
					</div>
					<div class="relative w-auto flex-initial pl-4">
						<div
							class="inline-flex h-12 w-12 items-center justify-center rounded-full bg-orange-500 p-3 text-center text-white shadow-lg"
						>
							<div class="i-fa-pie-chart"></div>
						</div>
					</div>
				</div>
				<p class="mt-4 text-sm text-zinc-400">
					<span class="mr-2 text-red-500">
						<span class="i-fa-arrow-down inline-flex align-middle"></span>
						3.48%
					</span>
					<span class="whitespace-nowrap">Since last week</span>
				</p>
			</div>
		</div>

		<div
			class="relative col-span-4 flex min-w-0 flex-col break-words rounded bg-white shadow-lg md:col-span-2 xl:mb-0 dark:bg-slate-800"
		>
			<div class="flex-auto p-4">
				<div class="flex flex-wrap">
					<div class="relative w-full max-w-full flex-1 flex-grow pr-4">
						<h5 class="text-xs font-bold uppercase text-zinc-400">SALES</h5>
						<span class="text-xl font-semibold text-zinc-700 dark:text-zinc-300">924</span>
					</div>
					<div class="relative w-auto flex-initial pl-4">
						<div
							class="inline-flex h-12 w-12 items-center justify-center rounded-full bg-pink-500 p-3 text-center text-white shadow-lg"
						>
							<div class="i-fa-users"></div>
						</div>
					</div>
				</div>
				<p class="mt-4 text-sm text-zinc-400">
					<span class="mr-2 text-orange-500">
						<span class="i-fa-arrow-down inline-flex align-middle"></span>
						1.10%
					</span>
					<span class="whitespace-nowrap">Since yesterday</span>
				</p>
			</div>
		</div>

		<div
			class="relative col-span-4 flex min-w-0 flex-col break-words rounded bg-white shadow-lg md:col-span-2 xl:mb-0 dark:bg-slate-800"
		>
			<div class="flex-auto p-4">
				<div class="flex flex-wrap">
					<div class="relative w-full max-w-full flex-1 flex-grow pr-4">
						<h5 class="text-xs font-bold uppercase text-zinc-400">PERFORMANCE</h5>
						<span class="text-xl font-semibold text-zinc-700 dark:text-zinc-300">49,65%</span>
					</div>
					<div class="relative w-auto flex-initial pl-4">
						<div
							class="inline-flex h-12 w-12 items-center justify-center rounded-full bg-emerald-500 p-3 text-center text-white shadow-lg"
						>
							<div class="i-fa-percent"></div>
						</div>
					</div>
				</div>
				<p class="mt-4 text-sm text-zinc-400">
					<span class="mr-2 text-emerald-500">
						<span class="i-fa-arrow-up inline-flex align-middle"></span>
						12%
					</span>
					<span class="whitespace-nowrap">Since last month</span>
				</p>
			</div>
		</div>

		<div
			class="relative col-span-4 flex w-full min-w-0 flex-col break-words rounded bg-white shadow-lg md:col-span-5 dark:bg-slate-800"
		>
			<div class="mb-0 rounded-t bg-transparent px-4 py-3">
				<div class="flex flex-wrap items-center">
					<div class="relative w-full max-w-full flex-1 flex-grow">
						<h6 class="mb-1 text-xs font-semibold uppercase text-zinc-400">OVERVIEW</h6>
						<h2 class="text-xl font-semibold text-zinc-700 dark:text-zinc-300">Sales value</h2>
					</div>
				</div>
			</div>

			<div class="flex-auto p-4">
				<div class="h-350-px relative">
					<div class="chartjs-size-monitor">
						<div class="chartjs-size-monitor-expand"><div class=""></div></div>
						<div class="chartjs-size-monitor-shrink"><div class=""></div></div>
					</div>

					<XChart :option="overviewOption" class="h-100 w-full" />
				</div>
			</div>
		</div>

		<div
			class="relative col-span-4 flex w-full min-w-0 flex-col break-words rounded bg-white shadow-lg md:col-span-3 dark:bg-slate-800"
		>
			<div class="mb-0 rounded-t bg-transparent px-4 py-3">
				<div class="flex flex-wrap items-center">
					<div class="relative w-full max-w-full flex-1 flex-grow">
						<h6 class="mb-1 text-xs font-semibold uppercase text-zinc-400">Performance</h6>
						<h2 class="text-xl font-semibold text-zinc-700 dark:text-zinc-300">Total orders</h2>
					</div>
				</div>
			</div>

			<div class="flex-auto p-4">
				<div class="h-350-px relative">
					<div class="chartjs-size-monitor">
						<div class="chartjs-size-monitor-expand"><div class=""></div></div>
						<div class="chartjs-size-monitor-shrink"><div class=""></div></div>
					</div>

					<XChart :option="performanceOption" class="h-100 w-full" />
				</div>
			</div>
		</div>

		<div
			class="relative col-span-4 flex w-full min-w-0 flex-col break-words rounded bg-white shadow-lg md:col-span-5 dark:bg-slate-800"
		>
			<div class="mb-0 rounded-t border-0 px-4 py-3">
				<div class="flex flex-wrap items-center">
					<div class="relative w-full max-w-full flex-1 flex-grow px-4">
						<h3 class="text-base font-semibold text-zinc-700 dark:text-zinc-300">Page visits</h3>
					</div>
					<div
						class="relative w-full max-w-full flex-1 flex-grow px-4 ltr:text-right rtl:text-left"
					>
						<button
							class="mb-1 mr-1 rounded bg-indigo-500 px-3 py-1 text-xs font-bold uppercase text-white outline-none transition-all duration-150 ease-linear focus:outline-none active:bg-indigo-600"
							type="button"
						>
							See all
						</button>
					</div>
				</div>
			</div>
			<div class="block w-full overflow-x-auto">
				<table class="w-full border-collapse items-center bg-transparent">
					<thead>
						<tr>
							<th
								class="whitespace-nowrap border border-l-0 border-r-0 border-solid border-zinc-100 bg-zinc-50 px-6 py-3 text-left align-middle text-xs font-semibold uppercase text-zinc-500 dark:border-zinc-600 dark:bg-zinc-700 dark:text-zinc-200"
							>
								Page name
							</th>
							<th
								class="whitespace-nowrap border border-l-0 border-r-0 border-solid border-zinc-100 bg-zinc-50 px-6 py-3 text-left align-middle text-xs font-semibold uppercase text-zinc-500 dark:border-zinc-600 dark:bg-zinc-700 dark:text-zinc-200"
							>
								Visitors
							</th>
							<th
								class="whitespace-nowrap border border-l-0 border-r-0 border-solid border-zinc-100 bg-zinc-50 px-6 py-3 text-left align-middle text-xs font-semibold uppercase text-zinc-500 dark:border-zinc-600 dark:bg-zinc-700 dark:text-zinc-200"
							>
								Unique users
							</th>
							<th
								class="whitespace-nowrap border border-l-0 border-r-0 border-solid border-zinc-100 bg-zinc-50 px-6 py-3 text-left align-middle text-xs font-semibold uppercase text-zinc-500 dark:border-zinc-600 dark:bg-zinc-700 dark:text-zinc-200"
							>
								Bounce rate
							</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 text-left align-middle text-xs"
							>
								/argon/
							</th>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								4,569
							</td>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								340
							</td>
							<td
								class="flex whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								<div class="i-fa-arrow-up mr-4 text-emerald-500"></div>
								46,53%
							</td>
						</tr>
						<tr>
							<th
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 text-left align-middle text-xs"
							>
								/argon/index.html
							</th>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								3,985
							</td>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								319
							</td>
							<td
								class="flex whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								<div class="i-fa-arrow-down mr-4 text-orange-500"></div>
								46,53%
							</td>
						</tr>
						<tr>
							<th
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 text-left align-middle text-xs"
							>
								/argon/charts.html
							</th>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								3,513
							</td>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								294
							</td>
							<td
								class="flex whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								<div class="i-fa-arrow-down mr-4 text-orange-500"></div>
								36,49%
							</td>
						</tr>
						<tr>
							<th
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 text-left align-middle text-xs"
							>
								/argon/tables.html
							</th>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								2,050
							</td>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								147
							</td>
							<td
								class="flex whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								<div class="i-fa-arrow-up mr-4 text-emerald-500"></div>
								50,87%
							</td>
						</tr>
						<tr>
							<th
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 text-left align-middle text-xs"
							>
								/argon/profile.html
							</th>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								1,795
							</td>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								190
							</td>
							<td
								class="flex whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								<div class="i-fa-arrow-down mr-4 text-red-500"></div>
								46,53%
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<div
			class="relative col-span-4 flex w-full min-w-0 flex-col break-words rounded bg-white shadow-lg md:col-span-3 dark:bg-slate-800"
		>
			<div class="mb-0 rounded-t border-0 px-4 py-3">
				<div class="flex flex-wrap items-center">
					<div class="relative w-full max-w-full flex-1 flex-grow px-4">
						<h3 class="text-base font-semibold text-zinc-700 dark:text-zinc-300">Social traffic</h3>
					</div>
					<div
						class="relative w-full max-w-full flex-1 flex-grow px-4 ltr:text-right rtl:text-left"
					>
						<button
							class="mb-1 mr-1 rounded bg-indigo-500 px-3 py-1 text-xs font-bold uppercase text-white outline-none transition-all duration-150 ease-linear focus:outline-none active:bg-indigo-600"
							type="button"
						>
							See all
						</button>
					</div>
				</div>
			</div>
			<div class="block w-full overflow-x-auto">
				<table class="w-full border-collapse items-center bg-transparent">
					<thead class="thead-light">
						<tr>
							<th
								class="whitespace-nowrap border border-l-0 border-r-0 border-solid border-zinc-100 bg-zinc-50 px-6 py-3 text-left align-middle text-xs font-semibold uppercase text-zinc-500 dark:border-zinc-600 dark:bg-zinc-700 dark:text-zinc-200"
							>
								Referral
							</th>
							<th
								class="whitespace-nowrap border border-l-0 border-r-0 border-solid border-zinc-100 bg-zinc-50 px-6 py-3 text-left align-middle text-xs font-semibold uppercase text-zinc-500 dark:border-zinc-600 dark:bg-zinc-700 dark:text-zinc-200"
							>
								Users
							</th>
							<th
								class="min-w-35 whitespace-nowrap border border-l-0 border-r-0 border-solid border-zinc-100 bg-zinc-50 px-6 py-3 text-left align-middle text-xs font-semibold uppercase text-zinc-500 dark:border-zinc-600 dark:bg-zinc-700 dark:text-zinc-200"
							></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 text-left align-middle text-xs"
							>
								Facebook
							</th>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								1,480
							</td>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								<div class="flex items-center">
									<span class="mr-2">60%</span>
									<div class="relative w-full">
										<div class="flex h-2 overflow-hidden rounded bg-red-200 text-xs">
											<div
												style="width: 60%"
												class="flex flex-col justify-center whitespace-nowrap bg-red-500 text-center text-white shadow-none"
											></div>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<th
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 text-left align-middle text-xs"
							>
								Facebook
							</th>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								5,480
							</td>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								<div class="flex items-center">
									<span class="mr-2">70%</span>
									<div class="relative w-full">
										<div class="flex h-2 overflow-hidden rounded bg-emerald-200 text-xs">
											<div
												style="width: 70%"
												class="flex flex-col justify-center whitespace-nowrap bg-emerald-500 text-center text-white shadow-none"
											></div>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<th
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 text-left align-middle text-xs"
							>
								Google
							</th>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								4,807
							</td>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								<div class="flex items-center">
									<span class="mr-2">80%</span>
									<div class="relative w-full">
										<div class="flex h-2 overflow-hidden rounded bg-purple-200 text-xs">
											<div
												style="width: 80%"
												class="flex flex-col justify-center whitespace-nowrap bg-purple-500 text-center text-white shadow-none"
											></div>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<th
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 text-left align-middle text-xs"
							>
								Instagram
							</th>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								3,678
							</td>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								<div class="flex items-center">
									<span class="mr-2">75%</span>
									<div class="relative w-full">
										<div class="bg-lightBlue-200 flex h-2 overflow-hidden rounded text-xs">
											<div
												style="width: 75%"
												class="bg-lightBlue-500 flex flex-col justify-center whitespace-nowrap text-center text-white shadow-none"
											></div>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<th
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 text-left align-middle text-xs"
							>
								Twitter
							</th>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								2,645
							</td>
							<td
								class="whitespace-nowrap border-l-0 border-r-0 border-t-0 p-4 px-6 align-middle text-xs"
							>
								<div class="flex items-center">
									<span class="mr-2">30%</span>
									<div class="relative w-full">
										<div class="flex h-2 overflow-hidden rounded bg-orange-200 text-xs">
											<div
												style="width: 30%"
												class="flex flex-col justify-center whitespace-nowrap bg-emerald-500 text-center text-white shadow-none"
											></div>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</template>
