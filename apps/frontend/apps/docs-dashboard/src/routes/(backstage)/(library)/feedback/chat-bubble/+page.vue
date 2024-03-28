<script lang="ts" setup>
import { XBreadcrumb, XCard, XChatBubble } from '@lyra/ui';
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Feedback' }, { text: 'ChatBubble' }]" />

	<h1 class="my-4 text-4xl font-extrabold">ChatBubble</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>

		<XCard>
			<div class="flex flex-col gap-4">
				<XChatBubble />

				<XChatBubble self class="justify-end" />
			</div>
		</XCard>
	</section>
</template>
