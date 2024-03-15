<script lang="ts" setup>
import { XBreadcrumb, XCard, XCode } from '@lyra/ui';
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Layout' }, { text: 'Aspect Ratio' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Aspect Ratio</h1>

	<section class="my-8">
		<h2 class="my-4 flex items-center gap-2 text-3xl font-bold">
			Video
			<XCode>aspect-video</XCode>
		</h2>

		<XCard>
			<iframe
				src="https://www.youtube.com/embed/QhBnZ6NPOY0"
				title="Naruto"
				allowfullscreen
				class="max-w-140 aspect-video w-full"
			></iframe>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 flex items-center gap-2 pt-6 text-3xl font-bold">
			4 by 3
			<XCode>aspect-4/3</XCode>
		</h2>

		<XCard>
			<img
				src="https://bit.ly/naruto-sage"
				alt="Naruto"
				class="max-w-100 aspect-4/3 w-full object-cover"
			/>
		</XCard>
	</section>
</template>
