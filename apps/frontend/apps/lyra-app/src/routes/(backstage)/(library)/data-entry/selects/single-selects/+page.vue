<script lang="ts" setup>
import { reactive } from 'vue';
import { XBreadcrumb, XCard, XSelect } from '@lyra/ui';

import WithTable from './WithTable.vue';
import WithTableInDialog from './WithTableInDialog.vue';

const flux = reactive({
	select1: '',
	select1Options: [
		{ label: 'Angular', value: 'f1' },
		{ label: 'React', value: 'f2' },
		{ label: 'Svelte', value: 'f3' },
		{ label: 'Vue', value: 'f4' },
		{ label: 'Nest', value: 'b1' },
		{ label: 'Express', value: 'b2' },
		{ label: 'Fastify', value: 'b3' },
		{ label: 'Koa', value: 'b4' },
	],
});
</script>

<template>
	<XBreadcrumb
		:items="[{ text: 'Library' }, { text: 'Data Entry' }, { text: 'Selects' }, { text: 'Select' }]"
	/>

	<h1 class="my-4 text-4xl font-extrabold">Select</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<div class="max-w-xs">
				<XSelect
					v-model:value="flux.select1"
					:label="'Example label'"
					:options="flux.select1Options"
				/>
			</div>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Disabled</h2>

		<XCard>
			<div class="max-w-xs">
				<XSelect
					v-model:value="flux.select1"
					:label="'Example label'"
					:options="flux.select1Options"
					disabled
				/>
			</div>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Clearable</h2>

		<XCard>
			<div class="max-w-xs">
				<XSelect v-model:value="flux.select1" :options="flux.select1Options" clearable />
			</div>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Filterable</h2>

		<XCard>
			<div class="max-w-xs">
				<XSelect v-model:value="flux.select1" :options="flux.select1Options" filterable />
			</div>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Invalid</h2>

		<XCard>
			<div class="max-w-xs">
				<XSelect
					v-model:value="flux.select1"
					:options="flux.select1Options"
					invalid="Incorrect entry"
				/>
			</div>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Loading</h2>

		<XCard>
			<div class="max-w-xs">
				<XSelect v-model:value="flux.select1" :options="flux.select1Options" loading />
			</div>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">With Table</h2>

		<XCard>
			<WithTable />
		</XCard>

		<section class="my-4">
			<h3 class="my-4 pt-2 text-2xl font-semibold">In Dialog</h3>

			<XCard>
				<WithTableInDialog />
			</XCard>
		</section>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Option Groups (TODO)</h2>

		<XCard>
			<!--  -->
		</XCard>
	</section>
</template>
