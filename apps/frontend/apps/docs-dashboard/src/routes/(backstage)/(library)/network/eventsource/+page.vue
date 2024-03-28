<script lang="ts" setup>
import { XBreadcrumb, XCard } from '@lyra/ui';
import { useEventSource } from '@lyra/ui';

const { data } = useEventSource('/sse');
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Network' }, { text: 'EventSource' }]" />

	<h1 class="my-4 text-4xl font-extrabold">EventSource</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<div class="flex justify-center">
				<div>{{ data }}</div>
			</div>
		</XCard>
	</section>
</template>
