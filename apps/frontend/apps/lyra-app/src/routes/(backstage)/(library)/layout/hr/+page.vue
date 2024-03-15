<script lang="ts" setup>
import { XBreadcrumb, XCard } from '@lyra/ui';
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Layout' }, { text: 'HR' }]" />

	<h1 class="my-4 text-4xl font-extrabold">HR</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Icon</h2>

		<XCard>
			<p class="text-gray-500 dark:text-gray-400">
				Track work across the enterprise through an open, collaborative platform. Link issues across
				Jira and ingest data from other software development tools, so your IT support and
				operations teams have richer contextual information to rapidly respond to requests,
				incidents, and changes.
			</p>

			<div class="relative inline-flex w-full items-center justify-center">
				<hr class="my-8 h-1 w-64 rounded border-0 bg-gray-200 dark:bg-gray-700" />

				<div class="absolute left-1/2 -translate-x-1/2 bg-white px-4 dark:bg-gray-800">
					<div class="i-bi-quote h-5 w-5 text-gray-700 dark:text-gray-300"></div>
				</div>
			</div>

			<p class="text-gray-500 dark:text-gray-400">
				Deliver great service experiences fast - without the complexity of traditional ITSM
				solutions.Accelerate critical development work, eliminate toil.
			</p>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Text</h2>

		<XCard>
			<p class="text-gray-500 dark:text-gray-400">
				Track work across the enterprise through an open, collaborative platform. Link issues across
				Jira and ingest data from other software development tools, so your IT support and
				operations teams have richer contextual information to rapidly respond to requests,
				incidents, and changes.
			</p>

			<div class="relative inline-flex w-full items-center justify-center">
				<hr class="my-8 h-px w-64 border-0 bg-gray-200 dark:bg-gray-700" />

				<span class="absolute left-1/2 -translate-x-1/2 bg-white px-3 font-medium dark:bg-gray-800"
					>or</span
				>
			</div>

			<p class="text-gray-500 dark:text-gray-400">
				Deliver great service experiences fast - without the complexity of traditional ITSM
				solutions.Accelerate critical development work, eliminate toil.
			</p>
		</XCard>
	</section>
</template>
