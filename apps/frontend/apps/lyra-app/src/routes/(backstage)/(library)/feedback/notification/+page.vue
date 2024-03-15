<script lang="ts" setup>
import type { ComponentProps } from 'vue-component-type-helpers';
import { ref } from 'vue';
import { XBreadcrumb, XCard, XButton, XNotification } from '@lyra/ui';
import { useNotification } from '@lyra/ui';

const notify = ref();

let count = 0;

const push = () => {
	count += 1;
	notify.value.push({ message: `This is a test notification. (${count})` });
};

const notify2 = ref();

let count2 = 0;

const push2 = () => {
	count2 += 1;
	notify2.value.push({ message: `This is a test notification. (${count2})` });
};

let count3 = 0;

const notification = useNotification();

type Color = ComponentProps<typeof XNotification>['color'];

const colorSelector = (() => {
	const colors: Color[] = ['success', 'danger', 'warning', 'info'];

	let currentIndex = 0;

	return () => {
		const currentColor = colors[currentIndex];
		currentIndex = (currentIndex + 1) % colors.length;
		return currentColor;
	};
})();

const push3 = () => {
	count3 += 1;

	notification.add({
		message: `This is a test notification. (${count3})`,
		color: colorSelector(),
	});
};
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Feedback' }, { text: 'Notification' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Notification</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<XButton @click="push">Push</XButton>
			<XNotification ref="notify" />
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Custom</h2>

		<XCard>
			<XButton @click="push2">Push</XButton>
			<XNotification ref="notify2" color="secondary" icon="i-material-symbols-wifi-rounded" />
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Severity</h2>

		<XCard>
			<XButton @click="push3">Push</XButton>
		</XCard>
	</section>
</template>
