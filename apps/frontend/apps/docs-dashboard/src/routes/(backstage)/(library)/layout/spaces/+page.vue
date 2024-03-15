<script lang="ts" setup>
import { XBreadcrumb } from '@lyra/ui';
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Layout' }, { text: 'Spaces' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Spaces</h1>

	<div class="mb-4 flex flex-col border p-4">
		<div class="mb-2">
			Horizontal
			<code class="rounded bg-gray-300 p-1 text-sm text-gray-700">space-x-4</code>
		</div>

		<div class="flex space-x-4">
			<div v-for="num in 5" :key="num" class="rounded bg-white px-4 py-2 shadow dark:bg-slate-700">
				{{ num }}
			</div>
		</div>
	</div>

	<div class="mb-4 flex flex-col border p-4">
		<div class="mb-2">
			Vertical
			<code class="rounded bg-gray-300 p-1 text-sm text-gray-700">space-y-4</code>
		</div>

		<div class="flex flex-col space-y-4">
			<div v-for="num in 5" :key="num" class="rounded bg-white px-4 py-2 shadow dark:bg-slate-700">
				{{ num }}
			</div>
		</div>
	</div>
</template>
