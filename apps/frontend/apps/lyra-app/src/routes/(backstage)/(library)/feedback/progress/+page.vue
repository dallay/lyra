<script lang="ts" setup>
import { XBreadcrumb, XCard, XSpinner, XProgressBar, XProgressCircle } from '@lyra/ui';
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Feedback' }, { text: 'Progress' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Progress</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Spinner</h2>

		<XCard>
			<div class="flex items-center justify-center gap-4">
				<XSpinner class="h-4 w-4" />
				<XSpinner />
				<XSpinner class="h-12 w-12" />
			</div>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">ProgressBar</h2>

		<XCard>
			<XProgressBar />
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">ProgressCircle</h2>

		<XCard>
			<XProgressCircle :percentage="72">72%</XProgressCircle>
		</XCard>

		<section class="my-4">
			<h3 class="my-4 pt-2 text-2xl font-semibold">Stroke</h3>

			<XCard>
				<XProgressCircle :percentage="56" stroke="stroke-3">56%</XProgressCircle>
			</XCard>
		</section>

		<section class="my-4">
			<h3 class="my-4 pt-2 text-2xl font-semibold">Size</h3>

			<XCard>
				<XProgressCircle :percentage="17" size="size-50" stroke="stroke-3">
					<div class="text-4xl font-extrabold">17%</div>
				</XProgressCircle>
			</XCard>
		</section>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">ProgressBar Percentage</h2>

		<XCard>
			<div class="flex items-center gap-4">
				<XProgressBar :percentage="66" class="!h-2 rounded-md" />
				<div>66%</div>
			</div>
		</XCard>
	</section>
</template>
