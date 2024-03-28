<script lang="ts" setup>
import { XBreadcrumb, XCard, XButton } from '@lyra/ui';
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Data Display' }, { text: 'Card' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Card</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard class="my-4 max-w-sm">
			<template #title>
				<div>Noteworthy technology acquisitions</div>
			</template>

			<template #subtitle>
				<div>2021/10/01 ~ 2021/10/31</div>
			</template>

			<div>
				Here are the biggest enterprise technology acquisitions of 2021 so far, in reverse
				chronological order.
			</div>

			<template #footer>
				<XButton>Action</XButton>
			</template>
		</XCard>

		<XCard class="my-4 max-w-sm">
			<template #header>
				<div class="aspect-16/9 flex items-center justify-center rounded-t-lg bg-gray-200">
					aspect-16/9
				</div>
			</template>

			<template #title>
				<div>Noteworthy technology acquisitions</div>
			</template>

			<template #subtitle>
				<div>2021/10/01 ~ 2021/10/31</div>
			</template>

			<div>
				Here are the biggest enterprise technology acquisitions of 2021 so far, in reverse
				chronological order.
			</div>

			<template #footer>
				<XButton>Action</XButton>
			</template>
		</XCard>

		<XCard class="my-4 flex max-w-lg">
			<template #header>
				<div class="w-50 flex h-full items-center justify-center rounded-l-lg bg-gray-200"></div>
			</template>

			<template #title>
				<time class="text-sm font-normal text-zinc-400">2021/10/01 ~ 2021/10/31</time>
				<div>Noteworthy technology acquisitions</div>
			</template>

			<template #subtitle>
				<div>Card subtitle</div>
			</template>

			<div>
				Here are the biggest enterprise technology acquisitions of 2021 so far, in reverse
				chronological order.
			</div>

			<template #footer>
				<XButton>Action</XButton>
			</template>
		</XCard>
	</section>
</template>
