<script lang="ts" setup>
import { ref } from 'vue';
import { XBreadcrumb, XCard, XPassword } from '@lyra/ui';

const password = ref('oxlint');
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Data Entry' }, { text: 'Password' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Password</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<XPassword v-model:value="password" label="Password" />
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Meter</h2>

		<XCard>
			<XPassword v-model:value="password" label="New Password" meter />
		</XCard>
	</section>
</template>
