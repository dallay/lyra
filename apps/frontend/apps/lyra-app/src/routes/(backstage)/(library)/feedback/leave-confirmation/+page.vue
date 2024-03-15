<script lang="ts" setup>
import { ref } from 'vue';
import { XBreadcrumb, XCard, XTextField, XLeaveConfirmation } from '@lyra/ui';

const initText = ref('');
const text = ref('');
</script>

<template>
	<XBreadcrumb
		:items="[{ text: 'Library' }, { text: 'Feedback' }, { text: 'LeaveConfirmation' }]"
	/>

	<h1 class="my-4 text-4xl font-extrabold">LeaveConfirmation</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<XTextField v-model:value="text" label="Label" />

			<div class="mt-1 text-xs text-gray-400 dark:text-gray-500">
				After entering text, leaving the page triggers a confirmation dialog before exiting.
			</div>
		</XCard>
	</section>

	<XLeaveConfirmation :trigger="initText !== text" />
</template>
