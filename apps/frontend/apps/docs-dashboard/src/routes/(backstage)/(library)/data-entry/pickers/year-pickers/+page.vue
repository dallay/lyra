<script lang="ts" setup>
import { ref } from 'vue';
import { XBreadcrumb, XCard, XYearPicker } from '@lyra/ui';

const year = ref<number>();
</script>

<template>
	<XBreadcrumb
		:items="[
			{ text: 'Library' },
			{ text: 'Data Entry' },
			{ text: 'Pickers' },
			{ text: 'Year Pickers' },
		]"
	/>

	<h1 class="my-4 text-4xl font-extrabold">YearPicker</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<XYearPicker v-model:value="year" />
			<div class="mt-1">{{ year }}</div>
		</XCard>
	</section>
</template>
