<script lang="ts" setup>
import { XBreadcrumb, XCard } from '@lyra/ui';
import { useWebSocket } from '@lyra/ui';

const { data, send } = useWebSocket('/echo');

send('Hello from Vue!');
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Network' }, { text: 'WebSocket' }]" />

	<h1 class="my-4 text-4xl font-extrabold">WebSocket</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<div class="flex justify-center">
				<div>{{ data }}</div>
			</div>
		</XCard>
	</section>
</template>
