<script lang="ts" setup>
import { XBreadcrumb, XCard, XDivider } from '@lyra/ui';
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Layout' }, { text: 'Divider' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Divider</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Horizontal</h2>

		<XCard>
			<div>Text</div>
			<XDivider />
			<div>Text</div>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Vertical</h2>

		<XCard>
			<div class="flex">
				<div>Text</div>
				<XDivider orientation="vertical" />
				<div>Text</div>
			</div>
		</XCard>
	</section>

	<div class="mb-4 flex flex-col border p-4">
		<div class="mb-2">
			Horizontal
			<code class="rounded bg-gray-300 p-1 text-sm text-gray-700">divide-x-2</code>
		</div>

		<div class="flex divide-x-2 rounded bg-white shadow dark:bg-slate-700">
			<div v-for="num in 5" :key="num" class="flex-1 px-4 py-2">
				{{ num }}
			</div>
		</div>
	</div>

	<div class="mb-4 flex flex-col border p-4">
		<div class="mb-2">
			Vertical
			<code class="rounded bg-gray-300 p-1 text-sm text-gray-700">divide-y-2</code>
		</div>

		<div class="flex flex-col divide-y-2 rounded bg-white shadow dark:bg-slate-700">
			<div v-for="num in 5" :key="num" class="px-4 py-2">
				{{ num }}
			</div>
		</div>
	</div>
</template>
