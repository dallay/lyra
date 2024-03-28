<script lang="ts" setup>
import { XBreadcrumb, XCarousel } from '@lyra/ui';
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Data Display' }, { text: 'Carousel' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Carousel</h1>

	<div class="mb-12 mt-4">
		<div class="mb-2 text-xl">Basic</div>

		<XCarousel class="h-100 w-full">
			<div
				v-for="(item, index) in ['#14b8a6', '#8b5cf6', '#ec4899']"
				:key="item"
				class="pointer-events-none absolute top-1/2 h-full w-full -translate-x-1/2 -translate-y-1/2 touch-none"
				:style="{ backgroundColor: item, left: `${50 + 100 * index}%` }"
			></div>
		</XCarousel>
	</div>

	<div class="mb-12 mt-4">
		<div class="mb-2 text-xl">Breakpoints</div>

		<XCarousel :move="45" class="h-50 w-full">
			<div
				v-for="(item, index) in [
					'bg-red-500',
					'bg-orange-500',
					'bg-yellow-500',
					'bg-green-500',
					'bg-blue-500',
					'bg-indigo-500',
					'bg-purple-500',
				]"
				:key="item"
				class="w-100 pointer-events-none absolute top-1/2 h-full -translate-x-1/2 -translate-y-1/2 touch-none rounded-lg"
				:class="item"
				:style="{ left: `${50 + 45 * index}%` }"
			></div>
		</XCarousel>
	</div>
</template>
