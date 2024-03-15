<script lang="ts" setup>
import { XBreadcrumb } from '@lyra/ui';
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'General' }, { text: 'Paper' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Paper</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Elevation</h2>

		<div class="grid grid-cols-3 gap-6 text-center">
			<div class="bg-white p-4 shadow dark:bg-slate-800">shadow</div>
			<div class="bg-white p-4 shadow-md dark:bg-slate-700 dark:text-slate-300">shadow-md</div>
			<div class="bg-white p-4 shadow-lg dark:bg-slate-600 dark:text-slate-200">shadow-lg</div>
		</div>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Rounded</h2>

		<div class="grid grid-cols-3 gap-6 text-center">
			<div class="border-primary-500 rounded border-2 p-4">rounded</div>
			<div class="border-primary-500 rounded-md border-2 p-4">rounded-md</div>
			<div class="border-primary-500 rounded-lg border-2 p-4">rounded-lg</div>
		</div>
	</section>
</template>
