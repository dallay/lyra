<script lang="ts" setup>
import type { HighlighterCore } from 'shiki/core';
import { ref, onBeforeMount, reactive } from 'vue';
import { useDark } from '@vueuse/core';
import { getHighlighterCore } from 'shiki/core';

import Card from '../card/Card.vue';
import XButton from '../button/Button.vue';
import XNotification from '../notification/Notification.vue';

const props = withDefaults(
	defineProps<{
		code?: string;
		language?: 'html' | 'scss' | 'ts' | 'vue';
	}>(),
	{
		code: '',
		language: 'vue',
	}
);

const isDark = useDark();

const highlighter = ref<HighlighterCore>();

const notify = ref();

const state = reactive({
	showCopyIcon: false,
});

const copyToClipboard = (text: string) => {
	navigator.clipboard.writeText(text).then(
		() => {
			// You can add some notification here to inform the user that the text has been copied
			notify.value.push({ message: 'Copied to clipboard' });
		},
		(err) => {
			console.error('Could not copy text: ', err);
		}
	);
};

onBeforeMount(async () => {
	highlighter.value = await getHighlighterCore({
		themes: [import('shiki/themes/github-dark.mjs'), import('shiki/themes/github-light.mjs')],
		langs: [
			import('shiki/langs/html.mjs'),
			import('shiki/langs/scss.mjs'),
			import('shiki/langs/typescript.mjs'),
			import('shiki/langs/vue.mjs'),
		],
		loadWasm: import('shiki/wasm'),
	});
});
</script>

<template>
	<div
		class="relative flex items-center justify-between"
		@mouseenter="state.showCopyIcon = true"
		@mouseleave="state.showCopyIcon = false"
	>
		<XButton
			v-show="state.showCopyIcon"
			icon="i-fluent:clipboard-32-regular"
			size="small"
			variant="text"
			color="secondary"
			@click="copyToClipboard(props.code)"
			class="absolute top-5 end-0.5 right-2.5"
		/>
		<XNotification ref="notify" color="success" icon="i-fluent:clipboard-16-regular" />
		<Card class="rounded-lg my-4 overflow-x-scroll">
			<!-- eslint-disable vue/no-v-html -->
			<pre
				class="w-fit px-4 lg:px-6 overflow-x-scroll"
				v-html="
					highlighter?.codeToHtml(props.code, {
						lang: props.language,
						theme: isDark ? 'github-dark' : 'github-light',
					})
				"
			></pre>
			<!--eslint-enable-->
		</Card>
	</div>
</template>

<style lang="scss" scoped>
:deep(.Card-Body) {
	@apply !px-0;
}
</style>
