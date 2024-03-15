<script lang="ts" setup>
import { reactive } from 'vue';
import { XBreadcrumb, XCard, XCheckboxGroup } from '@lyra/ui';

const flux = reactive({
	checkboxGroup1: [] as string[],
	checkboxGroup1Options: ['Angular', 'React', 'Svelte', 'Vue'],

	checkboxGroup2: [] as { label: string; value: string }[],
	checkboxGroup2Options: [
		{ label: 'Angular', value: 'angular' },
		{ label: 'React', value: 'react' },
		{ label: 'Svelte', value: 'svelte' },
		{ label: 'Vue', value: 'vue' },
	],
});
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Data Entry' }, { text: 'CheckboxGroup' }]" />

	<h1 class="my-4 text-4xl font-extrabold">CheckboxGroup</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<XCheckboxGroup v-model:value="flux.checkboxGroup1" :options="flux.checkboxGroup1Options" />
			<div class="mt-1">{{ flux.checkboxGroup1 }}</div>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Label</h2>

		<XCard>
			<XCheckboxGroup
				v-model:value="flux.checkboxGroup1"
				label="Example label"
				:options="flux.checkboxGroup1Options"
			/>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Invalid</h2>

		<XCard>
			<XCheckboxGroup
				v-model:value="flux.checkboxGroup1"
				:options="flux.checkboxGroup1Options"
				invalid
			/>
		</XCard>

		<section class="my-4">
			<h3 class="my-4 pt-2 text-2xl font-semibold">Error Message</h3>

			<XCard>
				<XCheckboxGroup
					v-model:value="flux.checkboxGroup1"
					:options="flux.checkboxGroup1Options"
					invalid="Incorrect entry"
				/>
			</XCard>
		</section>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Disabled</h2>

		<XCard>
			<XCheckboxGroup
				v-model:value="flux.checkboxGroup1"
				:options="flux.checkboxGroup1Options"
				disabled
			/>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Options</h2>

		<XCard>
			<XCheckboxGroup v-model:value="flux.checkboxGroup2" :options="flux.checkboxGroup2Options" />
			<div class="mt-1">{{ flux.checkboxGroup2 }}</div>
		</XCard>
	</section>
</template>
