<script lang="ts" setup>
import { reactive } from 'vue';
import { XBreadcrumb, XCard, XTextarea } from '@lyra/ui';

const flux = reactive({
	textarea1: '',
	textarea2: `Curabitur at sapien quis augue accumsan ultrices in non ipsum.\nNam pharetra tortor nec orci aliquam ornare.\nFusce quis nulla tempor, dapibus ligula at, posuere elit.\nFusce consequat odio id pretium ornare.\nMauris quis massa finibus odio auctor pulvinar.`,
});
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Data Entry' }, { text: 'Textarea' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Textarea</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<XTextarea v-model:value="flux.textarea1" label="Example label" placeholder="Type here..." />
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Required</h2>

		<XCard>
			<XTextarea
				v-model:value="flux.textarea2"
				label="Example label"
				placeholder="Type here..."
				required
			/>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Invalid</h2>

		<XCard>
			<XTextarea
				v-model:value="flux.textarea2"
				label="Example label"
				placeholder="Type here..."
				required
				invalid
			/>
		</XCard>

		<section class="my-4">
			<h3 class="my-4 pt-2 text-2xl font-semibold">Error Message</h3>

			<XCard>
				<XTextarea
					v-model:value="flux.textarea2"
					label="Example label"
					placeholder="Type here..."
					required
					invalid="Incorrect entry"
				/>
			</XCard>
		</section>
	</section>

	<section class="my-8">
		<h3 class="my-4 pt-6 text-3xl font-bold">Disabled</h3>

		<XCard>
			<XTextarea
				v-model:value="flux.textarea2"
				label="Example label"
				placeholder="Type here..."
				disabled
			/>
		</XCard>
	</section>

	<section class="my-8">
		<h3 class="my-4 pt-6 text-3xl font-bold">Read Only</h3>

		<XCard>
			<XTextarea v-model:value="flux.textarea2" label="Example label" readonly />
		</XCard>
	</section>
</template>
