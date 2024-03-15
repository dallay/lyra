<script lang="ts" setup>
import { XBreadcrumb, XCard } from '@lyra/ui';

import InfiniteList from './InfiniteList.vue';
</script>

<template>
	<XBreadcrumb
		:items="[{ text: 'Library' }, { text: 'Data Display' }, { text: 'Infinite Scroll' }]"
	/>

	<h1 class="my-4 text-4xl font-extrabold">Infinite Scroll</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<InfiniteList />
		</XCard>
	</section>
</template>
