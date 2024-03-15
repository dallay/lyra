<script lang="ts" setup>
import type { ComponentProps } from 'vue-component-type-helpers';
import { ref, computed } from 'vue';
import { XBreadcrumb, XCard, XButton, XTour } from '@lyra/ui';

const open = ref(false);
const step = ref(0);

const el1 = ref();
const el2 = ref();
const el3 = ref();
const el4 = ref();

const steps = computed<ComponentProps<typeof XTour>['steps']>(() => [
	{
		title: `What is Vue?`,
		content: `Vue (pronounced /vjuː/, like view) is a JavaScript framework for building user interfaces.`,
		target: el1,
	},
	{
		title: `The Progressive Framework`,
		content: `Vue is a framework and ecosystem that covers most of the common features needed in frontend development.`,
		target: el2,
	},
	{
		title: `Single-File Components`,
		content: `In most build-tool-enabled Vue projects, we author Vue components using an HTML-like file format called Single-File Component (also known as *.vue files, abbreviated as SFC).`,
		target: el3,
	},
	{
		title: `Composition API`,
		content: `With Composition API, we define a component's logic using imported API functions. In SFCs, Composition API is typically used with <script setup>.`,
		target: el4,
	},
]);
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Navigation' }, { text: 'Tours' }]" />

	<h1 ref="el1" class="my-4 text-4xl font-extrabold">Tours</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<XButton label="Begin Tour" @click="open = true" />
			<XTour v-model:open="open" v-model:step="step" :steps="steps" />
		</XCard>
	</section>

	<div role="status" class="my-7 animate-pulse">
		<div class="mb-4 h-2.5 w-48 rounded-full bg-gray-300 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[460px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[500px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[450px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[380px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="h-2 max-w-[460px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<span class="sr-only">Loading...</span>
	</div>

	<div
		ref="el2"
		class="flex h-48 w-full animate-pulse items-center justify-center rounded bg-gray-300 dark:bg-gray-700"
	>
		<div class="i-bi-image-alt h-12 w-12 text-gray-200"></div>
	</div>

	<div role="status" class="my-6 animate-pulse">
		<div class="mb-2.5 h-2 max-w-[460px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[450px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[460px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[500px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[450px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[380px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="h-2 max-w-[460px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<span class="sr-only">Loading...</span>
	</div>

	<div role="status" class="my-6 animate-pulse">
		<div ref="el3" class="mb-2.5 h-2 max-w-[460px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[450px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[460px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[500px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<span class="sr-only">Loading...</span>
	</div>

	<div role="status" class="mb-6 mt-7 animate-pulse">
		<div ref="el4" class="mb-4 h-2.5 w-48 rounded-full bg-gray-300 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[460px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[450px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[460px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[500px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[450px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[380px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[460px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="mb-2.5 h-2 max-w-[500px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<div class="h-2 max-w-[460px] rounded-full bg-gray-200 dark:bg-gray-700"></div>
		<span class="sr-only">Loading...</span>
	</div>
</template>
