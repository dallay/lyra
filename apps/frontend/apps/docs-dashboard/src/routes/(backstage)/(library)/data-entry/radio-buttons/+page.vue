<script lang="ts" setup>
import { reactive } from 'vue';
import { XBreadcrumb, XCard, XRadioGroup } from '@lyra/ui';

const flux = reactive({
	radioGroup1: '',
	radioGroup1Options: ['Angular', 'React', 'Svelte', 'Vue'],
});
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Data Entry' }, { text: 'RadioGroup' }]" />

	<h1 class="my-4 text-4xl font-extrabold">RadioGroup</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<XRadioGroup v-model:value="flux.radioGroup1" :options="flux.radioGroup1Options" />
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Label</h2>

		<XCard>
			<XRadioGroup
				v-model:value="flux.radioGroup1"
				label="Example label"
				:options="flux.radioGroup1Options"
			/>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Required</h2>

		<XCard>
			<XRadioGroup
				v-model:value="flux.radioGroup1"
				label="Example label"
				:options="flux.radioGroup1Options"
				required
			/>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Disabled</h2>

		<XCard>
			<XRadioGroup v-model:value="flux.radioGroup1" :options="flux.radioGroup1Options" disabled />
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Invalid</h2>

		<XCard>
			<XRadioGroup v-model:value="flux.radioGroup1" :options="flux.radioGroup1Options" invalid />
		</XCard>

		<section class="my-4">
			<h3 class="my-4 pt-2 text-2xl font-semibold">Error Message</h3>

			<XCard>
				<XRadioGroup
					v-model:value="flux.radioGroup1"
					:options="flux.radioGroup1Options"
					invalid="Incorrect entry"
				/>
			</XCard>
		</section>
	</section>
</template>
