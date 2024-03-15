<script lang="ts" setup>
import { XBreadcrumb, XCard, XTag, XCodeBlock } from '@lyra/ui';

const importCode = `import { XTag } from '@lyra/ui';`;

const basicCode = `<XTag>Primary</XTag>`;

const severityCode = `<XTag color="primary">Primary</XTag>
<XTag color="secondary">Secondary</XTag>
<XTag color="success">Success</XTag>
<XTag color="danger">Danger</XTag>
<XTag color="warning">Warning</XTag>
<XTag color="info">Info</XTag>`;
</script>

<template>
	<XBreadcrumb :items="[{ text: 'Library' }, { text: 'Data Display' }, { text: 'Tag' }]" />

	<h1 class="my-4 text-4xl font-extrabold">Tag</h1>
	<p class="my-4">Tag component is used to categorize content.</p>

	<section class="my-8">
		<h2 class="my-4 text-3xl font-bold">Import</h2>

		<XCodeBlock :code="importCode" language="ts" />
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Basic</h2>

		<XCard>
			<XTag>Primary</XTag>
		</XCard>

		<XCodeBlock :code="basicCode" language="html" />
	</section>

	<section class="my-8">
		<h2 class="my-4 pt-6 text-3xl font-bold">Severity</h2>

		<XCard>
			<div class="flex gap-4">
				<XTag color="primary">Primary</XTag>
				<XTag color="secondary">Secondary</XTag>
				<XTag color="success">Success</XTag>
				<XTag color="danger">Danger</XTag>
				<XTag color="warning">Warning</XTag>
				<XTag color="info">Info</XTag>
			</div>
		</XCard>

		<XCodeBlock :code="severityCode" language="html" />
	</section>
</template>
