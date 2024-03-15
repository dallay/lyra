<script lang="ts" setup>
import { ref } from 'vue';
import { XBreadcrumb, XCard, XTimePicker } from '@lyra/ui';

const time = ref('');
</script>

<template>
	<XBreadcrumb
		:items="[
			{ text: 'Library' },
			{ text: 'Data Entry' },
			{ text: 'Pickers' },
			{ text: 'TimePicker' },
		]"
	/>

	<h1 class="my-4 text-4xl font-extrabold">TimePicker</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<XTimePicker v-model:value="time" />
			<div class="mt-1">{{ time }}</div>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Clearable</h2>

		<XCard>
			<XTimePicker v-model:value="time" clearable />
			<div class="mt-1">{{ time }}</div>
		</XCard>
	</section>
</template>
