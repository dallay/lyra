<script lang="ts" setup>
import { XBreadcrumb, XCard } from '@lyra/ui';
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Layout' }, { text: 'Grid' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Grid</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Columns</h2>

		<XCard>
			<div class="grid grid-cols-12 gap-2 text-white">
				<div class="bg-primary-400 col-span-8 rounded p-2 text-center">col-8</div>
				<div class="bg-primary-400 col-span-4 rounded p-2 text-center">col-4</div>

				<div class="bg-primary-400 col-span-4 rounded p-2 text-center">col-4</div>
				<div class="bg-primary-400 col-span-8 rounded p-2 text-center">col-8</div>
			</div>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Rows</h2>

		<XCard>
			<div class="grid grid-cols-12 grid-rows-3 gap-2 text-white">
				<div class="bg-primary-400 col-span-4 row-span-3 rounded p-2 text-center">col-4 row-3</div>
				<div class="bg-primary-400 col-span-8 rounded p-2 text-center">col-8 row-1</div>
				<div class="bg-primary-400 col-span-8 row-span-2 rounded p-2 text-center">col-8 row-2</div>
			</div>
		</XCard>
	</section>
</template>
