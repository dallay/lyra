<script lang="ts" setup>
import { ref } from 'vue';
import { XBreadcrumb, XCard, XButton } from '@lyra/ui';
import { request } from '@lyra/ui';

const text = ref();

const doFetch = async () => {
	const { _data } = await request<{ message: string }>('/hello-world', { method: 'GET' });
	text.value = _data?.message;
};
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Network' }, { text: 'Fetch' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Fetch</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<div class="flex flex-col items-center gap-2">
				<div><XButton @click="doFetch">Fetch</XButton></div>
				<div>{{ text }}</div>
			</div>
		</XCard>
	</section>
</template>
