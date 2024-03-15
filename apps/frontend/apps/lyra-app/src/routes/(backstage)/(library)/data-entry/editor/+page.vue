<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { XBreadcrumb, XCard, XRichTextEditor, XButton } from '@lyra/ui';

const content = ref('');

onMounted(() => {
	content.value = `<h1>h1. Heading</h1>`;
});

function setContent() {
	const newContent = `
    <h1>h1. Heading</h1>
    <h2>h2. Heading</h2>
    <h3>h3. Heading</h3>
    <h4>h4. Heading</h4>
    <h5>h5. Heading</h5>
    <h6>h6. Heading</h6>

    <p><span style="color: #FE68C9">Text Color</span></p>

    <p>This isn't bold.</p>
    <p><strong>This is bold.</strong></p>
    <p><em>This is italic.</em></p>
  `;

	content.value = newContent;
}
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Data Entry' }, { text: 'RichTextEditor' }]" />

	<h1 class="my-4 text-4xl font-extrabold">RichTextEditor</h1>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Basic</h2>
		<p class="my-4">Two-way value binding is defined using `v-model`.</p>

		<XCard>
			<XRichTextEditor v-model="content" />
			<div class="my-4 rounded border p-4">{{ content }}</div>
			<XButton @click="setContent">Set Content</XButton>
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Label</h2>

		<XCard>
			<XRichTextEditor v-model="content" label="Example label" />
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Disabled</h2>

		<XCard>
			<XRichTextEditor v-model="content" disabled />
		</XCard>
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Viewonly</h2>

		<XCard>
			<XRichTextEditor v-model="content" viewonly />
		</XCard>
	</section>
</template>
