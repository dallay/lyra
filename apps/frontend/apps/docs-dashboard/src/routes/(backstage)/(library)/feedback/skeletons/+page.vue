<script lang="ts" setup>
import { ref } from 'vue';
import { XBreadcrumb, XCard, XSkeleton, XButton } from '@lyra/ui';
import { useImage } from '@vueuse/core';

const imageOptions = ref({ src: 'https://place-hold.it/300x200' });
const colors = ['14b8a6', '8b5cf6', 'ec4899'];
const { isLoading } = useImage(imageOptions, { delay: 1000 });

function change() {
	imageOptions.value.src = '';
	const color = colors[Math.floor(Math.random() * colors.length)];
	imageOptions.value.src = `https://place-hold.it/300x200/${color}`;
}
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Feedback' }, { text: 'Skeletons' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Skeleton</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard class="max-w-md">
			<div class="flex gap-4">
				<XSkeleton class="h-10 w-10 rounded-full" />

				<div class="flex-1 space-y-6 py-1">
					<XSkeleton class="h-2" />

					<div class="space-y-3">
						<div class="grid grid-cols-3 gap-4">
							<XSkeleton class="col-span-2 h-2" />
							<XSkeleton class="col-span-1 h-2" />
						</div>

						<XSkeleton class="h-2" />
					</div>
				</div>
			</div>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Image</h2>

		<XCard class="max-w-md">
			<XSkeleton
				v-if="isLoading"
				class="max-w-75 aspect-3/2 flex items-center justify-center rounded-md shadow-md"
			>
				<div class="i-bi-image-alt h-12 w-12 text-gray-200"></div>
			</XSkeleton>

			<img
				v-else
				:src="imageOptions.src"
				class="max-w-75 aspect-3/2 rounded-md bg-slate-300/90 shadow-md"
			/>

			<div class="mt-6">
				<XButton @click="change">Change</XButton>
			</div>
		</XCard>
	</section>
</template>
